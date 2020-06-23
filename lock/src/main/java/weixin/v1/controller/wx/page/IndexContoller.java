package weixin.v1.controller.wx.page;



import base.constant.RedisKeyPrefixConst;
import base.exception.BusinessException;
import base.session.SessionInfo;
import base.util.RedisOpsUtil;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import weixin.mapper.SysUserMapper;
import weixin.model.SysCorp;
import weixin.model.SysUser;
import weixin.model.SysUserExample;
import weixin.service.BaseConfigService;
import weixin.vo.userinfo.WxLoginInfo;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;


import static java.net.URLEncoder.encode;

@Controller
@Slf4j
@RequestMapping("/wx/v1")
public class IndexContoller extends Thread {
    @Autowired
    BaseConfigService baseConfigService;
    @Autowired
    RedisOpsUtil redisOpsUtil;
    @Autowired
    SysUserMapper userMapper;

    @RequestMapping("/index")
    public String  hello(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
       log.info("######################index######################");
//        Cookie[]cookies=request.getCookies();//获取所有的Cookie
//        String LoginId="",CorpId="";
//        if(cookies==null){
//            log.info("#################第一次进入###############");
//            String url=back();
//            log.info("重定向路径==>{}",url);
//            return "redirect:" + url;
//        }else {
//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals("LoginId")) {//寻找需要的值
//                    LoginId = cookie.getValue();
//                    log.info("LoginId==>{}",LoginId);
//                }
//                if(cookie.getName().equals("CorpId")){
//                    CorpId= cookie.getValue();
//                    log.info("CorpId==>{}",CorpId);
//                }
//            }
//            if("".equals(LoginId)||"".equals(CorpId)){
//                log.info("#################第一次进入###############");
//                String url=back();
//                return "redirect:" + url;
//            }else {
//                log.info("#################直接从cookie中进入###############");
//                if(redisOpsUtil.hasKey(RedisKeyPrefixConst.USER_INFO+CorpId+":"+LoginId)){
//                    SysUser user=redisOpsUtil.get(RedisKeyPrefixConst.USER_INFO+CorpId+":"+LoginId,SysUser.class);
//                    SessionInfo sessionInfo=new SessionInfo();
//                    sessionInfo.setLoginId(user.getLoginId());
//                    sessionInfo.setRoleid(user.getRoleId());
//                    sessionInfo.setCorpids(user.getCorpids());
//                    sessionInfo.setCorpname(user.getCorpname());
//                    model.addAttribute("userInfo",user);
//                    session.setAttribute(RedisKeyPrefixConst.SESSION_INFO, sessionInfo);
//                    return "weixin/index/index";
//                }else {
//                    model.addAttribute("msg","用户账号无法登录");
//                    return "error/504";
//                }
//
//            }
//        }
        if(redisOpsUtil.hasKey("wx:base:crop:user:info:wwabb136cec3204033:LiuZhenJun")) {
            SysUser user = redisOpsUtil.get("wx:base:crop:user:info:wwabb136cec3204033:LiuZhenJun", SysUser.class);
            SessionInfo sessionInfo=new SessionInfo();
            sessionInfo.setLoginId(user.getLoginId());
            sessionInfo.setRoleid(user.getRoleId());
            sessionInfo.setCorpids(user.getCorpids());
            sessionInfo.setCorpname(user.getCorpname());
            session.setAttribute(RedisKeyPrefixConst.SESSION_INFO, sessionInfo);
            model.addAttribute("userInfo",user);
            Cookie cookie1 =new Cookie("LoginId", user.getLoginId());//创建Cookie
            cookie1.setMaxAge(3600*60*60);//设置Cookie生命周期，以秒为单位
            cookie1.setPath("/");
            Cookie cookie2=new Cookie("CorpId", user.getCorpids());
            cookie2.setMaxAge(3600*60*60);
            cookie2.setPath("/");
            response.addCookie(cookie1);//发回浏览器
            response.addCookie(cookie2);//发回浏览器
            return "weixin/index/index";
        }else {
            model.addAttribute("msg","用户账号无法登录");
            return "error/504";
        }

    }

