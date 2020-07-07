package weixin.v1.controller.wx.yewu;

import base.api.CommonResult;
import base.constant.RedisKeyPrefixConst;
import base.exception.BusinessException;
import base.util.RedisOpsUtil;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import weixin.mapper.SysLogMapper;
import weixin.mapper.SysUserMapper;
import weixin.model.SysLog;
import weixin.model.SysUser;
import weixin.model.SysUserExample;
import weixin.service.BaseConfigService;
import weixin.vo.XiaoLoginInfo;
import weixin.vo.userinfo.WxLoginInfo;
import java.nio.charset.Charset;
import java.util.Date;

@RestController
@Slf4j
@RequestMapping("/wx/v1")
public class LoginController {
    @Autowired
    BaseConfigService baseConfigService;
    @Autowired
    RedisOpsUtil redisOpsUtil;
    @Autowired
    SysUserMapper userMapper;
    @Autowired
    SysLogMapper logMapper;

    /**
     * 小程序登录
     * @param code
     * @return
     * @throws BusinessException
     */
    @RequestMapping("/qy/login")
    public CommonResult login(@RequestParam String code) throws BusinessException {
        log.info("回调获取个人信息==>{}",code);
        String suite_access_token=baseConfigService.get_suite_access_token();
        if(suite_access_token==null){
            return CommonResult.failed("suite_access_token 不存在");
        }
        try {
            String    str = Request.Get("https://qyapi.weixin.qq.com/cgi-bin/service/miniprogram/jscode2session?suite_access_token="+suite_access_token+"&js_code="+code+"&grant_type=authorization_code")
                    .execute()
                    .returnContent()
                    .asString(Charset.forName("UTF-8"));
            Gson gson = new Gson();
            XiaoLoginInfo user=gson.fromJson(str, XiaoLoginInfo.class);
            log.info("user==>{}",user);
            if(user!=null){
                String CorpId=user.getCorpid();
                String UserId=user.getUserid();
                if(redisOpsUtil.hasKey(RedisKeyPrefixConst.USER_INFO+CorpId+":"+UserId)){
                    SysUser use=redisOpsUtil.get(RedisKeyPrefixConst.USER_INFO+CorpId+":"+UserId,SysUser.class);
                    //TODO 记录日志
                    SysLog sysLog = new SysLog(UserId, CorpId, "LOGIN",
                            "LoginController.index", "企业微信登录", true, "登录成功",
                            new Date(),RedisKeyPrefixConst.JINCHU_IMG,RedisKeyPrefixConst.JIN_CHU);
                    logMapper.insert(sysLog);
                    return CommonResult.success(use);
                }else {
                    SysUserExample userExample=new SysUserExample();
                    userExample.createCriteria().andCorpidsEqualTo(CorpId).andLoginIdEqualTo(UserId);
                    SysUser user1=userMapper.selectOneByExample(userExample);
                    if(user1!=null){
                        //TODO 记录日志
                        SysLog sysLog = new SysLog(user1.getLoginId(), user1.getCorpids(), "LOGIN",
                                "LoginController.index", "企业微信登录", true, "登录成功",
                                new Date(),RedisKeyPrefixConst.JINCHU_IMG,RedisKeyPrefixConst.JIN_CHU);
                        logMapper.insert(sysLog);
                        return CommonResult.success(user1);
                    }else {
                        return CommonResult.failed("用户账号无法登录");
                    }
                }
            }else {
                return CommonResult.failed("用户账号无法登录");
            }
        } catch (Exception e) {
            return CommonResult.failed(e.getCause(),e.getMessage());
        }
    }
}
