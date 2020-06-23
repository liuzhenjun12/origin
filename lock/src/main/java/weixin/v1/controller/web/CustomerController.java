package weixin.v1.controller.web;

import base.constant.RedisKeyPrefixConst;
import base.session.SessionInfo;
import base.util.RedisOpsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import weixin.mapper.SysUserMapper;
import weixin.model.SysCorp;
import weixin.model.SysUser;
import weixin.model.SysUserExample;
import weixin.vo.Count;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/web/customer")
public class CustomerController {
    @Autowired
    RedisOpsUtil redisOpsUtil;
    @Autowired
    SysUserMapper userMapper;
    /**
     * 授权企业管理中心页面
     * home：首页
     * @return
     */
    @RequestMapping("/{url}")
    public String customer(@PathVariable String url, Model model){
        return "web/customer/"+url;
    }

    /**
     * 授权企业管理中心首页
     * @param model
     * @return
     */
    @RequestMapping("/shou")
    public String home(Model model, HttpSession session){
        SessionInfo sessionInfo= (SessionInfo) session.getAttribute(RedisKeyPrefixConst.SESSION_INFO);
        SysCorp corp=redisOpsUtil.get(RedisKeyPrefixConst.CORP_INFO+sessionInfo.getCorpids(),SysCorp.class);
        model.addAttribute("corpInfo",corp);
        SysUserExample example=new SysUserExample();
        example.createCriteria().andCorpidsEqualTo(sessionInfo.getCorpids());
        List<SysUser> userList=userMapper.selectByExample(example);
        model.addAttribute("userList",userList);
        return "web/customer/home";
    }
}
