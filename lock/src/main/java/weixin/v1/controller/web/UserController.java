package weixin.v1.controller.web;

import base.aop.Log;
import base.api.CommonResult;
import base.constant.RedisKeyPrefixConst;
import base.util.File_Util;
import base.util.RedisOpsUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import weixin.mapper.SysUserMapper;
import weixin.model.SysUser;
import weixin.model.SysUserExample;
import weixin.vo.page.Grid;
import weixin.vo.page.PageFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/web/user")
public class UserController {
    @Autowired
    SysUserMapper userMapper;
    @Autowired
    RedisOpsUtil redisOpsUtil;

    @Value("${file.uploadFolder}")
    private String uploadFolder;
    private String staticAccessPath = "/upload/";

    /**
     * 分页条件查询
     * @return
     */
    @RequestMapping("/list")
    public Grid getList(SysUser sysUser,  PageFilter ph){
        log.info("info===>{}",sysUser.toString());
        Grid grid=new Grid();
        SysUserExample example = new SysUserExample();
        SysUserExample.Criteria tiao = example.createCriteria();
        if(!StringUtils.isEmpty(sysUser.getCorpids())){
            tiao.andCorpidsEqualTo(sysUser.getCorpids());
        }
        if(!StringUtils.isEmpty(sysUser.getCorpname())){
            tiao.andCorpnameLike("%"+sysUser.getCorpname()+"%");
        }
        if(!StringUtils.isEmpty(sysUser.getLoginId())){
            tiao.andLoginIdLike("%"+sysUser.getLoginId()+"%");
        }
        if(!StringUtils.isEmpty(sysUser.getRoleId())){
            tiao.andRoleIdEqualTo(sysUser.getRoleId());
        }
        RowBounds rowBounds = new RowBounds(ph.getPage()-1,ph.getRows());
        List<SysUser> ls = userMapper.selectByExampleAndRowBounds(example,rowBounds);
        grid.setRows(ls);
        int count=userMapper.selectCountByExample(example);
        grid.setTotal(count);
        return grid;
    }

    /**
     * 通过id获取
     * @param id
     * @return
     */
    @RequestMapping("/get")
    public CommonResult get(Integer id){
        try{
            SysUser user=userMapper.selectByPrimaryKey(id);
            return CommonResult.success(user);
        }catch (Exception e){
            return CommonResult.failed(e.getCause(),e.getMessage());
        }
    }

    /**
     * 通过企业id、用户id获取
     * @return
     */
    @RequestMapping("/getByCorpUserid")
    public CommonResult getByCorpUserid(String corpid,String userid){
        try{
            if(redisOpsUtil.hasKey(RedisKeyPrefixConst.USER_INFO+corpid+":"+userid)){
                SysUser use=redisOpsUtil.get(RedisKeyPrefixConst.USER_INFO+corpid+":"+userid,SysUser.class);
                return CommonResult.success(use);
            }else {
                SysUserExample userExample = new SysUserExample();
                userExample.createCriteria().andCorpidsEqualTo(corpid).andLoginIdEqualTo(userid);
                SysUser user = userMapper.selectOneByExample(userExample);
                return user!=null?CommonResult.success(user):CommonResult.failed();
            }
        }catch (Exception e){
            return CommonResult.failed(e.getCause(),e.getMessage());
        }
    }

    /**
     * 修改用户头像
     * @return
     */
    @Log(desc = "修改用户头像",type = Log.LOG_TYPE.UPDATE)
    @RequestMapping(value = "/updateTx", method = {RequestMethod.POST})
    CommonResult updateTx(HttpServletRequest request, HttpSession session, SysUser user,
                          @RequestParam("imgpath") MultipartFile imgpath){
        if(user==null){
            return CommonResult.failed("参数错误");
        }
        if(StringUtils.isEmpty(user.getLoginId())||StringUtils.isEmpty(user.getCorpids())){
            return CommonResult.failed("参数错误,用户id、企业id不能为空");
        }
        if(imgpath==null){
            return CommonResult.failed("图片样例不能为空");
        }
        String fileStr = File_Util.inputUploadFile(imgpath, uploadFolder,user.getLoginId());
        if(fileStr.equals("NOT_IMAGE")){
            return CommonResult.failed("图片上传失败");
        }else {
            String url="https://safe.jinchu.com.cn"+staticAccessPath+ fileStr;
            SysUserExample example=new SysUserExample();
            example.createCriteria().andLoginIdEqualTo(user.getLoginId()).andCorpidsEqualTo(user.getCorpids());
            SysUser sysUser=userMapper.selectOneByExample(example);
            if(sysUser!=null){
                sysUser.setImg(url);
                sysUser.setLastupdatetime(new Date());
                userMapper.updateByPrimaryKey(sysUser);
                redisOpsUtil.set(RedisKeyPrefixConst.USER_INFO+sysUser.getCorpids()+":"+sysUser.getLoginId(),sysUser);
                return CommonResult.success(null,"修改头像成功");
            }else {
                return CommonResult.failed("用户不存在");
            }
        }
    }
}
