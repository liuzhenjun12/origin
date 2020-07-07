package udp.nio;
import org.apache.mina.core.session.IoSession;

import java.io.IOException;

public interface TimeServerListener {
	void sessionCreated(IoSession session);
	void exceptionCaught(IoSession session, Throwable cause);
	void messageReceived(IoSession session, Object message) throws IOException;
	void sessionClosed(IoSession session) throws IOException;
}
