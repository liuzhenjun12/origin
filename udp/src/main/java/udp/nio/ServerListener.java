package udp.nio;

import java.io.IOException;

public interface ServerListener {
	void conn(long id, String address);
	void receivedata(long id, String address, String info) throws IOException;
	void disconn(long id, String address) throws IOException;
}
