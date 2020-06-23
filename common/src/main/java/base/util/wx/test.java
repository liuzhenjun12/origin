package base.util.wx;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class test {
    public static void main(String[] args) {
        String sToken = "nkY9OLaKm2CWC5xV";
        String sCorpID = "wwabb136cec3204033";
        String sEncodingAESKey = "IWuYHINjV9RRXQSjmF2TusYo0AGPm4SwCrgLPgi5AQx";
        String sVerifyMsgSig = "5c1932a53bae87894c0df1937881c33141981387";
        String sReqMsgSig = "ed979e538b81978b7e23208f29f764f8c7e5e212";
        // String sReqTimeStamp = HttpUtils.ParseUrl("timestamp");
        String sReqTimeStamp = "1591069462";
        // String sReqNonce = HttpUtils.ParseUrl("nonce");
        String sReqNonce = "1590929792";
        // post请求的密文数据
        // sReqData = HttpUtils.PostData();
        String sReqData = "<xml><SuiteId><![CDATA[wwf5decd0326a45209]]></SuiteId><AuthCode><![CDATA[iEYJ7wDnxE6-hqKz3Cfo8f2ADDhbo6qjZ0FAxYMbarg09rH8M70rrFeCrzV-WtDIeC2VBwoXgBSnftWchab3_Pu8z92b32E8ANDbDSp5Rdk]]></AuthCode><InfoType><![CDATA[create_auth]]></InfoType><TimeStamp>1591069439</TimeStamp></xml>";
        String XX="<xml><ToUserName><![CDATA[wwf5decd0326a45209]]></ToUserName><Encrypt><![CDATA[zuyPdrykQKxG9d7CDzG3z06Ogpge5lOgSNcGiLRf1/Ir/vNxc0KPB/249HO4uKPZbbduRZTqtwXgP5XgcijJN1OLlJb6WFpPJ8PnRAeluCeSXIa0j0ZYB4oKAwqzwmnrv9gSUn8YUldqKN1g0i0t+QpPu8nmAamcEBzU8rfR5/sZN7caPgHJ4uW43ZDxSGyRX4rrQjN15wbpRJ3W42PHO0s4vNiAY7vaIOhfMTOICyfEXGp6XwX1rmOUYyTYE8LwfP8zcvNFUoYOQWg7lnRYt58qFGqHyXdGsvmw9O8E61cuFW/En3vdFL8MyA2hH7yBBk2GD3xd9lpIaXeEq59142FP/cpeyW5sai5Xeyk9QWyo12bGnFdRDk6Or4J6IChusnbQgTYwqTZRxy3hfVwoR9QYsyGyQADy25P0kE7LvRw=]]></Encrypt><AgentID><![CDATA[1000106]]></AgentID></xml>";
        String X3=" <xml><SuiteId><![CDATA[wwf5decd0326a45209]]></SuiteId><InfoType><![CDATA[suite_ticket]]></InfoType><TimeStamp>1591069462</TimeStamp><SuiteTicket><![CDATA[CNfGjkA-2yLWNgg8V3HY4VLmc-OlgkFobefCQq6guUJIRgL5vdmhvCUn1V-FaCsx]]></SuiteTicket></xml> ";
        String sEchoStr; //需要返回的明文 1c2eae9dbeb8e90c44d65f1d4061f9c1244331c4
        //5c1932a53bae87894c0df1937881c33141981387
        try {
            WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(sToken, sEncodingAESKey, "ww0f89226772f32c77");
            String msgXml = wxcpt.DecryptMsg(sVerifyMsgSig, sReqTimeStamp, sReqNonce, XX);
            String InfoType = XmlUtil.readStringXmlOut(msgXml, "InfoType");
            log.info("InfoType==>{}",InfoType);
        } catch (AesException e) {
            e.printStackTrace();
        }
    }
}
