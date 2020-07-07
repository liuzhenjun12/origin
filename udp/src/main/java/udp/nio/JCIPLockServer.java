package udp.nio;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import udp.config.WebSocket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

public class JCIPLockServer {

	private static final long serialVersionUID = 1L;
	private IoAcceptor acceptor;
	private ServerListener serverlistener;
	public void setOnServerlistener(ServerListener serverlistener) {
		this.serverlistener = serverlistener;
	}
    private int port;
	public void setPort(int port) {
		this.port = port;
	}
	public void initSocket() {
		// 创建服务端监控线程
        acceptor = new NioSocketAcceptor();
        acceptor.getSessionConfig().setReadBufferSize(2048);
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
        // 设置编码过滤器
        acceptor.getFilterChain().addLast("codec",new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
        // 指定业务逻辑处理器
        TimeServerHandler handler=new TimeServerHandler();
        handler.setListener(new TimeServerListener() {
			@Override
			public void sessionCreated(IoSession session) {
				String remoteAddress=session.getRemoteAddress().toString();
				System.out.println(remoteAddress+"-->已连接");
				serverlistener.conn(session.getId(),remoteAddress);
			}
			@Override
			public void sessionClosed(IoSession session) throws IOException {
				System.out.println(session.getRemoteAddress().toString()+"-->已断开");
				WebSocket socket=new WebSocket();
				serverlistener.disconn(session.getId(),session.getRemoteAddress().toString());
			}
			@Override
			public void messageReceived(IoSession session, Object message) throws IOException {
				String strMsg = message.toString();
				System.out.println(strMsg+"\n");
				if (strMsg.trim().equalsIgnoreCase("quit")) {
					session.close(true);
					return;
				}
				serverlistener.receivedata(session.getId(),session.getRemoteAddress().toString(),strMsg);
			}
			@Override
			public void exceptionCaught(IoSession session, Throwable cause) {
		        cause.printStackTrace();
			}

		});
        acceptor.setHandler(handler);
        try {
        	// 设置端口号
			acceptor.bind(new InetSocketAddress(port));
			// 启动监听线程
			acceptor.bind();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/*断开*/
	@SuppressWarnings("deprecation")
	public void disconnect(long id) {
		IoSession session=acceptor.getManagedSessions().get(id);
		if (session!=null) {
			session.close(true);
		}
	}
	/*向指定id发送消息*/
	public void send(long id,String info) {
		if (acceptor.getManagedSessions().containsKey(id)) {
			acceptor.getManagedSessions().get(id).write(info);
		}
	}



	private void close() {
		acceptor.dispose();
	}

	public void destroy() {
		acceptor.dispose();
		System.exit(0);
	}
}
