package base.util;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.ReadFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class MinaClient {

	@SuppressWarnings("deprecation")
	public String startClient(InetSocketAddress address, long timeout, String sendMsg, String delLock) {
		// 创建客户端连接器.
		NioSocketConnector connector = new NioSocketConnector();
		connector.getFilterChain().addLast("logger", new LoggingFilter());
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8")))); // 设置编码过滤器
		connector.setConnectTimeoutMillis(10000);
		// connector.setHandler(new TimeClientHandler());//设置事件处理器

		SocketSessionConfig cfg = connector.getSessionConfig();
		// 如果是客户端同步处理服务端信息，而不是通过IoHandlerAdapter异步处理，必须设置如下属性useReadOperation为true
		cfg.setUseReadOperation(true);

		ConnectFuture cf = connector.connect(address);// 建立连接
		cf.awaitUninterruptibly();// 等待连接创建完成
		// session.getCloseFuture().awaitUninterruptibly();// 等待连接断开

		IoSession session = cf.getSession();

		// 发送
		session.write(sendMsg).awaitUninterruptibly();
		// 接收服务端信息，以下为同步处理方法，如果是异步需要到IoHandlerAdapter类中的messageReceived方法中处理来自服务端的异步信息
		ReadFuture readFuture = session.read();
		String result = null;
		if (readFuture.awaitUninterruptibly(timeout)) {
			result = readFuture.getMessage().toString();
		} else { // 读超时
			// 发送
			session.write(delLock).awaitUninterruptibly();
			result = "timeout";
		}
		// 断开
		session.close(false); // 为true时,IoSession将会立即被关闭。在WriteRequestQueue中排队的写请求将会被丢弃。为false时,所有在排队的消息被写入到操行系统缓存区,然后IoSession将会关闭。
		session.getService().dispose();
		connector.dispose();
		return result;
	}

	public static void main(String[] args) {
		System.out.println(new MinaClient().startClient(new InetSocketAddress("127.0.0.1", 8087), 5000, "{\"date\":\"20200706225706\",\"code\":\"1004\",\"openCode\":\"no\",\"psk\":\"123456\",\"sn\":\"515068000044\",\"content\":\"请求初始化设备\"}", "hello"));
	}
}
