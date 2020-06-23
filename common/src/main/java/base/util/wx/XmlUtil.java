package base.util.wx;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

public class XmlUtil {
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
    public static void main(String[] args) {
//		System.out.println(getStringXmlFromPath("E:\\java\\workspace\\menjin\\WebContent\\download\\update.xml"));
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<xml>\n" +
                "  <SuiteId><![CDATA[ww4asffe99e54c0f4c]]></SuiteId>\n" +
                "  <InfoType> <![CDATA[suite_ticket]]></InfoType>\n" +
                "  <TimeStamp>1403610513</TimeStamp>\n" +
                "    <SuiteTicket><![CDATA[asdfasfdasdfasdf]]></SuiteTicket>" +
                "</xml>";
        String SuiteId="SuiteId";
        String s=readStringXmlOut(xml,SuiteId);
        System.out.println(s);

    }

}
