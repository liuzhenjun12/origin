package com.nio;

import com.alibaba.fastjson.JSONObject;
import com.test.JCIPLockDemo;
import com.vo.LockBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.ref.WeakReference;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

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
        frame.setPort(8087);
        frame.setOnServerlistener(new ServerListener() {
            @Override
            public void receivedata(final long id,String address,String info) {
                if (info==null) {
                    frame.disconnect(id);
                    return;
                }
                log.info("info:{}",info);
                JSONObject jsonObject = JSONObject.parseObject(info);
                log.info(jsonObject.getString("msg"));

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
            public void disconn(long id,String address) {
                for (LockBean lock : locks) {
                    if (lock.getSocketid()==id) {
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
        //循环给锁具发送心跳包文
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
						frame.send(lock.getSocketid(),"<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><code>1004</code><Date>"+sdf.format(new Date())+"</Date></root>");
					}
				}
			}
		}).start();
//        new Thread(new MyJCIPLockDemo()).start();
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
class MyJCIPLockDemo implements Runnable {
    @Override
    public void run() {
        new JCIPLockDemo().test();
    }
}
/*将非锁具20s后直接踢掉*/
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
