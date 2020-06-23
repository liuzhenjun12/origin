package weixin.impl;

import base.api.CommonResult;
import base.constant.RedisKeyPrefixConst;
import base.exception.BusinessException;
import base.mybatis.BaseMapper;
import base.mybatis.BaseServiceImpl;
import base.util.RedisOpsUtil;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import weixin.mapper.SysLogMapper;
import weixin.mapper.SysUserMapper;
import weixin.model.SysCorp;
import weixin.model.SysLog;
import weixin.model.SysUser;
import weixin.model.SysUserExample;
import weixin.service.BaseConfigService;
import weixin.service.UserService;
import weixin.vo.SuiteAccesstoken;
import weixin.vo.admin.Ad;
import weixin.vo.admin.Admin;
import weixin.vo.permanentCode.DealerCorpInfo;
import weixin.vo.userinfo.LoginInfo;
import weixin.vo.userxiangxi.UserXI;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl   extends BaseServiceImpl<SysUser, Integer> implements UserService {
    @Autowired
    RedisOpsUtil redisOpsUtil;
    @Autowired
    SysUserMapper userMapper;
    @Autowired
    SysLogMapper logMapper;
    @Autowired
    BaseConfigService baseConfigService;

    @Override
    public BaseMapper<SysUser, Integer> getMappser() {
        return userMapper;
    }

    /**
     * 添加企业微信授权用户
     * @param corpid
     * @return
     */
    @Override
    public CommonResult addUser(String corpid) {
        log.info("corpid==>{}",corpid);
        if(StringUtils.isEmpty(corpid)){
            return CommonResult.failed(null,"corpid不能为空");
        }
        if(!redisOpsUtil.hasKey(RedisKeyPrefixConst.PERMANENT_CODE+corpid)){
            return CommonResult.failed(null,"PERMANENT_CODE没有数据");
        }
        DealerCorpInfo dealerCorpInfo=redisOpsUtil.get(RedisKeyPrefixConst.PERMANENT_CODE+corpid,DealerCorpInfo.class);
        if(!redisOpsUtil.hasKey(RedisKeyPrefixConst.ACCESS_TOKEN+corpid)){
            return CommonResult.failed(null,"ACCESS_TOKEN没有数据");
        }
        String token=redisOpsUtil.get(RedisKeyPrefixConst.ACCESS_TOKEN+corpid);
        String ids[]=dealerCorpInfo.getAuth_info().getAgent().get(0).getPrivilege().getAllow_user();
        for(int i=0;i<ids.length;i++){
            UserXI userxi=null;
            String str = null;
            try {
                log.info("get==>{}","https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token="+token+"&userid="+ids[i]);
                str = Request.Get("https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token="+token+"&userid="+ids[i])
                        .execute()
                        .returnContent()
                        .asString(Charset.forName("UTF-8"));
                log.info("dataa==>{}",str);
                Gson gson = new Gson();
                userxi =gson.fromJson(str, UserXI.class);
                log.info("userxi==>{}",userxi.toString());
                SysUser user=new SysUser();
                user.setLoginId(userxi.getUserid());
                user.setCorpids(corpid);
                user.setCorpname(dealerCorpInfo.getAuth_corp_info().getCorp_name());
                user.setImg(userxi.getAvatar());
                user.setRoleId(2);
                user.setStatus(userxi.getStatus()==1?true:false);
                user.setDepartment(userxi.getDepartment().length>0?userxi.getDepartment()[0]:0);
                user.setMainDepartment(userxi.getMain_department());
                user.setCreatedatetime(new Date());
                userMapper.insert(user);
                redisOpsUtil.set(RedisKeyPrefixConst.USER_INFO+corpid+":"+userxi.getUserid(),user);
            } catch (Exception e) {
                return CommonResult.failed(null,"发生异常==>"+e.getMessage());
            }
        }
        SysLog sysLog=new SysLog();
        sysLog.setMethodjc("添加应用");
        sysLog.setCorpid(corpid);
        sysLog.setCreatedate(new Date());
        sysLog.setMethodtype("ADD");
        sysLog.setMethodname("UserServiceImpl.addUser");
        sysLog.setLoginid(dealerCorpInfo.getAuth_user_info().getUserid());
        sysLog.setSccess(true);
        sysLog.setResult("成功应用成功并授权"+ids.length+"个成员使用");
        logMapper.insert(sysLog);
        return CommonResult.success(null,"添加用户成功");
    }

    /**
     * 应用的管理员列表（不包括外部管理员）
     * @param corpid
     * @return
     */
    @Override
    public CommonResult addAdmin(String corpid) throws BusinessException {
        log.info("管理员列表corpid==>",corpid);
        if(StringUtils.isEmpty(corpid)){
            return CommonResult.failed(null,"corpid没有数据");
        }
        if(!redisOpsUtil.hasKey(RedisKeyPrefixConst.PERMANENT_CODE+corpid)){
            return CommonResult.failed("没有数据");
        }
        DealerCorpInfo dealerCorpInfo=redisOpsUtil.get(RedisKeyPrefixConst.PERMANENT_CODE+corpid,DealerCorpInfo.class);
        String suite_access_token=baseConfigService.get_suite_access_token();
        StringBuffer strb = new StringBuffer();
        strb.append(" { ");
        strb.append("\"auth_corpid\": \""+corpid+"\",");
        strb.append("\"agentid\": \""+dealerCorpInfo.getAuth_info().getAgent().get(0).getAgentid()+"\" ");
        strb.append(" }");
        try {
            String  str = Request.Post("https://qyapi.weixin.qq.com/cgi-bin/service/get_admin_list?suite_access_token="+suite_access_token)
                    .bodyString(strb.toString(), ContentType.APPLICATION_JSON)
                    .execute()
                    .returnContent()
                    .asString(Charset.forName("UTF-8"));
            Gson gson = new Gson();
            Admin admin =gson.fromJson(str, Admin.class);
            if(admin!=null){
                redisOpsUtil.set(RedisKeyPrefixConst.ADMIN_INFO+corpid,admin);
                SysUserExample userExample = new SysUserExample();
                userExample.createCriteria().andCorpidsEqualTo(corpid);
                List<SysUser> userList = userMapper.selectByExample(userExample);
                if(!userList.isEmpty()){
                     List<Ad> adm=admin.getAdmin();
                    for(int i=0;i<adm.size();i++){
                        for(SysUser u:userList){
                          if(u.getLoginId().equals(adm.get(i).getUserid())){
                              u.setRoleId(1);
                              userMapper.updateByPrimaryKey(u);
                              redisOpsUtil.set(RedisKeyPrefixConst.USER_INFO+corpid + ":" + u.getLoginId(),u);
                          }
                        }
                    }
                }
            }
        } catch (IOException e) {
            return CommonResult.failed(null,"抛出IOException异常==>"+e.getMessage());
        }
        return CommonResult.success(null,"添加管理员成功");
    }

    /**
     * 修改授权用户
     * @param corpid
     * @return
     */
    @Override
    public CommonResult updateUser(String corpid) throws BusinessException {
        log.info("更新授权信息corpid==>{}",corpid);
        if(StringUtils.isEmpty(corpid)){
            return CommonResult.failed(null,"corpid没有数据");
        }
        if(!redisOpsUtil.hasKey(RedisKeyPrefixConst.PERMANENT_CODE+corpid)){
            return CommonResult.failed("PERMANENT_CODE没有数据");
        }
        String token=baseConfigService.getAccessToken(corpid);
        DealerCorpInfo dealerCorpInfo=redisOpsUtil.get(RedisKeyPrefixConst.PERMANENT_CODE+corpid,DealerCorpInfo.class);
        String suite_access_token=baseConfigService.get_suite_access_token();
        StringBuffer strb = new StringBuffer();
        strb.append("{");
        strb.append("\"auth_corpid\": \""+corpid+"\",");
        strb.append("\"permanent_code\": \""+dealerCorpInfo.getPermanent_code()+"\"");
        strb.append(" }");
        try {
        String   str = Request.Post("https://qyapi.weixin.qq.com/cgi-bin/service/get_auth_info?suite_access_token="+suite_access_token)
                    .bodyString(strb.toString(), ContentType.APPLICATION_JSON)
                    .execute()
                    .returnContent()
                    .asString(Charset.forName("UTF-8"));
            Gson gson = new Gson();
            DealerCorpInfo dealerCorpInfo1 =gson.fromJson(str, DealerCorpInfo.class);
            if(dealerCorpInfo1!=null){
                dealerCorpInfo.setAuth_corp_info(dealerCorpInfo1.getAuth_corp_info());
                dealerCorpInfo.setAuth_info(dealerCorpInfo1.getAuth_info());
                redisOpsUtil.set(RedisKeyPrefixConst.PERMANENT_CODE+corpid,dealerCorpInfo);
                String ids[]=dealerCorpInfo1.getAuth_info().getAgent().get(0).getPrivilege().getAllow_user();
                List<String> yuan = new ArrayList<String>();
                SysUserExample userExample = new SysUserExample();
                userExample.createCriteria().andCorpidsEqualTo(corpid);
                List<SysUser> userList = userMapper.selectByExample(userExample);
                if (!userList.isEmpty()) {
                    boolean syuan = false;
                    for (int i = 0; i < ids.length; i++) {
                        syuan = false;
                        for (SysUser user : userList) {
                            if (ids[i].equals(user.getLoginId())) {
                                yuan.add(user.getLoginId());
                                syuan = true;
                                continue;
                            }
                        }
                            if (!syuan) {
                                try {
                                    String str1 = Request.Get("https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token=" + token + "&userid=" + ids[i])
                                            .execute()
                                            .returnContent()
                                            .asString(Charset.forName("UTF-8"));
                                    Gson gson1 = new Gson();
                                    UserXI userxi = gson1.fromJson(str1, UserXI.class);
                                    SysUser us = new SysUser();
                                    us.setLoginId(userxi.getUserid());
                                    us.setCorpids(corpid);
                                    us.setCorpname(dealerCorpInfo.getAuth_corp_info().getCorp_name());
                                    us.setImg(userxi.getAvatar());
                                    us.setRoleId(2);
                                    us.setStatus(userxi.getStatus() == 1 ? true : false);
                                    us.setDepartment(userxi.getDepartment().length > 0 ? userxi.getDepartment()[0] : 0);
                                    us.setMainDepartment(userxi.getMain_department());
                                    us.setCreatedatetime(new Date());
                                    userMapper.insert(us);
                                    redisOpsUtil.set(RedisKeyPrefixConst.USER_INFO + corpid + ":" + userxi.getUserid(), us);
                                } catch (Exception e) {
                                    return CommonResult.failed(null, "发生异常==>" + e.getMessage());
                                }
                            }
                        }
                    if(yuan.size()>0){
                        boolean isdel=false;
                            for(SysUser user : userList){
                                isdel=false;
                                for (int i = 0; i < yuan.size(); i++) {
                                    if(user.getLoginId().equals(yuan.get(i))){
                                        isdel=true;
                                    }
                                }
                                if(!isdel){
                                    redisOpsUtil.delete(RedisKeyPrefixConst.USER_INFO+corpid+":"+user.getLoginId());
                                    userMapper.delete(user);
                                }
                            }
                    }

                }

            }
        }catch (Exception e){
            return CommonResult.failed(e.getCause(),e.getMessage());
        }
        SysLog sysLog=new SysLog();
        sysLog.setMethodjc("修改授权用户");
        sysLog.setCorpid(corpid);
        sysLog.setCreatedate(new Date());
        sysLog.setMethodtype("ADD");
        sysLog.setMethodname("UserServiceImpl.updateUser");
        sysLog.setLoginid(dealerCorpInfo.getAuth_user_info().getUserid());
        sysLog.setSccess(true);
        sysLog.setResult("修改成功");
        logMapper.insert(sysLog);
        return  CommonResult.success(null,"修改成功");
    }

    /**
     * 获取企业微信登录用户信息
     * @param authCode
     * @return
     */
    @Override
    public CommonResult get_login_info(String authCode) throws BusinessException {
        /**
         * 从缓存中获取服务商token
         */
        String token=baseConfigService.get_provider_token();
        try {
            String str = null;
            StringBuffer strb = new StringBuffer();
            strb.append(" { ");
            strb.append("\"auth_code\": \""+authCode+"\" ");
            strb.append(" }");
            str = Request.Post("https://qyapi.weixin.qq.com/cgi-bin/service/get_login_info?access_token="+token)
                    .bodyString(strb.toString(), ContentType.APPLICATION_JSON)
                    .execute()
                    .returnContent()
                    .asString(Charset.forName("UTF-8"));
            Gson gson = new Gson();
            LoginInfo info=gson.fromJson(str,LoginInfo.class);
            if(info!=null){
                return CommonResult.success(info.getCorp_info().getCorpid()+":"+info.getUser_info().getUserid());
            }
        }catch (Exception e){
            log.info("发生错误 ==>{}",e.getMessage());
            return CommonResult.failed(e.getCause(),e.getMessage());
        }
        return CommonResult.failed();
    }
}
