package udp.config;


import base.api.CommonResult;
import base.api.ResultCode;
import base.util.RedisOpsUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import udp.utils.WebsocketUtil;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/wss/id/{id}")
@Component
@Slf4j
public class WebSocket {
    /**静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。*/
    private static int onlineCount = 0;
    /**concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。*/
    private static ConcurrentHashMap<String,WebSocket> webSocketMap = new ConcurrentHashMap<>();
    /**与某个客户端的连接会话，需要通过它来给客户端发送数据*/
    private Session session;
    /**接收id*/
    private String id="";


    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session,@PathParam("id") String id) throws IOException {
        this.session = session;
        this.id=id;
        InetSocketAddress remoteAddress = WebsocketUtil.getRemoteAddress(session);
        if(webSocketMap.containsKey(id)){
            webSocketMap.remove(id);
            webSocketMap.put(id,this);
            //加入set中
        }else{
            webSocketMap.put(id,this);
            //加入set中
            addOnlineCount();
            //在线数加1
        }
        log.info("连接成功:"+id+",当前在线连接数为:" + getOnlineCount());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() throws IOException {
        log.info("关闭连接:{}",this.id);
        if(webSocketMap.containsKey(this.id)){
            webSocketMap.remove(this.id);
            //从set中删除
            subOnlineCount();
        }
        log.info("连接退出:"+id+",当前在线连接数为:" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        log.info("收到消息:"+id+",报文:"+message);
        if(!StringUtils.isEmpty(message)){
            for (WebSocket item : webSocketMap.values()) {
                if (item.id.equals("jinchu") ) {
                    log.info("发送消息到:"+id+"，报文:"+message);
                    item.session.getAsyncRemote().sendText(message);
                    continue;
                }
            }
        }
    }

    /**
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) throws IOException {
        error.printStackTrace();
        sendInfo(JSON.toJSONString(CommonResult.success(ResultCode.SOCKETERROR.getCode(),this.id,error.getMessage())),"jinchu");
        error.printStackTrace();
    }
    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 发送自定义消息
     * */
    public static void sendInfo(String message, String id) throws IOException {
        log.info("发送消息到:"+id+"，报文:"+message);
        if(StringUtils.isEmpty(message)){
            return;
        }
        for (WebSocket item : webSocketMap.values()) {
            if (item.id.equals("jinchu") ) {
                item.session.getAsyncRemote().sendText(message);
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocket.onlineCount--;
    }


}
