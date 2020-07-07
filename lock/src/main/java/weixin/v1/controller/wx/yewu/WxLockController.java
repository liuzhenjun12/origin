package weixin.v1.controller.wx.yewu;

import base.aop.Log;
import base.api.CommonResult;
import base.api.SocketCode;
import base.constant.RedisKeyPrefixConst;
import base.util.JSON_Util;
import base.util.MinaClient;
import base.util.sm3.SM3Digest;
import base.util.sm3.StringUtil;
import base.vo.ReturnJson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import weixin.mapper.SysLockMapper;
import weixin.mapper.SysLogMapper;
import weixin.mapper.SysTodoMapper;
import weixin.mapper.SysUserMapper;
import weixin.model.*;
import weixin.vo.UserLock;

import javax.servlet.http.HttpServletRequest;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@RestController
@Slf4j
@RequestMapping("/wx/v1")
public class WxLockController {
    @Autowired
    SysLockMapper sysLockMapper;
    @Autowired
    SysUserMapper userMapper;
    @Autowired
    SysTodoMapper todoMapper;
    @Autowired
    SysLogMapper logMapper;
    /**
     * 通过企业id、用户id获取设备列表
     * @param corpid
     * @param userid
     * @return
     */
    @RequestMapping("/wxgetByCorpAndUser")
    public CommonResult wxgetByCorpAndUser(String corpid, String userid){
        SysTodoExample todoExample=new SysTodoExample();
        todoExample.createCriteria().andCorpIdEqualTo(corpid).andUserIdEqualTo(userid);
        List<SysTodo> todos=todoMapper.selectByExample(todoExample);
        if(todos.isEmpty()){
            return CommonResult.failed("未找到设备");
        }else {
            return CommonResult.success(todos);
        }
    }

    /**
     * 通过id获取设备信息
     * @param id
     * @return
     */
    @RequestMapping("/wxgetLockByid")
    public CommonResult wxgetLockByid(Integer id){
        try {
            SysLock lock=sysLockMapper.selectByPrimaryKey(id);
            return CommonResult.success(lock);
        }catch (Exception e){
            return CommonResult.failed(e.getCause(),e.getMessage());
        }
    }

    /**
     * 通过企业id获取设备列表
     * @param corpid
     * @return
     */
    @RequestMapping("/wxgetByCorp")
    public CommonResult wxgetByCorp(String corpid){
        SysLockExample lockExample=new SysLockExample();
        lockExample.createCriteria().andCorpidEqualTo(corpid);
        List<SysLock> locks=sysLockMapper.selectByExample(lockExample);
        if(locks.isEmpty()){
            return CommonResult.failed("未找到设备");
        }else {
            return CommonResult.success(locks);
        }
    }
    /**
     * 通过企业id统计设备数量
     * @param corpid
     * @return
     */
    @RequestMapping("/wxgetByCorpCount")
    public CommonResult wxgetByCorpCount(String corpid){
        try {
            SysLockExample lockExample=new SysLockExample();
            lockExample.createCriteria().andCorpidEqualTo(corpid);
            int count =sysLockMapper.selectCountByExample(lockExample);
            return CommonResult.success(count);
        }catch (Exception e){
            return CommonResult.failed(e.getCause(),e.getMessage());
        }
    }
    /**
     * 通过企业id、用户id、设备编号开启设备
     * @param corpid
     * @param userid
     * @param deviceSn
     * @return
     */
    @RequestMapping("/wxopenLock")
    public CommonResult wxopenLock(String corpid, String userid, String deviceSn, HttpServletRequest request){
        log.info("corpid:{},userid:{},deviceSn:{}",corpid,userid,deviceSn);
        SysTodoExample todoExample=new SysTodoExample();
        todoExample.createCriteria().andUserIdEqualTo(userid).andCorpIdEqualTo(corpid).andDeviceSnEqualTo(deviceSn);
        SysTodo todo=todoMapper.selectOneByExample(todoExample);
        if(todo==null){
            return CommonResult.failed("开锁任务不存在");
        }
        SysLockExample lockExample=new SysLockExample();
        lockExample.createCriteria().andDeviceSnEqualTo(deviceSn);
        SysLock lock=sysLockMapper.selectOneByExample(lockExample);
        if(lock==null){
            return CommonResult.failed("设备不存在");
        }
        //TODO 生成开锁码
        String opencode = StringUtil.sm3(SM3Digest.StringToSM3(lock.getRootPwd() + deviceSn + (System.currentTimeMillis() / 60000)));
        //TODO 开锁请求
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        ReturnJson json=new ReturnJson(SocketCode.OPEN_LOCK.getCode(),sdf.format(new Date()),deviceSn,SocketCode.OPEN_LOCK.getMessage(),opencode,"no");
        //TODO 超时请求
        ReturnJson chao=new ReturnJson(SocketCode.OPEN_CONNENT_TIMEOUT.getCode(),sdf.format(new Date()),deviceSn,SocketCode.OPEN_CONNENT_TIMEOUT.getMessage(),"no","no");
        String result=new MinaClient().startClient(new InetSocketAddress(request.getServerName(), 8030),
                5000, JSON_Util.setResult(json), JSON_Util.setResult(chao));
        ReturnJson re=JSON_Util.getResult(result);
        if(re==null){
            return CommonResult.failed("服务器繁忙，请稍后重试");
        }
        if(SocketCode.OPEN_LOCK_SUCCCESS.getCode().equals(re.getCode())){
            SysLog sysLog=new SysLog(userid,corpid,"OPEN","wxopenLock","开锁",true,deviceSn+"开锁成功",new Date(),RedisKeyPrefixConst.OPEN_LOCK_IMG,RedisKeyPrefixConst.JIN_CHU);
            logMapper.insert(sysLog);
            return CommonResult.success(null,"开锁成功");
        }else {
            return CommonResult.failed("服务器繁忙，请稍后重试");
        }
    }

