package com.nio;

public interface ServerListener {
	void conn(long id, String address);
	void receivedata(long id, String address, String info);
	void disconn(long id, String address);
}
