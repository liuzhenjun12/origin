package weixin.v1.controller.wx.page;

import base.util.RedisOpsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import weixin.mapper.SysLockMapper;
import weixin.mapper.SysLogMapper;
import weixin.mapper.SysTodoMapper;
import weixin.mapper.SysUserMapper;
import weixin.model.SysTodo;
import weixin.model.SysTodoExample;
import weixin.model.SysUser;
import weixin.model.SysUserExample;
import weixin.vo.UserLock;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/wx/v1")
public class PageController {
    @Autowired
    RedisOpsUtil redisOpsUtil;
    @Autowired
    SysUserMapper userMapper;
    @Autowired
    SysTodoMapper todoMapper;
    @Autowired
    SysLogMapper logMapper;
    @Autowired
    SysLockMapper lockMapper;

    @RequestMapping(value = "/page/{b}", method = {RequestMethod.GET} )
    public String toPang(@PathVariable String b){
        return "weixin/marage/"+b;
    }

    /**
     * 修改设备名称、秘钥页面
     * @param model
     * @param lockid
     * @return
     */
    @RequestMapping(value = "/page/getlock", method = {RequestMethod.GET} )
    public String toPang(Model model,Integer lockid){
        model.addAttribute("lockinfo",lockMapper.selectByPrimaryKey(lockid));
        return "weixin/marage/updateLock";
    }

    /**
     * 设备分配给成员列表
     * @param model
     * @param lockid
     * @return
     */
    @RequestMapping(value = "/page/fenpei", method = {RequestMethod.GET} )
    public String fenpei(Model model,Integer lockid,String corpid){
        model.addAttribute("lockinfo",lockMapper.selectByPrimaryKey(lockid));
        SysUserExample userExample=new SysUserExample();
        userExample.createCriteria().andCorpidsEqualTo(corpid);
        List<SysUser> userList=userMapper.selectByExample(userExample);
        List<UserLock> userLocks=new ArrayList<UserLock>();
        if(!userList.isEmpty()){
            for(SysUser u:userList){
                UserLock userLock=new UserLock();
                userLock.setUserid(u.getLoginId());
                //TODO 查看任务表中是否有任务，有代表有设备，没有则无
                SysTodoExample todoExample=new SysTodoExample();
                todoExample.createCriteria().andUserIdEqualTo(u.getLoginId()).andCorpIdEqualTo(corpid);
                List<SysTodo> todos=todoMapper.selectByExample(todoExample);
                if(todos.isEmpty()){
                    userLock.setHas(false);
                }else {
                    userLock.setHas(true);
                }
                userLocks.add(userLock);
            }
        }
        model.addAttribute("userLock",userLocks);
        return "weixin/marage/fenpei";
    }


}