    /**
     * 修改设备名称、秘钥
     * @param id
     * @param name
     * @param pwd
     * @return
     */
    @RequestMapping("/wxupdateNm")
    public CommonResult wxupdateNm(Integer id,String name,String pwd, HttpServletRequest request){
        if(StringUtils.isEmpty(id)||StringUtils.isEmpty(name)||StringUtils.isEmpty(pwd)){
            return CommonResult.failed("参数不能为空");
        }else {
            SysLock lock=sysLockMapper.selectByPrimaryKey(id);
            if(lock==null){
                return CommonResult.failed("设备id错误");
            }
            lock.setName(name);
            log.info("pwd:{},root:{}",pwd,lock.toString());
            if(!pwd.equals(lock.getRootPwd())){
                //TODO 开锁请求
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                ReturnJson json=new ReturnJson(SocketCode.UPDATE_PSK.getCode(),sdf.format(new Date()),lock.getDeviceSn(),SocketCode.UPDATE_PSK.getMessage(),"no",pwd);
                //TODO 超时请求
                ReturnJson chao=new ReturnJson(SocketCode.OPEN_CONNENT_TIMEOUT.getCode(),sdf.format(new Date()),lock.getDeviceSn(),SocketCode.OPEN_CONNENT_TIMEOUT.getMessage(),"no","no");
                String result=new MinaClient().startClient(new InetSocketAddress(request.getServerName(), 8030),
                        5000, JSON_Util.setResult(json), JSON_Util.setResult(chao));
                ReturnJson re=JSON_Util.getResult(result);
                if(re==null){
                    return CommonResult.failed("服务器繁忙，请稍后重试");
                }
                if(re.getCode().equals(SocketCode.UPDATE_PSK_SUCCESS.getCode())){
                    log.info("indo:{}",lock.getRootPwd());
                    lock.setRootPwd(pwd);
                    sysLockMapper.updateByPrimaryKey(lock);
                }else {
                    return CommonResult.failed("服务器繁忙，请稍后重试");
                }
            }else {
                sysLockMapper.updateByPrimaryKey(lock);
            }
            SysLog sysLog = new SysLog(lock.getLoginid(), lock.getCorpid(), "UPDATE", "WxLockController.wxupdateNm", "修改设备名称、秘钥", true, "修改" + lock.getDeviceSn() + "名称、秘钥成功" , new Date(), RedisKeyPrefixConst.JINCHU_IMG,RedisKeyPrefixConst.JIN_CHU);
            logMapper.insert(sysLog);
            return CommonResult.success("修改成功");
        }
    }

    /**
     * 设备分配给成员列表
     * @param lockid
     * @return
     */
    @RequestMapping("/fenpei")
    public CommonResult fenpei(Integer lockid, String corpid){
        SysUserExample userExample=new SysUserExample();
        userExample.createCriteria().andCorpidsEqualTo(corpid);
        List<SysUser> userList=userMapper.selectByExample(userExample);
        List<UserLock> userLocks=new ArrayList<UserLock>();
        if(!userList.isEmpty()){
            for(SysUser u:userList){
                UserLock userLock=new UserLock();
                userLock.setUserid(u.getLoginId());
                userLock.setAvatar(u.getImg());
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
            return CommonResult.success(userLocks);
        }else {
            return CommonResult.failed();
        }
    }
}
