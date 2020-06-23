package com.nio;
import org.apache.mina.core.session.IoSession;

public interface TimeServerListener {
	void sessionCreated(IoSession session);
	void exceptionCaught(IoSession session, Throwable cause);
	void messageReceived(IoSession session, Object message);
	void sessionClosed(IoSession session);
}
