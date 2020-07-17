package weixin.v1.controller.web;

import base.aop.Log;
import base.api.CommonResult;
import base.constant.RedisKeyPrefixConst;
import base.session.SessionInfo;
import base.util.RedisOpsUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import weixin.mapper.SysLogMapper;
import weixin.mapper.SysUserMapper;
import weixin.model.SysLog;
import weixin.model.SysLogExample;
import weixin.model.SysUser;
import weixin.model.SysUserExample;
import weixin.service.UserService;
import weixin.vo.admin.Ad;
import weixin.vo.admin.Admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/web/v1")
public class LoginConstroller {
    @Autowired
    UserService userService;
    @Autowired
    SysUserMapper sysUserMapper;
    @Autowired
    RedisOpsUtil redisOpsUtil;
    @Autowired
    SysLogMapper logMapper;
    /**
     * web页面home页
     * @param model
     * @param request
     * @param response
     * @param session
     * @return
     */
    @RequestMapping("/login")
    public String  login(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        return "web/login/login";
    }

    /**
     * 用户登出
     * @param model
     * @param request
     * @param response
     * @param session
     * @return
     */
    @RequestMapping("/logout")
    public String logout(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session){
        session.invalidate();
        return "redirect:/web/v1/login";
    }
    /**
     * 用户首页
     * @param model
     * @param request
     * @param response
     * @param session
     * @return
     */
    @RequestMapping("/index")
    public String  index(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        SessionInfo sessionInfo= (SessionInfo) session.getAttribute(RedisKeyPrefixConst.SESSION_INFO);
        log.info("index===>index");
        if(sessionInfo!=null&&sessionInfo.getLoginId()!=null){
            if(redisOpsUtil.hasKey(RedisKeyPrefixConst.USER_INFO+sessionInfo.getCorpids()+":"+sessionInfo.getLoginId())) {
                SysUser user = redisOpsUtil.get(RedisKeyPrefixConst.USER_INFO + sessionInfo.getCorpids() + ":" + sessionInfo.getLoginId(), SysUser.class);
                model.addAttribute("userInfo", user);
                //TODO 记录日志
                SysLog sysLog = new SysLog(user.getLoginId(), user.getCorpids(), "LOGIN",
                        "LoginConstroller.index", "后台扫码登录", true, "扫码登录成功",
                        new Date(),RedisKeyPrefixConst.JINCHU_IMG,RedisKeyPrefixConst.JIN_CHU);
                logMapper.insert(sysLog);
                log.info("index===>sysLog");
                SysLogExample example = new SysLogExample();
                example.createCriteria().andLoginidEqualTo(user.getLoginId()).andCorpidEqualTo(user.getCorpids());
                example.setOrderByClause("id DESC");
                RowBounds rowBounds = new RowBounds(0, 10);
                List<SysLog> logList = logMapper.selectByExampleAndRowBounds(example, rowBounds);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                log.info("index===>logMapper");
                for(SysLog l:logList){
                    l.setIp(dateFormat.format(l.getCreatedate()));
                    if(l.getImg().indexOf("images/")>-1){
                        l.setImg("/static"+l.getImg());
                    }
                }
                log.info("index===>redis");
                model.addAttribute("logList", logList);
                return "web/index/home";
            }else {
                SysUserExample example=new SysUserExample();
                example.createCriteria().andCorpidsEqualTo(sessionInfo.getCorpids()).andLoginIdEqualTo(sessionInfo.getLoginId());
                SysUser user=sysUserMapper.selectOneByExample(example);
                if(user!=null) {
                    model.addAttribute("userInfo", user);
                    //TODO 记录日志
                    SysLog sysLog = new SysLog(user.getLoginId(), user.getCorpids(), "LOGIN",
                            "LoginConstroller.index", "后台扫码登录", true, "扫码登录成功",
                            new Date(),RedisKeyPrefixConst.JINCHU_IMG,RedisKeyPrefixConst.JIN_CHU);
                    logMapper.insert(sysLog);
                    SysLogExample examp = new SysLogExample();
                    examp.createCriteria().andLoginidEqualTo(user.getLoginId()).andCorpidEqualTo(user.getCorpids());
                    example.setOrderByClause("id DESC");
                    RowBounds rowBounds = new RowBounds(0, 10);
                    List<SysLog> logList = logMapper.selectByExampleAndRowBounds(examp, rowBounds);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                    for(SysLog l:logList){
                        l.setIp(dateFormat.format(l.getCreatedate()));
                    }
                    model.addAttribute("logList", logList);
                    log.info("index===>mysql");
                    return "web/index/home";
                }else {
                    model.addAttribute("msg","无权访问，请注册后再登录！");
                    model.addAttribute("redirect_uri","/web/v1/login");
                    return "error/404";
                }
            }
        }else {
            model.addAttribute("msg","登录超时，请重新登录！");
            model.addAttribute("redirect_uri","/web/v1/login");
            return "error/404";
        }
    }
    /**
     /**
     * 企业微信扫描登录网页端
     */
    @GetMapping("/qrConnect")
    public String qrConnect(HttpSession session){
        String backUrl="";
        SessionInfo sessionInfo= (SessionInfo) session.getAttribute(RedisKeyPrefixConst.SESSION_INFO);
        if(sessionInfo!=null&&sessionInfo.getLoginId()!=null){
            backUrl="/web/v1/index";
        }else {
            log.info("##########企业微信扫码登录网页##########");
             backUrl = "https://open.work.weixin.qq.com/wwopen/sso/3rd_qrConnect?" +
                    "appid=wwabb136cec3204033" +
                    "&redirect_uri=https%3a%2f%2fsafe.jinchu.com.cn%2fweb%2fv1%2fhome" +
                    "&state=" +
                    "&usertype=admin";
        }
        return "redirect:" + backUrl;
    }

