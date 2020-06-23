package weixin.impl;

import base.api.CommonResult;
import base.constant.RedisKeyPrefixConst;
import base.exception.BusinessException;
import base.util.RedisOpsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import weixin.mapper.SysCorpMapper;
import weixin.mapper.SysDepartMapper;
import weixin.mapper.SysLogMapper;
import weixin.mapper.SysUserMapper;
import weixin.model.*;
import weixin.service.BaseConfigService;
import weixin.vo.*;
import org.apache.http.client.fluent.Request;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.http.entity.ContentType;
import com.google.gson.Gson;
import weixin.vo.department.Depart;
import weixin.vo.permanentCode.DealerCorpInfo;
import weixin.vo.userxiangxi.UserXI;

@Service("baseConfigService")
@Slf4j
public class BaseConfigServiceImpl implements BaseConfigService {
    @Autowired
    RedisOpsUtil redisOpsUtil;
    @Autowired
    SysUserMapper userMapper;
    @Autowired
    SysCorpMapper corpMapper;
    @Autowired
    SysDepartMapper departMapper;
    @Autowired
    SysLogMapper logMapper;

    /**
     * 缓存基本配置
     * @param baseConfig
     */
    @Override
    public void set_base_config(BaseConfig baseConfig) {
        redisOpsUtil.set(RedisKeyPrefixConst.BASE_CONFIG,baseConfig);
    }

    /**
     * 缓存每10分钟，微信服务器发送的凭证
     * @param suiteTicket
     */
    @Override
    public void set_suite_ticket(String suiteTicket) {
       redisOpsUtil.set(RedisKeyPrefixConst.SUITE_TICKET,suiteTicket,600, TimeUnit.SECONDS);
    }

    /**
     * 缓存第三方应用凭证
     */
    @Override
    public String get_suite_access_token() throws BusinessException {
        /**
         * 从缓存中获取
         */
        if(redisOpsUtil.hasKey(RedisKeyPrefixConst.SUITE_ACCESS_TOKEN)){
            return redisOpsUtil.get(RedisKeyPrefixConst.SUITE_ACCESS_TOKEN);
        }else {
            /**
             * 从外网获取
             * 通过suiteTicket去获取，如果suiteTicket不存在直接返回空字符串
             */
            String suiteTicket=redisOpsUtil.get(RedisKeyPrefixConst.SUITE_TICKET);
            if(StringUtils.isEmpty(suiteTicket)){
                throw new BusinessException("suiteTicket不存在");
            }else {
                SuiteAccesstoken suiteAccesstoken=get_suite_access_token_gateway(suiteTicket);
                if(suiteAccesstoken==null){
                    throw new BusinessException("suiteAccesstoken不存在");
                }else {
                    redisOpsUtil.set(RedisKeyPrefixConst.SUITE_ACCESS_TOKEN,suiteAccesstoken.getSuite_access_token(),2,TimeUnit.HOURS);
                    return suiteAccesstoken.getSuite_access_token();
                }
            }
        }
    }
    /**
     * 获取预授权码
     * @param
     * @return
     */
    @Override
    public String get_pre_auth_code() throws BusinessException {
        /**
         * 从缓存中获取
         */
        if(redisOpsUtil.hasKey(RedisKeyPrefixConst.PRE_AUTH_CODE)){
            return redisOpsUtil.get(RedisKeyPrefixConst.PRE_AUTH_CODE);
        }else {
        /**
         * 从外网获取
         * 通过suiteTicket去获取，如果suiteTicket不存在直接返回空字符串
         */
        PreAuthCode preAuth=get_pre_auth_code_gateway();
        if(preAuth!=null){
            redisOpsUtil.set(RedisKeyPrefixConst.PRE_AUTH_CODE,preAuth.getPre_auth_code(),1200,TimeUnit.SECONDS);
            return preAuth.getPre_auth_code();
        }else {
           throw new BusinessException("网络异常");
        }
        }
    }
    /**
     * 设置授权配置
     * @param auth_type 授权类型：0 正式授权， 1 测试授权。
     * @return
     */
    @Override
    public String SetSessionInfo(String auth_type) throws BusinessException {
        String accesstoken=redisOpsUtil.get(RedisKeyPrefixConst.SUITE_ACCESS_TOKEN);
        String preAuthCode=redisOpsUtil.get(RedisKeyPrefixConst.PRE_AUTH_CODE);
        if(StringUtils.isEmpty(accesstoken)||StringUtils.isEmpty(preAuthCode)){
           throw new BusinessException("accesstoken、preAuthCode不存在");
        }
        StringBuffer strb = new StringBuffer();
        strb.append("{");
        strb.append("\"pre_auth_code\": \""+preAuthCode+"\",");
        strb.append("\"session_info\": { ");
        strb.append("\"appid\": [] ");
        strb.append("\"auth_type\": "+auth_type+" ");
        strb.append(" } ");
        strb.append(" } ");
        try {
        String  str = Request.Post("https://qyapi.weixin.qq.com/cgi-bin/service/set_session_info?suite_access_token="+accesstoken)
                    .bodyString(strb.toString(), ContentType.APPLICATION_JSON)
                    .execute()
                    .returnContent()
                    .asString(Charset.forName("UTF-8"));
        return "ok";
        } catch (IOException e) {
            throw new BusinessException(e.getMessage(),e.getCause());
        }
    }

