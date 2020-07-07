package udp.test;

import base.api.SocketCode;
import base.util.JSON_Util;
import base.vo.ReturnJson;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class JCIPLockDemo {

	private Socket s;
	private InputStream is;
	private InputStreamReader isr;
	private BufferedReader br;
	private OutputStream os;
	private PrintWriter pw;
	private final String ip="localhost";
	private final int port=8030;

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
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			ReturnJson json=new ReturnJson(SocketCode.OPEN_CONNENT.getCode(),sdf.format(new Date()),"515068000006",SocketCode.OPEN_CONNENT.getMessage(),"no","no");
			pw.println(JSON_Util.setResult(json));
			while(true) {
				//不停地读取从服务器端发来的信息
				String info=br.readLine();
				if (info==null) {
					closeAll();
					break;
				}
				JSONObject jsonObject = JSONObject.parseObject(info);
				String code=jsonObject.getString("code");
				if (code==null) {
					continue;
				}
				if(SocketCode.OPEN_LOCK.getCode().equals(code)){
					ReturnJson jso=new ReturnJson(SocketCode.OPEN_LOCK_FILA.getCode(),sdf.format(new Date()),"515068000006",SocketCode.OPEN_LOCK_FILA.getMessage(),"no","no");
					pw.println(JSON_Util.setResult(jso));
				}
				if(SocketCode.UPDATE_PSK.getCode().equals(code)){
					ReturnJson jso=new ReturnJson(SocketCode.UPDATE_PSK_SUCCESS.getCode(),sdf.format(new Date()),"515068000006",SocketCode.UPDATE_PSK_SUCCESS.getMessage(),"no","no");
					pw.println(JSON_Util.setResult(jso));
				}
				//TODO 如果是心跳包
				if(SocketCode.XIN_TIAO.getCode().equals(code)){
					log.info("收到心跳包:{}",code);
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
