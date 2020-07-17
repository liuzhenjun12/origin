package udp.nio;

import base.api.SocketCode;
import base.util.JSON_Util;
import base.vo.ReturnJson;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import udp.config.WebSocket;
import udp.test.JCIPLockDemo;
import udp.vo.LockBean;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class MyApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
    private JCIPLockServer frame;
    private static PreparedStatement ps = null;
    private static Connection conn=null;
    private List<LockBean> locks=new ArrayList<LockBean>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        frame = new JCIPLockServer();
        frame.setPort(8040);
        log.info("创建socket服务端8040");
        frame.setOnServerlistener(new ServerListener() {
            @Override
            public void receivedata(final long id,String address,String info) throws IOException {
                if (info==null) {
                    frame.disconnect(id);
                    return;
                }
                WebSocket socket=new WebSocket();
                ReturnJson result=JSON_Util.getResult(info);
                if(result==null){
                    ReturnJson json=new ReturnJson(SocketCode.DATA_FORMAT_ERROR.getCode(),sdf.format(new Date()),"no",SocketCode.DATA_FORMAT_ERROR.getMessage(),"no","no");
                    frame.send(id, JSON_Util.setResult(json));
                    return;
                }
                if(StringUtils.isEmpty(result.getCode())|| StringUtils.isEmpty(result.getSn())||StringUtils.isEmpty(result.getContent())){
                    ReturnJson json=new ReturnJson(SocketCode.DATA_FORMAT_ERROR.getCode(),sdf.format(new Date()),"no",SocketCode.DATA_FORMAT_ERROR.getMessage(),"no","no");
                    frame.send(id, JSON_Util.setResult(json));
                    return;
                }
                //TODO 如果是锁端建立连接
                if(SocketCode.OPEN_CONNENT.getCode().equals(result.getCode())){
                    long socketlockid=getSocketIdByNum(result.getSn());
                    if (socketlockid!=0) { //如果存在编号相同的锁，直接把原来的踢掉，新的替换上
                        frame.disconnect(socketlockid);
                    }
                    locks.add(new LockBean(id, result.getSn(), -1, address));
                    ReturnJson json=new ReturnJson(SocketCode.OPEN_CONNENT_SUCCESS.getCode(),sdf.format(new Date()),result.getSn(),SocketCode.OPEN_CONNENT_SUCCESS.getMessage(),"no","no");
                    frame.send(id, JSON_Util.setResult(json));
                    socket.sendInfo(JSON_Util.setResult(json),result.getSn());
                    return;
                }

                //TODO 如果是请求开锁
                if(SocketCode.OPEN_LOCK.getCode().equals(result.getCode())){
                    //TODO 如果openCode是空的返回
                    if (StringUtils.isEmpty(result.getOpenCode())) {
                        ReturnJson json=new ReturnJson(SocketCode.DATA_FORMAT_ERROR.getCode(),sdf.format(new Date()),result.getSn(),SocketCode.DATA_FORMAT_ERROR.getMessage(),"no","no");
                        frame.send(id, JSON_Util.setResult(json));
                        delaydisc(id,5000);
                        return;
                    }
                    LockBean tlock=null;
                    for (LockBean lock : locks) {
                        if (result.getSn().equals(lock.getSn())) {
                            tlock=lock;
                            break;
                        }
                    }
                    //TODO 锁具尚未准备就绪！
                    if (tlock==null) {
                        ReturnJson json=new ReturnJson(SocketCode.OPEN_CONNENT_FILA.getCode(),sdf.format(new Date()),result.getSn(),SocketCode.OPEN_CONNENT_FILA.getMessage(),"no","no");
                        frame.send(id, JSON_Util.setResult(json));
                        delaydisc(id,5000);
                        socket.sendInfo(JSON_Util.setResult(json),result.getSn());
                        return;
                    }
                    //TODO 门已经开了！
                    if (tlock.getUserid()!=-1) {
                        ReturnJson json=new ReturnJson(SocketCode.OPEN_LOCK_FILA.getCode(),sdf.format(new Date()),result.getSn(),SocketCode.OPEN_LOCK_FILA.getMessage(),"no","no");
                        frame.send(id, JSON_Util.setResult(json));
                        delaydisc(id,5000);
                        socket.sendInfo(JSON_Util.setResult(json),result.getSn());
                        return;
                    }
                    //TODO 向锁端请求开锁
                    tlock.setUserid(id);
                    ReturnJson json=new ReturnJson(SocketCode.OPEN_LOCK.getCode(),sdf.format(new Date()),result.getSn(),SocketCode.OPEN_LOCK.getMessage(),result.getOpenCode(),"no");
                    frame.send(tlock.getSocketid(), JSON_Util.setResult(json));
                    new Thread(new Runnable() { //TODO 延迟3s后解锁
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(3000);
                                for (LockBean lock : locks) {
                                    if (lock.getSn().equals(result.getSn())) {
                                        lock.setUserid(-1);
                                        break;
                                    }
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    return;
                }
                //TODO 如果开锁成功
                if(SocketCode.OPEN_LOCK_SUCCCESS.getCode().equals(result.getCode())){
                    ReturnJson json=new ReturnJson(SocketCode.OPEN_LOCK_SUCCCESS.getCode(),sdf.format(new Date()),result.getSn(),SocketCode.OPEN_LOCK_SUCCCESS.getMessage(),"no","no");
                    frame.send (getUserSocketIdByLockSocketID(id), JSON_Util.setResult(json)); //根据锁socketID获取用户socketID
                    socket.sendInfo(JSON_Util.setResult(json),result.getSn());
                    return;
                }
                //TODO 如果开锁失败
                if(SocketCode.OPEN_LOCK_FILA.getCode().equals(result.getCode())){
                    ReturnJson json=new ReturnJson(SocketCode.OPEN_LOCK_FILA.getCode(),sdf.format(new Date()),result.getSn(),result.getContent(),"no","no");
                    frame.send (getUserSocketIdByLockSocketID(id), JSON_Util.setResult(json)); //根据锁socketID获取用户socketID
                    socket.sendInfo(JSON_Util.setResult(json),result.getSn());
                    return;
                }
                //TODO 如果闭锁成功
                if(SocketCode.UN_LOCK_SUCCCESS.getCode().equals(result.getCode())){
                    ReturnJson json=new ReturnJson(SocketCode.UN_LOCK_SUCCCESS.getCode(),sdf.format(new Date()),result.getSn(),SocketCode.UN_LOCK_SUCCCESS.getMessage(),"no","no");
                    frame.send (getUserSocketIdByLockSocketID(id), JSON_Util.setResult(json)); //根据锁socketID获取用户socketID
                    socket.sendInfo(JSON_Util.setResult(json),result.getSn());
                    return;
                }
                //TODO 如果闭锁失败
                if(SocketCode.UN_LOCK_FILA.getCode().equals(result.getCode())){
                    ReturnJson json=new ReturnJson(SocketCode.UN_LOCK_FILA.getCode(),sdf.format(new Date()),result.getSn(),result.getContent(),"no","no");
                    frame.send (getUserSocketIdByLockSocketID(id), JSON_Util.setResult(json)); //根据锁socketID获取用户socketID
                    socket.sendInfo(JSON_Util.setResult(json),result.getSn());
                    return;
                }
                //TODO 如果连接超时
                if(SocketCode.OPEN_CONNENT_TIMEOUT.getCode().equals(result.getCode())){
                    long socketlockid=getSocketIdByNum(result.getSn());
                    if (socketlockid!=0) { //如果存在编号相同的锁，直接把原来的踢掉，新的替换上
                        frame.disconnect(socketlockid);
                        return;
                    }
                }
                //TODO 如果是请求修改秘钥
                if(SocketCode.UPDATE_PSK.getCode().equals(result.getCode())){
                    LockBean tlock=null;
                    for (LockBean lock : locks) {
                        if (result.getSn().equals(lock.getSn())) {
                            tlock=lock;
                            break;
                        }
                    }
                    //TODO 锁具尚未准备就绪！
                    if (tlock==null) {
                        ReturnJson json=new ReturnJson(SocketCode.OPEN_CONNENT_FILA.getCode(),sdf.format(new Date()),result.getSn(),SocketCode.OPEN_CONNENT_FILA.getMessage(),"no","no");
                        frame.send(id, JSON_Util.setResult(json));
                        delaydisc(id,5000);
                        return;
                    }
                    //TODO 向锁端请求修改秘钥
                    tlock.setUserid(id);
                    ReturnJson json=new ReturnJson(SocketCode.UPDATE_PSK.getCode(),sdf.format(new Date()),result.getSn(),SocketCode.UPDATE_PSK.getMessage(),"no",result.getPsk());
                    frame.send(tlock.getSocketid(), JSON_Util.setResult(json));
                    socket.sendInfo(JSON_Util.setResult(json),result.getSn());
                    return;
                }
                //TODO 如果修改秘钥成功
                if(SocketCode.UPDATE_PSK_SUCCESS.getCode().equals(result.getCode())){
                    ReturnJson json=new ReturnJson(SocketCode.UPDATE_PSK_SUCCESS.getCode(),sdf.format(new Date()),result.getSn(),SocketCode.UPDATE_PSK_SUCCESS.getMessage(),"no","no");
                    frame.send (getUserSocketIdByLockSocketID(id), JSON_Util.setResult(json)); //根据锁socketID获取用户socketID
                    socket.sendInfo(JSON_Util.setResult(json),result.getSn());
                    return;
                }
                //TODO 如果修改秘钥失败
                if(SocketCode.UPDATE_PSK_FILA.getCode().equals(result.getCode())){
                    ReturnJson json=new ReturnJson(SocketCode.UPDATE_PSK_FILA.getCode(),sdf.format(new Date()),result.getSn(),result.getContent(),"no","no");
                    frame.send (getUserSocketIdByLockSocketID(id), JSON_Util.setResult(json)); //根据锁socketID获取用户socketID
                    socket.sendInfo(JSON_Util.setResult(json),result.getSn());
                    return;
                }
            }

            private long getSocketIdByNum(String sn) {
                for (LockBean lock : locks) {
                    if (lock.getSn().equals(sn)) {
                        return lock.getSocketid();
                    }
                }
                return 0;
            }

            @Override
            public void disconn(long id,String address) throws IOException {
                for (LockBean lock : locks) {
                    if (lock.getSocketid()==id) {
                        ReturnJson json=new ReturnJson(SocketCode.OPEN_CONNENT_FILA.getCode(),sdf.format(new Date()),lock.getSn(),SocketCode.OPEN_CONNENT_FILA.getMessage(),"no","no");
                        WebSocket socket=new WebSocket();
                        socket.sendInfo(JSON_Util.setResult(json),lock.getSn());
                        locks.remove(lock);
                        break;
                    }
                }
            }
            @Override
            public void conn(final long id,String address) {
                new Thread(new NotLock(frame,locks,id,20000)).start();
            }
        });
        frame.initSocket();
        //TODO 循环给锁具发送心跳包文
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					for (LockBean lock : locks) {
                        ReturnJson json=new ReturnJson(SocketCode.XIN_TIAO.getCode(),sdf.format(new Date()),lock.getSn(),SocketCode.XIN_TIAO.getMessage(),"","");
                        frame.send(lock.getSocketid(), JSON_Util.setResult(json));
					}
				}
			}
		}).start();
    }

    private void delaydisc(final long id, final long time) {
        new Thread(new MyApps(frame,id,time)).start();
    }

    private long getUserSocketIdByLockSocketID(long id) {
        for (LockBean lock : locks) {
            if (lock.getSocketid()==id) {
                return lock.getUserid();
            }
        }
        return 0;
    }
}
class MyApps implements Runnable {
    private WeakReference<JCIPLockServer> wframe;
    private long id,time;
    public MyApps(JCIPLockServer frame, long id, long time) {
        super();
        this.wframe = new WeakReference<JCIPLockServer>(frame);
        this.id = id;
        this.time = time;
    }
    @Override
    public void run() {
        try {
            Thread.sleep(time);
            if (wframe.get()!=null) {
                wframe.get().disconnect(id);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class MyJCIPLockDemo implements Runnable {
    @Override
    public void run() {
        new JCIPLockDemo().test();
    }
}

//TODO 将非锁具20s后直接踢掉
class NotLock implements Runnable {
    private WeakReference<JCIPLockServer> wframe;
    private WeakReference<List<LockBean>> wlocks;
    private long id, time;

    public NotLock(JCIPLockServer frame, List<LockBean> locks, long id, long time) {
        super();
        this.wframe = new WeakReference<JCIPLockServer>(frame);
        this.wlocks = new WeakReference<List<LockBean>>(locks);
        this.id = id;
        this.time = time;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(time);
            if (wframe.get() != null && wlocks.get() != null) {
                boolean notlock = true;
                for (LockBean lock : wlocks.get()) {
                    if (lock.getSocketid() == id) {
                        notlock = false;
                        break;
                    }
                }
                if (notlock) {
                    wframe.get().disconnect(id);
                }
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