    /**
     * 进入换取授权方的永久授权码
     * @param auth_code
     * @return
     */
    @Override
    public String get_permanent_code(String auth_code) throws BusinessException {
        String accesstoken=redisOpsUtil.get(RedisKeyPrefixConst.SUITE_ACCESS_TOKEN);
        if(StringUtils.isEmpty(accesstoken)){
            throw new BusinessException("SUITE_ACCESS_TOKEN 不存在");
        }
        DealerCorpInfo dealerCorpInfo = null;
        String str = null;
        StringBuffer strb = new StringBuffer();
        strb.append("{");
        strb.append("\"auth_code\": \""+auth_code+"\"");
        strb.append(" }");
        try {
            str = Request.Post("https://qyapi.weixin.qq.com/cgi-bin/service/get_permanent_code?suite_access_token=" + accesstoken)
                    .bodyString(strb.toString(), ContentType.APPLICATION_JSON)
                    .execute()
                    .returnContent()
                    .asString(Charset.forName("UTF-8"));
            Gson gson = new Gson();
            dealerCorpInfo =gson.fromJson(str, DealerCorpInfo.class);
            redisOpsUtil.set(RedisKeyPrefixConst.PERMANENT_CODE+dealerCorpInfo.getAuth_corp_info().getCorpid(),dealerCorpInfo);
            getAccessToken(dealerCorpInfo.getPermanent_code(),dealerCorpInfo.getAuth_corp_info().getCorpid(),accesstoken);
            redisOpsUtil.publish(RedisKeyPrefixConst.MSG_SN,dealerCorpInfo.getAuth_corp_info().getCorpid()+",add");
            return "操作成功";
        }catch (Exception e){
            throw new BusinessException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public String getAccessToken(String auth_cropid) throws BusinessException {
        if(redisOpsUtil.hasKey(RedisKeyPrefixConst.ACCESS_TOKEN+auth_cropid)){
            return redisOpsUtil.get(RedisKeyPrefixConst.ACCESS_TOKEN+auth_cropid);
        }
        if(!redisOpsUtil.hasKey(RedisKeyPrefixConst.PERMANENT_CODE+auth_cropid)){
            throw new BusinessException("PERMANENT_CODE不存在");
        }
        DealerCorpInfo dealerCorpInfo=redisOpsUtil.get(RedisKeyPrefixConst.PERMANENT_CODE+auth_cropid,DealerCorpInfo.class);
        String token=get_suite_access_token();
        Accesstoken accesstoken=null;
        String str = null;
        StringBuffer strb = new StringBuffer();
        strb.append(" { ");
        strb.append("\"auth_corpid\": \""+auth_cropid+"\" ,");
        strb.append("\"permanent_code\": \""+dealerCorpInfo.getPermanent_code()+"\" ");
        strb.append(" }");
        try{
            str = Request.Post("https://qyapi.weixin.qq.com/cgi-bin/service/get_corp_token?suite_access_token="+token)
                    .bodyString(strb.toString(), ContentType.APPLICATION_JSON)
                    .execute()
                    .returnContent()
                    .asString(Charset.forName("UTF-8"));
            Gson gson = new Gson();
            accesstoken =gson.fromJson(str, Accesstoken.class);
            redisOpsUtil.set(RedisKeyPrefixConst.ACCESS_TOKEN+auth_cropid,accesstoken.getAccess_token(),7200,TimeUnit.SECONDS);
            return accesstoken.getAccess_token();
        }catch (Exception e){
            throw new BusinessException(e.getMessage(),e.getCause());
        }
    }

    /**
     * 删除企业全部信息
     * @param auth_code
     * @return
     */
    @Override
    public CommonResult deleteAll(String auth_code) throws BusinessException {
        try {
            redisOpsUtil.delete(RedisKeyPrefixConst.ADMIN_INFO + auth_code);
            redisOpsUtil.delete(RedisKeyPrefixConst.ACCESS_TOKEN + auth_code);
            redisOpsUtil.delete(RedisKeyPrefixConst.PERMANENT_CODE + auth_code);
            redisOpsUtil.delete(RedisKeyPrefixConst.CORP_INFO + auth_code);
            redisOpsUtil.delete(RedisKeyPrefixConst.USER_INFO + auth_code);
            SysCorpExample corpExample = new SysCorpExample();
            corpExample.createCriteria().andCorpidEqualTo(auth_code);
            corpMapper.deleteByExample(corpExample);
            SysUserExample userExample = new SysUserExample();
            userExample.createCriteria().andCorpidsEqualTo(auth_code);
            List<SysUser> userList = userMapper.selectByExample(userExample);
            if (!userList.isEmpty()) {
                for (SysUser user : userList) {
                    redisOpsUtil.delete(RedisKeyPrefixConst.USER_INFO+auth_code+":"+user.getLoginId());
                    userMapper.delete(user);
                }
            }
            SysLogExample logExample=new SysLogExample();
            logExample.createCriteria().andCorpidEqualTo(auth_code);
            List<SysLog> logList=logMapper.selectByExample(logExample);
            if(!logList.isEmpty()){
                for(SysLog sysLog:logList){
                    logMapper.delete(sysLog);
                }
            }
        }catch (Exception e){
            throw new BusinessException(e.getMessage(),e.getCause());
        }
        return CommonResult.success(null,"清除成功");
    }

    /**
     * 服务商的token
     * 以corpid、provider_secret（获取方法为：登录服务商管理后台->标准应用服务->通用开发参数，可以看到）
     * 换取provider_access_token，代表的是服务商的身份，而与应用无关。请求单点登录、注册定制化等接口需要用到该凭证
     * @return
     */
    @Override
    public String get_provider_token() throws BusinessException {
        if(redisOpsUtil.hasKey(RedisKeyPrefixConst.PROVIDER_ACCESS_TOKEN)){
            return redisOpsUtil.get(RedisKeyPrefixConst.PROVIDER_ACCESS_TOKEN);
        }else {
            StringBuffer strb = new StringBuffer();
            strb.append(" { ");
            strb.append("\"corpid\": \"wwabb136cec3204033\",");
            strb.append("\"provider_secret\": \"feUWMSR8oFWUp1Isjeob6WTxtwdaxX_T1_BVc_BJRvHcqpSkkaiKOLfLYoKo_B6m\"");
            strb.append(" } ");
            log.info("发送给企业微信服务器json数据包:"+strb.toString());
            try {
                String   str = Request.Post("https://qyapi.weixin.qq.com/cgi-bin/service/get_provider_token")
                        .bodyString(strb.toString(), ContentType.APPLICATION_JSON)
                        .execute()
                        .returnContent()
                        .asString(Charset.forName("UTF-8"));
                Gson gson = new Gson();
                ProviderToken providerToken=gson.fromJson(str,ProviderToken.class);
                if(providerToken!=null){
                    redisOpsUtil.set(RedisKeyPrefixConst.PROVIDER_ACCESS_TOKEN,providerToken.getProvider_access_token(),7200,TimeUnit.SECONDS);
                    return providerToken.getProvider_access_token();
                }
            } catch (IOException e) {
                throw new BusinessException(e.getMessage(),e.getCause());
            }
        }
        return "";
    }

    /**
     * 第三方服务商在取得企业的永久授权码后，通过此接口可以获取到企业的access_token。
     * 获取后可通过通讯录、应用、消息等企业接口来运营这些应用。
     * @return
     */
    private void getAccessToken(String permanent_code,String corpid,String token) throws BusinessException {
        Accesstoken accesstoken=null;
        String str = null;
        StringBuffer strb = new StringBuffer();
        strb.append(" { ");
        strb.append("\"auth_corpid\": \""+corpid+"\" ,");
        strb.append("\"permanent_code\": \""+permanent_code+"\" ");
        strb.append(" }");
        try{
            str = Request.Post("https://qyapi.weixin.qq.com/cgi-bin/service/get_corp_token?suite_access_token="+token)
                    .bodyString(strb.toString(), ContentType.APPLICATION_JSON)
                    .execute()
                    .returnContent()
                    .asString(Charset.forName("UTF-8"));
            Gson gson = new Gson();
            accesstoken =gson.fromJson(str, Accesstoken.class);
            redisOpsUtil.set(RedisKeyPrefixConst.ACCESS_TOKEN+corpid,accesstoken.getAccess_token(),7200,TimeUnit.SECONDS);
        }catch (Exception e){
            throw new BusinessException(e.getMessage(),e.getCause());
        }
    }


    /**
     * 网络请求获取预授权码
     * @return
     */
    private PreAuthCode get_pre_auth_code_gateway() throws BusinessException {
        PreAuthCode preAuthCode=null;
        String accesstoken=get_suite_access_token();
        if(!StringUtils.isEmpty(accesstoken)){
            try {
                String  str = Request.Get("https://qyapi.weixin.qq.com/cgi-bin/service/get_pre_auth_code?suite_access_token="+accesstoken)
                        .execute()
                        .returnContent()
                        .asString(Charset.forName("UTF-8"));
                Gson gson = new Gson();
                preAuthCode =gson.fromJson(str, PreAuthCode.class);
            } catch (Exception e) {
                throw new BusinessException(e.getMessage(),e.getCause());
            }
        }
        return preAuthCode;
    }

    /**
     * 内部私有方法，从外网中获取第三方应用凭证suite_access_token
     * @return
     * @param suiteTicket
     */
    private SuiteAccesstoken get_suite_access_token_gateway(String suiteTicket) throws BusinessException {
        BaseConfig baseConfig=redisOpsUtil.get(RedisKeyPrefixConst.BASE_CONFIG,BaseConfig.class);
        if(baseConfig==null){
            return null;
        }
        SuiteAccesstoken accesstoken = null;
        StringBuffer strb = new StringBuffer();
        strb.append(" { ");
        strb.append("\"suite_id\": \""+baseConfig.getSuiteID()+"\",");
        strb.append("\"suite_secret\": \""+baseConfig.getSuiteSecret()+"\", ");
        strb.append("\"suite_ticket\": \""+suiteTicket+"\" ");
        strb.append(" }");
        try {
        String  str = Request.Post("https://qyapi.weixin.qq.com/cgi-bin/service/get_suite_token")
                    .bodyString(strb.toString(), ContentType.APPLICATION_JSON)
                    .execute()
                    .returnContent()
                    .asString(Charset.forName("UTF-8"));
            Gson gson = new Gson();
            accesstoken =gson.fromJson(str, SuiteAccesstoken.class);
        } catch (IOException e) {
            throw new BusinessException(e.getMessage(),e.getCause());
        }
        return accesstoken;
    }
}
