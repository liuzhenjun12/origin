package com.nio.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;

/**
 * @description xml工具类
 */
public class Dom4jXmlUtil {

	/**
	 * @description 将xml文件转换成字符串
	 * @return elementText
	 */
	public static String getStringXmlFromPath(String xmlpath,String apppath) {
    	File xmlfile=new File(xmlpath);
		if (!xmlfile.exists()||xmlfile.isDirectory()) {
			return null;
		}
        File appfile=new File(apppath);
        if (!appfile.exists()||appfile.isDirectory()) {
        	return null;
        }
		try {
			SAXReader reader=new SAXReader();
			Document document=reader.read(xmlfile);
			Element root=document.getRootElement();
			Element lengthElem=root.element("filelength");
			lengthElem.setText(String.valueOf(appfile.length()));
			Element md5Elem=root.element("filemd5");
			md5Elem.setText(FileUtil.getFileMD5(appfile));
			return document.asXML();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @description 将xml字符串转换成map
	 * @param xml,nodeName
	 * @return elementText
	 */
	public static String readStringXmlOut(String xml, String nodeName) {
		if (xml == null || xml.trim().equals("")) {
			return null;
		}
		try {
			return DocumentHelper.parseText(xml).getRootElement().elementTextTrim(nodeName.trim());
		} catch (DocumentException e) {
			// System.out.println("节点不存在");
		}
		return null;
	}
	
//	public static void main(String[] args) {
//		System.out.println(getStringXmlFromPath("E:\\java\\workspace\\menjin\\WebContent\\download\\update.xml"));
//	}
}