    /**
     * 拼接微信回调
     * @return
     */
    private String back() {
        String backUrl ="https://safe.jinchu.com.cn/wx/v1/oauth2me";
        String redirect_uri = "";
        try {
            redirect_uri = encode(backUrl, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String oauth2Url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=ww120bb8bfb3277e5c&redirect_uri=" + redirect_uri
                + "&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect";
        return oauth2Url;
    }

    /**
     * 授权回调请求处理
     * @return
     */
    @RequestMapping(value = { "/oauth2me" })
    public String oAuth2Url(Model model,HttpServletRequest request, HttpServletResponse response, HttpSession session, @RequestParam String code) throws BusinessException {
        log.info("回调获取个人信息==>{}",code);
        String suite_access_token=baseConfigService.get_suite_access_token();
        if(suite_access_token==null){
            throw new BusinessException("suite_access_token 不存在");
        }
        try {
        String    str = Request.Get("https://qyapi.weixin.qq.com/cgi-bin/service/getuserinfo3rd?suite_access_token="+suite_access_token+"&code="+code)
                    .execute()
                    .returnContent()
                    .asString(Charset.forName("UTF-8"));
            Gson gson = new Gson();
            WxLoginInfo user=gson.fromJson(str,WxLoginInfo.class);
            log.info("user==>{}",user);
            if(user!=null){
                String CorpId=user.getCorpId();
                String UserId=user.getUserId();
                if(redisOpsUtil.hasKey(RedisKeyPrefixConst.USER_INFO+CorpId+":"+UserId)){
                    SysUser use=redisOpsUtil.get(RedisKeyPrefixConst.USER_INFO+CorpId+":"+UserId,SysUser.class);
                    SessionInfo sessionInfo=new SessionInfo();
                    sessionInfo.setLoginId(use.getLoginId());
                    sessionInfo.setRoleid(use.getRoleId());
                    sessionInfo.setCorpids(use.getCorpids());
                    sessionInfo.setCorpname(use.getCorpname());
                    model.addAttribute("userInfo",use);
                    session.setAttribute(RedisKeyPrefixConst.SESSION_INFO, sessionInfo);
                    Cookie cookie1 =new Cookie("LoginId", UserId);//创建Cookie
                    cookie1.setMaxAge(3600*60*60);//设置Cookie生命周期，以秒为单位
                    cookie1.setPath("/");
                    Cookie cookie2=new Cookie("CorpId", CorpId);
                    cookie2.setMaxAge(3600*60*60);
                    cookie2.setPath("/");
                    response.addCookie(cookie1);//发回浏览器
                    response.addCookie(cookie2);//发回浏览器
                    return "weixin/index/index";
                }else {
                    SysUserExample userExample=new SysUserExample();
                    userExample.createCriteria().andCorpidsEqualTo(CorpId).andLoginIdEqualTo(UserId);
                    SysUser user1=userMapper.selectOneByExample(userExample);
                    if(user1!=null){
                        SessionInfo sessionInfo=new SessionInfo();
                        sessionInfo.setLoginId(user1.getLoginId());
                        sessionInfo.setRoleid(user1.getRoleId());
                        sessionInfo.setCorpids(user1.getCorpids());
                        sessionInfo.setCorpname(user1.getCorpname());
                        session.setAttribute(RedisKeyPrefixConst.SESSION_INFO, sessionInfo);
                        Cookie cookie1 =new Cookie("LoginId", UserId);//创建Cookie
                        cookie1.setMaxAge(3600*60*60);//设置Cookie生命周期，以秒为单位
                        cookie1.setPath("/");
                        Cookie cookie2=new Cookie("CorpId", CorpId);
                        cookie2.setMaxAge(3600*60*60);
                        cookie2.setPath("/");
                        response.addCookie(cookie1);//发回浏览器
                        response.addCookie(cookie2);//发回浏览器
                        return "weixin/index/index";
                    }else {
                        model.addAttribute("msg", "用户账号无法登录");
                        return "error/504";
                    }
                }
            }else {
                model.addAttribute("msg","用户账号无法登录");
                return "error/504";
            }
        } catch (Exception e) {
           throw new BusinessException(e.getMessage(),e.getCause());
        }
    }
}
