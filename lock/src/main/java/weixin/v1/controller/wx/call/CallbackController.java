package weixin.v1.controller.wx.call;


import base.constant.RedisKeyPrefixConst;
import base.util.RedisOpsUtil;
import base.util.wx.AesException;
import base.util.wx.WXBizMsgCrypt;
import base.util.wx.XmlUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import weixin.service.BaseConfigService;
import weixin.vo.BaseConfig;
import weixin.vo.InitLock;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/** 企业号 回调模式验证域名的请求位置 */
@RestController
@Slf4j
@EnableAsync
@RequestMapping("/wx/v1")
public class CallbackController {
    @Autowired
    BaseConfigService baseConfigService;
    @Autowired
    RedisOpsUtil redisOpsUtil;

    /**
     * 企业微信通信入口
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/wechatCorpValidUrl")
    @ApiOperation("企业微信GET访问")
    public void wechatCorpValidUrl(HttpSession session, HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
        BaseConfig baseConfig = redisOpsUtil.get(RedisKeyPrefixConst.BASE_CONFIG, BaseConfig.class);
        boolean isGet = request.getMethod().toLowerCase().equals("get");
        PrintWriter out = response.getWriter();
        WXBizMsgCrypt wxcpt;
        //TODO 微信加密签名
        String sVerifyMsgSig = request.getParameter("msg_signature");
        log.info("sVerifyMsgSig ==>{}", sVerifyMsgSig);
        //TODO 时间戳
        String sVerifyTimeStamp = request.getParameter("timestamp");
        log.info("sVerifyTimeStamp ==>{}", sVerifyTimeStamp);
        //TODO 随机数
        String sVerifyNonce = request.getParameter("nonce");
        log.info("sVerifyNonce ==>{}", sVerifyNonce);
        //TODO 随机字符串
        String sVerifyEchoStr = request.getParameter("echostr");
        log.info("sVerifyEchoStr ==>", sVerifyEchoStr);
        if (StringUtils.isEmpty(sVerifyMsgSig) || StringUtils.isEmpty(sVerifyTimeStamp)) {
            return;
        }
        if (isGet) {
            //TODO sEchoStr 需要返回的明文
            String sEchoStr;
            try {
                wxcpt = new WXBizMsgCrypt(baseConfig.getSToken(), baseConfig.getSEncodingAESKey(), baseConfig.getSCorpID());
                sEchoStr = wxcpt.VerifyURL(sVerifyMsgSig, sVerifyTimeStamp, sVerifyNonce, sVerifyEchoStr);
                log.info("sEchoStr==>{}", sEchoStr);
                out.print(sEchoStr);
                out.flush();
                out.close();
                out = null;
            } catch (AesException e) {
                log.error("发生错误==>{},{}", e.getMessage(), e.getCode());
            }
        } else {
            ServletInputStream sis = null;
            String xmlData = "";
            sis = request.getInputStream();
            int size = request.getContentLength();
            //TODO 用于缓存每次读取的数据
            byte[] buffer = new byte[size];
            //TODO 用于存放结果的数组
            byte[] xmldataByte = new byte[size];
            int count = 0;
            int rbyte = 0;
            //TODO 循环读取
            while (count < size) {
                //TODO 每次实际读取长度存于rbyte中
                rbyte = sis.read(buffer);
                for (int i = 0; i < rbyte; i++) {
                    xmldataByte[count + i] = buffer[i];
                }
                count += rbyte;
            }
            xmlData = new String(xmldataByte, "UTF-8");
            //TODO 获取到的suite_id,证明是企业微信后台推送
            String ToUserName = XmlUtil.readStringXmlOut(xmlData, "ToUserName");
            log.info("suite ==>{}", ToUserName);
            if (baseConfig.getSuiteID().equals(ToUserName)) {
                wxcpt = new WXBizMsgCrypt(baseConfig.getSToken(), baseConfig.getSEncodingAESKey(), ToUserName);
                String msgXml = wxcpt.DecryptMsg(sVerifyMsgSig, sVerifyTimeStamp, sVerifyNonce, xmlData);
                log.info("msgXml ==>{}", msgXml);
                if (!StringUtils.isEmpty(msgXml)) {
                    //TODO 通过解析xml获取消息类型
                    String InfoType = XmlUtil.readStringXmlOut(msgXml, "InfoType");
                    log.info("InfoType==>{}",InfoType);
                    //TODO 如果是应用凭证，企业微信服务器会定时（每十分钟）推送ticket。ticket会实时变更，并用于后续接口的调用。
                    if (!StringUtils.isEmpty(InfoType) && InfoType.equals("suite_ticket")) {
                        String suiteTicket = XmlUtil.readStringXmlOut(msgXml, "SuiteTicket");
                        if (!StringUtils.isEmpty(suiteTicket)) {
                            //TODO 缓存每10分钟，微信服务器发送的凭证
                            baseConfigService.set_suite_ticket(suiteTicket);
                            //TODO 缓存第三方应用凭证
                            baseConfigService.get_suite_access_token();
                            //TODO 开始获取预授权码pre_auth_code
                            baseConfigService.get_pre_auth_code();
                            //TODO 开始设置授权配置
                            baseConfigService.SetSessionInfo("1");
                            //TODO 设置服务器token
                            baseConfigService.get_provider_token();
                        }
                    }else if(!StringUtils.isEmpty(InfoType)&&InfoType.equals("create_auth")){
                        //TODO 企业微信后台推送授权成功通知
                        String authCode=XmlUtil.readStringXmlOut(msgXml,"AuthCode");
                        if(!StringUtils.isEmpty(authCode)){
                            //TODO 是添加授权事件
                            baseConfigService.get_permanent_code(authCode);
                        }
                    }else if(!StringUtils.isEmpty(InfoType)&&InfoType.equals("cancel_auth")){
                        //TODO 是删除授权事件
                        String authCorpId=XmlUtil.readStringXmlOut(msgXml,"AuthCorpId");//授权方企业的corpid
                        if(!StringUtils.isEmpty(authCorpId)) {
                            redisOpsUtil.publish(RedisKeyPrefixConst.MSG_SN,authCorpId+",del");
                        }
                    }else if(!StringUtils.isEmpty(InfoType)&&InfoType.equals("change_auth")){
                        //TODO 是更改授权事件
                        String authCorpId=XmlUtil.readStringXmlOut(msgXml,"AuthCorpId");
                        if(!StringUtils.isEmpty(authCorpId)){
                            redisOpsUtil.publish(RedisKeyPrefixConst.MSG_SN,authCorpId+",change");
                        }
                    }

                }
            }else {
                //TODO 是其他事件
                wxcpt = new WXBizMsgCrypt(baseConfig.getSToken(), baseConfig.getSEncodingAESKey(), ToUserName);
                String msgXml = wxcpt.DecryptMsg(sVerifyMsgSig, sVerifyTimeStamp, sVerifyNonce, xmlData);
                if (!StringUtils.isEmpty(msgXml)) {
                    String InfoType = XmlUtil.readStringXmlOut(msgXml, "InfoType");
                    String fromUserName =XmlUtil.readStringXmlOut(msgXml,"FromUserName");
                    if(!StringUtils.isEmpty(InfoType)){
                        String AuthCorpId = XmlUtil.readStringXmlOut(msgXml, "AuthCorpId");
                        String DeviceSn = XmlUtil.readStringXmlOut(msgXml, "DeviceSn");
                        if("add_device".equals(InfoType)){
                            //TODO 添加设备事件
                            String RemarkName = XmlUtil.readStringXmlOut(msgXml, "RemarkName");
                            String ModelId = XmlUtil.readStringXmlOut(msgXml, "ModelId");
                            InitLock lock=new InitLock();
                            lock.setAuthCorpId(AuthCorpId);
                            lock.setDeviceSn(DeviceSn);
                            lock.setRemarkName(RemarkName);
                            lock.setModelId(ModelId);
                            lock.setStatus(false);
                            redisOpsUtil.set(RedisKeyPrefixConst.INIT_LOCK+AuthCorpId,lock,60, TimeUnit.SECONDS);
                            redisOpsUtil.publish(RedisKeyPrefixConst.MSG_SN,AuthCorpId+",init");
                        }else if("del_device".equals(InfoType)){
                            //TODO 删除设备事件
                            redisOpsUtil.publish(RedisKeyPrefixConst.MSG_SN,AuthCorpId+",unlock,"+DeviceSn);
                        }else if("remark_device_name".equals(InfoType)){
                            //TODO 修改设备名称事件
                            String RemarkName = XmlUtil.readStringXmlOut(msgXml, "RemarkName");
                            redisOpsUtil.publish(RedisKeyPrefixConst.MSG_SN,DeviceSn+",uplock,"+RemarkName);
                        }else if("error_report".equals(InfoType)){
                            //TODO 设备报错事件
                            String ErrCode = XmlUtil.readStringXmlOut(msgXml, "ErrCode");
                            String ErrMsg = XmlUtil.readStringXmlOut(msgXml, "ErrMsg");
                            log.info("设备事件发生错误==>{},{},{}",DeviceSn,ErrCode,ErrMsg);
                        }else if("connect_info".equals(InfoType)){
                            //TODO 设备连接网络成功事件
                            redisOpsUtil.publish(RedisKeyPrefixConst.MSG_SN,AuthCorpId+",connect,"+DeviceSn);
                        }else if("disconnect_info".equals(InfoType)){
                            //TODO 设备断开网络事件
                            redisOpsUtil.publish(RedisKeyPrefixConst.MSG_SN,AuthCorpId+",disconnect,"+DeviceSn);
                        }
                    }
                }
            }
            //TODO 不管任何情况都返回成功
            out.print("success");
            out.flush();
            out.close();
        }
    }
}


