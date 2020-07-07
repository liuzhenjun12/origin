package weixin.v1.controller.wx.yewu;

import base.api.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import weixin.mapper.SysUserMapper;
import weixin.model.SysUser;
import weixin.model.SysUserExample;
import weixin.vo.userinfo.UserInfo;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/wx/v1")
public class WxUserController {
    @Autowired
    SysUserMapper userMapper;
    /**
     * 修改用户头像
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
                if(s.getRoleId().toString().equals("1")){
                    info.setRole("管理员");
                }else if(s.getRoleId().toString().equals("2")){
                    info.setRole("成员");
                }else {
                    info.setRole("超级管理员");
                }
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
}