    /**
     * web登录后的首页
     * @param model
     * @param request
     * @param auth_code
     * @param session
     * @return
     */
    @RequestMapping(value = "/home", method = {RequestMethod.GET, RequestMethod.POST} )
    public String  home(Model model, HttpServletRequest request, @RequestParam String auth_code, HttpSession session){
        log.info("企业微信登录重定向 ==>{}",auth_code);
        try {
            CommonResult info = userService.get_login_info(auth_code);
            if (info.getCode() == 200) {
                String loginInfo = (String) info.getData();
                SessionInfo sessionInfo=new SessionInfo();
                if (redisOpsUtil.hasKey(RedisKeyPrefixConst.USER_INFO + loginInfo)) {
                    SysUser user = redisOpsUtil.get(RedisKeyPrefixConst.USER_INFO + loginInfo, SysUser.class);
                    sessionInfo.setLoginId(user.getLoginId());
                    sessionInfo.setRoleid(user.getRoleId());
                    sessionInfo.setCorpids(user.getCorpids());
                    sessionInfo.setCorpname(user.getCorpname());
                    session.setAttribute(RedisKeyPrefixConst.SESSION_INFO, sessionInfo);
                    return "redirect:/web/v1/index";
                }else {
                    String infos[]=loginInfo.split(":");
                    if(infos.length>1){
                        SysUserExample example=new SysUserExample();
                        example.createCriteria().andCorpidsEqualTo(infos[0]).andLoginIdEqualTo(infos[1]);
                        SysUser user=sysUserMapper.selectOneByExample(example);
                        if(user!=null) {
                            sessionInfo.setLoginId(user.getLoginId());
                            sessionInfo.setRoleid(user.getRoleId());
                            sessionInfo.setCorpids(user.getCorpids());
                            sessionInfo.setCorpname(user.getCorpname());
                            session.setAttribute(RedisKeyPrefixConst.SESSION_INFO, sessionInfo);
                            return "redirect:/web/v1/index";
                        }else {
                            model.addAttribute("msg","无权访问，请注册后再登录！");
                            model.addAttribute("redirect_uri","/web/v1/login");
                            return "error/404";
                        }
                    }else {
                        model.addAttribute("msg","无权访问，请注册后再登录！");
                        model.addAttribute("redirect_uri","/web/v1/login");
                        return "error/404";
                    }
                }
            }else {
                model.addAttribute("msg","无权访问，请注册后再登录！");
                model.addAttribute("redirect_uri","/web/v1/login");
                return "error/404";
            }
        }catch (Exception e){
            model.addAttribute("msg",e.getMessage());
            model.addAttribute("redirect_uri","/web/v1/login");
            return "error/404";
        }

    }

    @RequestMapping(value = "/test", method = {RequestMethod.GET, RequestMethod.POST} )
    public String  test(Model model, HttpServletRequest request,  HttpSession session){
        try {
            if (redisOpsUtil.hasKey("wx:base:crop:user:info:wwabb136cec3204033:LiuZhenJun")) {
                SysUser user = redisOpsUtil.get("wx:base:crop:user:info:wwabb136cec3204033:LiuZhenJun", SysUser.class);
                SessionInfo sessionInfo=new SessionInfo();
                sessionInfo.setLoginId(user.getLoginId());
                sessionInfo.setRoleid(user.getRoleId());
                sessionInfo.setCorpids(user.getCorpids());
                sessionInfo.setCorpname(user.getCorpname());
                session.setAttribute(RedisKeyPrefixConst.SESSION_INFO, sessionInfo);
                return "redirect:/web/v1/index";
            }else {
                model.addAttribute("msg","无权访问，请注册后再登录！");
                model.addAttribute("redirect_uri","/web/v1/login");
                return "error/404";
            }
        }catch (Exception e){
            model.addAttribute("msg",e.getMessage());
            model.addAttribute("redirect_uri","/web/v1/login");
            return "error/404";
        }
    }

    /**
     * 如何首页
     * @param model
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "/shouye", method = {RequestMethod.GET} )
    public String  shouye(Model model, HttpServletRequest request,  HttpSession session){
        try {
            SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(RedisKeyPrefixConst.SESSION_INFO);
            if ((sessionInfo == null) || (sessionInfo.getLoginId() == null)) {// 如果没有登录或登录超时
                model.addAttribute("msg","登录超时，请重新登录！");
                model.addAttribute("redirect_uri","/web/v1/login");
                return "error/404";
            }else {
                SysUser user = redisOpsUtil.get("wx:base:crop:user:info:"+sessionInfo.getCorpids()+":"+sessionInfo.getLoginId(), SysUser.class);
                model.addAttribute("userInfo",user);
                return "web/index/home";
            }
        }catch (Exception e){
            model.addAttribute("msg",e.getMessage());
            model.addAttribute("redirect_uri","/web/v1/login");
            return "error/404";
        }
    }
}
