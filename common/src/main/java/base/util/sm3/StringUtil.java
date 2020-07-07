package base.util.sm3;

import java.util.regex.Pattern;
public class StringUtil {
	static final String CHARS="0123456789abcdef";
	/**
	 * 格式化字符串
	 *
	 * 例：formateString("xxx{0}bbb",1) = xxx1bbb
	 *
	 * @param str
	 * @param params
	 * @return
	 */
	public static String formateString(String str, String... params) {
		for (int i = 0; i < params.length; i++) {
			str = str.replace("{" + i + "}", params[i] == null ? "" : params[i]);
		}
		return str;
	}
	public static boolean isEmpty(String str){
		if("".equals(str)|| str==null){
			return true;
		}else{
			return false;
		}
	}

	public static boolean isNotEmpty(String str){
		if(!"".equals(str)&&str!=null){
			return true;
		}else{
			return false;
		}
	}
	public static String sm3(String s) { //拿到SM3加密过的数据后，计算8位开锁码
		StringBuilder sb1=new StringBuilder();
		StringBuilder sb2=new StringBuilder();
		for (int i = 0; i <s.length(); i++) {
			if (CHARS.indexOf(s.charAt(i))<10) {
				sb1.append(s.charAt(i));
			} else {
				sb2.append(CHARS.indexOf(s.charAt(i))-10);
			}
			if (sb1.length()>=8) {
				break;
			}
		}
		sb1.append(sb2);
		sb1.delete(8, sb1.length());
		return sb1.toString();
	}
	 public static boolean checkMac(String mac) {
	    	if (mac == null||mac.isEmpty()) {
	    		return false;
	    	}
	    	//正则校验MAC合法性
	    	return Pattern.compile("^[A-F0-9]{2}(:[A-F0-9]{2}){5}$").matcher(mac).find();
	    }
	 public static void main(String[] args) {
		 String opencode = StringUtil.sm3(SM3Digest.StringToSM3("123" +"456" + (System.currentTimeMillis() / 60000))); //+blemac.replace(":", "")
		 System.out.println(opencode);
	}
}
