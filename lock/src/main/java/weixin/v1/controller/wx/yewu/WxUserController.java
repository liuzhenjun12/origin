package weixin.v1.controller.wx.yewu;

import base.api.CommonResult;
import base.constant.RedisKeyPrefixConst;
import base.util.File_Util;
import base.util.RedisOpsUtil;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import weixin.mapper.SysTodoMapper;
import weixin.mapper.SysUserMapper;
import weixin.model.SysTodoExample;
import weixin.model.SysUser;
import weixin.model.SysUserExample;
import weixin.vo.userinfo.UserInfo;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/wx/v1")
public class WxUserController {
    @Autowired
    SysUserMapper userMapper;
    @Autowired
    RedisOpsUtil redisOpsUtil;
    @Autowired
    SysTodoMapper todoMapper;

    @Value("${file.uploadFolder}")
    private String uploadFolder;
    private String staticAccessPath = "/upload/";
    /**
     * 获取微信头像
     * @return
     */
    @RequestMapping("/updateAvatar")
    public CommonResult updateAvatar(String cropid,String userid,String avatar){
        SysUserExample userExample = new SysUserExample();
        userExample.createCriteria().andCorpidsEqualTo(cropid).andLoginIdEqualTo(userid);
        SysUser user=userMapper.selectOneByExample(userExample);
        if(user!=null){
            user.setImg(avatar);
            userMapper.updateByPrimaryKey(user);
            redisOpsUtil.set(RedisKeyPrefixConst.USER_INFO+user.getCorpids()+":"+user.getLoginId(),user);
            return CommonResult.success(null);
        }else {
            return CommonResult.failed();
        }
    }
    /**
     * 修改用户头像
     * @return
     */
    @RequestMapping("/updateTou")
    public String updateTou(HttpServletRequest request, @RequestParam(value = "file", required = false) MultipartFile file) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        String cropid = request.getParameter("cropid");
        String userid = request.getParameter("userid");
        log.info("corpid:{},userid:{}",cropid,userid);
        if(StringUtils.isEmpty(cropid)||StringUtils.isEmpty(userid)){
            return "error";
        }
        SysUserExample userExample = new SysUserExample();
        userExample.createCriteria().andCorpidsEqualTo(cropid).andLoginIdEqualTo(userid);
        SysUser user=userMapper.selectOneByExample(userExample);
        if(user!=null){
            if(!file.isEmpty()) {
                String fileStr = File_Util.inputUploadFile(file, uploadFolder,user.getLoginId()+"_"+System.currentTimeMillis());
                if(fileStr.equals("NOT_IMAGE")){
                    return "error";
                }else {
                    String url="https://safe.jinchu.com.cn"+staticAccessPath+ fileStr;
                    user.setImg(url);
                    user.setLastupdatetime(new Date());
                    userMapper.updateByPrimaryKey(user);
                    redisOpsUtil.set(RedisKeyPrefixConst.USER_INFO+user.getCorpids()+":"+user.getLoginId(),user);
                    return url;
                }
            }else {
                return "error";
            }
        }else {
            return "error";
        }
    }

    /**
     * 修改用户昵称
     * @param cropid
     * @param userid
     * @param name
     * @return
     */
    @RequestMapping("/updateUserName")
    public CommonResult updateUserName(String cropid,String userid,String name){
        SysUserExample userExample = new SysUserExample();
        userExample.createCriteria().andCorpidsEqualTo(cropid).andLoginIdEqualTo(userid);
        SysUser user=userMapper.selectOneByExample(userExample);
        if(user!=null){
            user.setName(name);
            userMapper.updateByPrimaryKey(user);
            redisOpsUtil.set(RedisKeyPrefixConst.USER_INFO+user.getCorpids()+":"+user.getLoginId(),user);
            return CommonResult.success(null);
        }else {
            return CommonResult.failed();
        }
    }

    /**
     * 用户绑定手机
     * @param cropid
     * @param userid
     * @param phone
     * @return
     */
    @RequestMapping("/updateUserPhone")
    public CommonResult updateUserPhone(String cropid,String userid,String phone){
        SysUserExample userExample = new SysUserExample();
        userExample.createCriteria().andCorpidsEqualTo(cropid).andLoginIdEqualTo(userid);
        SysUser user=userMapper.selectOneByExample(userExample);
        if(user!=null){
            user.setPhone(phone);
            userMapper.updateByPrimaryKey(user);
            redisOpsUtil.set(RedisKeyPrefixConst.USER_INFO+user.getCorpids()+":"+user.getLoginId(),user);
            return CommonResult.success(null);
        }else {
            return CommonResult.failed();
        }
    }

    /**
     * 通过企业id获取用户数量
     * @param cropid
     * @return
     */
    @RequestMapping("/getUserCountBycorpid")
    public CommonResult getUserCountBycorpid(String cropid){
        try {
            SysUserExample userExample = new SysUserExample();
            userExample.createCriteria().andCorpidsEqualTo(cropid);
            int count =userMapper.selectCountByExample(userExample);
            return CommonResult.success(count);
        }catch (Exception e){
            return CommonResult.failed(e.getCause(),e.getMessage());
        }
    }

    /**
     * 通过企业id获取用户列表
     * @param cropid
     * @return
     */
    @RequestMapping("/getUsersBycorpid")
    public CommonResult getUsersBycorpid(String cropid){
        try {
        SysUserExample userExample = new SysUserExample();
        userExample.createCriteria().andCorpidsEqualTo(cropid);
        List<SysUser> userList=userMapper.selectByExample(userExample);
        if(!userList.isEmpty()){
            List<UserInfo> userInfos=new ArrayList<UserInfo>();
            for(SysUser s:userList){
                UserInfo info=new UserInfo();
                info.setUserid(s.getLoginId());
                info.setName(s.getName());
                info.setAvatar(s.getImg());
                info.setRole(s.getRoleId().toString());
                userInfos.add(info);
            }
            return CommonResult.success(userInfos);
        }else {
            return CommonResult.failed();
        }
        }catch (Exception e){
            return CommonResult.failed(e.getCause(),e.getMessage());
        }
    }

    /**
     * 判断手机号码是否绑定过
     * @param phone
     * @return
     */
    @RequestMapping("/isPhone")
    public CommonResult isPhone(String phone){
        SysUserExample userExample = new SysUserExample();
        userExample.createCriteria().andPhoneEqualTo(phone);
        List<SysUser> userList=userMapper.selectByExample(userExample);
        if(userList.isEmpty()){
            return CommonResult.success(null);
        }else {
            return CommonResult.failed();
        }
    }

    /**
     * 发送短信验证码
     * @param phone
     * @return
     * @throws ClientException
     */
    @RequestMapping("/sendXin")
    public CommonResult sendXin(String phone) throws ClientException {
        //设置超时时间-可自行调整
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化ascClient需要的几个参数
        final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
        final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
        //替换成你的AK
        final String accessKeyId = "LTAI7rqjPyazNUwK";//你的accessKeyId,参考本文档步骤2
        final String accessKeySecret = "vZejKETI8BG5RMftWoFMZwCKbrHuxq";//你的accessKeySecret，参考本文档步骤2
        //初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
                accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        //使用post提交
        request.setMethod(MethodType.POST);
        //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为国际区号+号码，如“85200000000”
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("金储智能");
        //必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
        request.setTemplateCode("SMS_160490612");
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        int c=(int)((Math.random()*9+1)*100000);
        request.setTemplateParam("{\"code\":\""+c+"\"}");
        //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");
        //请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
            return CommonResult.success(c);
        }else{
            return CommonResult.failed(sendSmsResponse.getMessage());
        }
    }

    /**
     * 通过用户id获取用户信息
     * @param corpid
     * @param userid
     * @return
     */
    @RequestMapping("/getUserinfoByuserid")
    public CommonResult getUserinfoByuserid(String corpid,String userid){
        try {
            SysUserExample userExample = new SysUserExample();
            userExample.createCriteria().andCorpidsEqualTo(corpid).andLoginIdEqualTo(userid);
            SysUser user=userMapper.selectOneByExample(userExample);
            SysTodoExample todoExample=new SysTodoExample();
            todoExample.createCriteria().andCorpIdEqualTo(corpid).andUserIdEqualTo(userid);
            int count=todoMapper.selectCountByExample(todoExample);
            user.setAttr5(count);
            return CommonResult.success(user);
        }catch (Exception e){
            return CommonResult.failed();
        }
    }

}
