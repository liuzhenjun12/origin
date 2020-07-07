package udp.nio;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import java.io.IOException;

/**
 * 服务器端业务逻辑
 */
public class TimeServerHandler extends IoHandlerAdapter {


    private TimeServerListener listener;
	public void setListener(TimeServerListener listener) {
		this.listener = listener;
	}
    /**
     * 连接创建事件
     */
    @Override
    public void sessionCreated(IoSession session) {
    	listener.sessionCreated(session);
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) {
    	String msg=cause.getLocalizedMessage();
    	if (msg != null&&!"远程主机强迫关闭了一个现有的连接。".equals(cause.getLocalizedMessage())) {
    		listener.exceptionCaught(session, cause);
		}
    }

    /**
     * 消息接收事件
     */
	@Override
    public void messageReceived(IoSession session, Object message) throws IOException {
		System.out.println(message.toString()+"-->消息接收事件");
    	listener.messageReceived(session, message);
    }

	@Override
	public void sessionClosed(IoSession session) throws IOException {
		try {
			super.sessionClosed(session);
		} catch (Exception e) {
			e.printStackTrace();
		}
		listener.sessionClosed(session);
	}

}
