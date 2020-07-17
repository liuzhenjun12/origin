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
import org.slf4j.Logger;
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
     * 通过设备编号获取信息
     * @param sn
     * @return
     */
    @RequestMapping("/wxgetLockBySn")
    public CommonResult wxgetLockBySn(String sn){
        SysLockExample lockExample=new SysLockExample();
        lockExample.createCriteria().andDeviceSnEqualTo(sn);
        SysLock lock=sysLockMapper.selectOneByExample(lockExample);
        if(lock==null){
            return CommonResult.failed("不是金储智能设备");
        }
        if(lock.getStatus()==true){
            return CommonResult.filed(300,lock,"设备已经激活");
        }
        if(lock.getAttr6()==true){
            return CommonResult.failed("是WIFI连接设备，请在企业微信中扫码安装");
        }
        return CommonResult.success(lock,"可以初始化");
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
     * 开锁(包括wifi、蓝牙开锁) .
     * wifi开锁返回成功、失败
     * 蓝牙开锁返回开锁码
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
        String opencode = StringUtil.sm3(SM3Digest.StringToSM3(deviceSn+(lock.getMac().replaceAll(":",""))+lock.getRootPwd() +  (System.currentTimeMillis() / 60000)));
        //TODO 如果是WIFI模式
        if(lock.getAttr6()==true) {
            //TODO 如果开锁权限是远程开锁使用scoket
            if(todo.getOpenType().toString().equals("1")) {
                //TODO 开锁请求
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                ReturnJson json = new ReturnJson(SocketCode.OPEN_LOCK.getCode(), sdf.format(new Date()), deviceSn, SocketCode.OPEN_LOCK.getMessage(), opencode, "no");
                //TODO 超时请求
                ReturnJson chao = new ReturnJson(SocketCode.OPEN_CONNENT_TIMEOUT.getCode(), sdf.format(new Date()), deviceSn, SocketCode.OPEN_CONNENT_TIMEOUT.getMessage(), "no", "no");
                String result = new MinaClient().startClient(new InetSocketAddress(request.getServerName(), 8030),
                        5000, JSON_Util.setResult(json), JSON_Util.setResult(chao));
                ReturnJson re = JSON_Util.getResult(result);
                if (re == null) {
                    return CommonResult.failed("服务器繁忙，请稍后重试");
                }
                if (SocketCode.OPEN_LOCK_SUCCCESS.getCode().equals(re.getCode())) {
                    SysLog sysLog = new SysLog(userid, corpid, "OPEN", "wxopenLock", "开锁", true, deviceSn + "开锁成功", new Date(), RedisKeyPrefixConst.OPEN_LOCK_IMG, RedisKeyPrefixConst.JIN_CHU);
                    logMapper.insert(sysLog);
                    return CommonResult.success(null, "开锁成功");
                } else {
                    return CommonResult.failed("服务器繁忙，请稍后重试");
                }
            }else {
                //TODO 如果是蓝牙模式直接返回开锁码
                return CommonResult.success(300,opencode,"返回开锁码");
            }
        }else {
            //TODO 如果是蓝牙模式直接返回开锁码
            return CommonResult.success(300,opencode,"返回开锁码");
        }
    }

    /**
     * 记录蓝牙开锁成功
     * @param corpid
     * @param userid
     * @param deviceSn
     * @return
     */
    @RequestMapping("/macOpenLockSuccess")
    public CommonResult macOpenLockSuccess(String corpid, String userid, String deviceSn){
        SysLog sysLog = new SysLog(userid, corpid, "OPEN", "wxopenLock", "开锁", true, deviceSn + "开锁成功", new Date(), RedisKeyPrefixConst.OPEN_LOCK_IMG, RedisKeyPrefixConst.JIN_CHU);
        logMapper.insert(sysLog);
        return CommonResult.success(null, "开锁成功");
    }

    /**
     * 修改设备名称
     * @param id
     * @param name
     * @return
     */
    @RequestMapping("/wxupdateNm")
    public CommonResult wxupdateNm(Integer id,String name,HttpServletRequest request){
        if(StringUtils.isEmpty(id)||StringUtils.isEmpty(name)){
            return CommonResult.failed("参数不能为空");
        }else {
            SysLock lock=sysLockMapper.selectByPrimaryKey(id);
            if(lock==null){
                return CommonResult.failed("设备id错误");
            }
            lock.setName(name);
            sysLockMapper.updateByPrimaryKey(lock);
            SysLog sysLog = new SysLog(lock.getLoginid(), lock.getCorpid(), "UPDATE", "WxLockController.wxupdateNm", "修改设备名称", true, "修改" + lock.getDeviceSn() + "名称成功" , new Date(), RedisKeyPrefixConst.JINCHU_IMG,RedisKeyPrefixConst.JIN_CHU);
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

    /**
     * 判断蓝牙设备是否可以初始化
     * @param mac
     * @return
     */
    @RequestMapping("/addMacLock")
    public CommonResult addMacLock(String mac){
        if(StringUtils.isEmpty(mac)){
            return CommonResult.failed("设备蓝牙不能为空");
        }
        SysLockExample lockExample=new SysLockExample();
        lockExample.createCriteria().andMacEqualTo(mac);
        SysLock lock=sysLockMapper.selectOneByExample(lockExample);
        if(lock==null){
            return CommonResult.failed("设备不存在");
        }
        if(lock.getStatus()==true){
            return CommonResult.filed(300,lock,"设备已经激活");
        }
        if(lock.getAttr6()==true){
            return CommonResult.failed("是WIFI连接设备，请在企业微信中扫码安装");
        }
        return CommonResult.success(lock,"可以初始化");
    }

    /**
     * 扫码、自动发现成功后确认初始化设备
     * @param lock
     * @return
     */
    @RequestMapping("/firmAddLock")
    public CommonResult firmAddLock(SysLock lock){
        if(lock==null){
            return CommonResult.failed("参数不全");
        }
        if(StringUtils.isEmpty(lock.getRootPwd())||StringUtils.isEmpty(lock.getId())){
            return CommonResult.failed("参数不全");
        }
        log.info(lock.toString());
        try {
            SysLock sysLock=sysLockMapper.selectByPrimaryKey(lock.getId());
            if(sysLock==null){
                return CommonResult.failed("不是金储智能设备");
            }
            if(sysLock.getStatus()==true){
                return CommonResult.failed(300,"设备已经激活,卸载设备吗?");
            }
            sysLock.setLastupdatetime(new Date());
            sysLock.setRootPwd(lock.getRootPwd());
            sysLock.setAttr4(1);
            sysLock.setName(lock.getName());
            sysLock.setLoginid(lock.getLoginid());
            sysLock.setCorpid(lock.getCorpid());
            sysLock.setCorpname(lock.getCorpname());
            sysLock.setMac(lock.getMac());
            sysLock.setStatus(true);
            sysLockMapper.updateByPrimaryKey(sysLock);
            SysTodo todo=new SysTodo();
            todo.setCreateby(sysLock.getLoginid());
            todo.setCreateDate(new Date());
            todo.setUserId(sysLock.getLoginid());
            todo.setCorpId(sysLock.getCorpid());
            todo.setAttr1(sysLock.getModelId());//TODO 设备类型
            todo.setOpenType(1);//TODO 开锁类型，1、远程开锁。2、蓝牙开锁
            todo.setTodoType(1);//TODO 任务类型，1、长期。2、短期
            todo.setIcon(sysLock.getIcon());
            todo.setDeviceSn(sysLock.getDeviceSn());
            todo.setStatus(true);
            todoMapper.insert(todo);
            //TODO 记录日志
            SysLog sysLog = new SysLog(lock.getLoginid(), lock.getCorpid(), "ADD",
                    "LockServiceImpl.init", "初始化设备", true, "初始化设备【"+sysLock.getDeviceSn()+"】成功",
                    new Date(),RedisKeyPrefixConst.JINCHU_IMG,RedisKeyPrefixConst.JIN_CHU);
            logMapper.insert(sysLog);
            return CommonResult.success(null,"初始化设备【"+sysLock.getDeviceSn()+"】成功");
        }catch (Exception e){
            return CommonResult.failed(e.getCause(),e.getMessage());
        }
    }

    /**
     * 卸载蓝牙设备
     * @param lockid
     * @return
     */
    @RequestMapping("/frimDelLock")
    public CommonResult frimDelLock(Integer lockid){
        if(lockid==null){
            return CommonResult.failed("参数错误");
        }
        try {
            //TODO 记录日志
            SysLock lock=sysLockMapper.selectByPrimaryKey(lockid);
            if(lock==null){
                return CommonResult.failed("设备不存在");
            }
            SysLog sysLog=new SysLog();
            sysLog.setMethodjc("卸载设备");
            sysLog.setCorpid(lock.getCorpid());
            sysLog.setLoginid(lock.getLoginid());

            lock.setStatus(false);
            lock.setCorpname(null);
            lock.setCorpid(null);
            lock.setName(null);
            lock.setLoginid(null);
            lock.setAttr4(0);
            lock.setRootPwd("123456");
            lock.setLastupdatetime(new Date());
            //TODO 修改设备为未激活状态
            sysLockMapper.updateByPrimaryKey(lock);
            //TODO 删除任务表
            SysTodoExample todoExample=new SysTodoExample();
            todoExample.createCriteria().andDeviceSnEqualTo(lock.getDeviceSn());
            todoMapper.deleteByExample(todoExample);

            sysLog.setCreatedate(new Date());
            sysLog.setMethodtype("DEL");
            sysLog.setMethodname("LockServiceImpl.unlock");
            sysLog.setSccess(true);
            sysLog.setResult("卸载设备【"+lock.getDeviceSn()+"】成功");
            sysLog.setImg(RedisKeyPrefixConst.JINCHU_IMG);
            sysLog.setAttr1(RedisKeyPrefixConst.JIN_CHU);
            logMapper.insert(sysLog);
            return CommonResult.success(null,"卸载设备【"+lock.getDeviceSn()+"】成功");
        }catch (Exception e){
            return CommonResult.failed(e.getCause(),e.getMessage());
        }
    }
}
