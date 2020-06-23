package com.test;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
@Slf4j
public class JCIPLockDemo {
	
	private Socket s;
	private InputStream is;
	private InputStreamReader isr;
	private BufferedReader br;
	private OutputStream os;
	private PrintWriter pw;
	private final String ip="localhost";
	private final int port=8087;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new JCIPLockDemo().test();
	}

	public void test() {
		try {
			s = new Socket(ip,port);
			is=s.getInputStream();
			isr=new InputStreamReader(is);
			br=new BufferedReader(isr);
			os=s.getOutputStream();
			pw=new PrintWriter(os,true);
			//System.out.println("已连接服务器");
			pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><code>1000</code><sn>515068000043</sn></root>");
			while(true) {
				//不停地读取从服务器端发来的信息
				String info=br.readLine();
				if (info==null) {
					//System.out.println("服务器断开14");
					closeAll();
					break;
				}
				//System.out.println("515069000014-收到消息："+info);
				//<?xml version="1.0" encoding="UTF-8"?><root><TransCode>1001</TransCode><TransName>LockStatus</TransName><LockId>515069000014</LockId></root>
				String transcode= com.nio.utils.Dom4jXmlUtil.readStringXmlOut(info,"code");
				if (transcode==null) {
					continue;
				}
				//System.out.println(transcode);
				switch (Integer.parseInt(transcode)) {
					case 1001 :{
					//System.out.println("开锁成功");
					pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><code>1001</code><sn>515068000043</sn><Return>0</Return></root>");
					try {
						Thread.sleep(5000);
						//System.out.println("关锁成功");
						pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><code>1002</code><sn>515068000043</sn></root>");
						break;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
					case 1004 :{
						String re = com.nio.utils.Dom4jXmlUtil.readStringXmlOut(info,"Date");
						log.info("收到服务端信息 ==>{}",re);
						break;
					}
				default:
					break;
				}
			}
		} catch (UnknownHostException e1) {
			//System.out.println(e1.getLocalizedMessage());
			e1.printStackTrace();
			closeAll();
		} catch (IllegalArgumentException e1) {
			//System.out.println(e1.getLocalizedMessage());
			e1.printStackTrace();
			closeAll();
		} catch (IOException e1) {
			if ("Connection refused: connect".equals(e1.getLocalizedMessage())) {
				//System.out.println(e1.getLocalizedMessage());
				closeAll();
			} else if ("Socket closed".equals(e1.getLocalizedMessage())) {
				//System.out.println(e1.getLocalizedMessage());
				closeAll();
			} else {
				//System.out.println(e1.getLocalizedMessage());
				e1.printStackTrace();
				closeAll();
			}
		}
	}

	private void closeAll(){
		if (pw!=null) {
			pw.close();
		}
		try {
			if (os!=null) {
				os.close();
			}
			if (br!=null) {
				br.close();
			}
			if (isr!=null) {
				isr.close();
			}
			if (is!=null) {
				is.close();
			}
			if (s!=null) {
				s.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
