package weixin.impl;

import base.aop.Log;
import base.api.CommonResult;
import base.api.SocketCode;
import base.constant.RedisKeyPrefixConst;
import base.exception.BusinessException;
import base.mybatis.BaseMapper;
import base.mybatis.BaseServiceImpl;
import base.util.JSON_Util;
import base.util.MinaClient;
import base.util.RedisOpsUtil;
import base.vo.ReturnJson;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpRequest;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import weixin.mapper.SysLockMapper;
import weixin.mapper.SysLockTypeMapper;
import weixin.mapper.SysLogMapper;
import weixin.mapper.SysTodoMapper;
import weixin.model.*;
import weixin.service.BaseConfigService;
import weixin.service.LockService;
import weixin.vo.InitLock;
import weixin.vo.SupperBean;
import weixin.vo.admin.Admin;
import weixin.vo.device.Device;
import weixin.vo.device.DeviceInfo;
import weixin.vo.permanentCode.DealerCorpInfo;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class LockServiceImpl  extends BaseServiceImpl<SysLock, Integer> implements LockService {
    @Autowired
    SysLockMapper lockMapper;
    @Autowired
    SysLockTypeMapper typeMapper;
    @Autowired
    SysLogMapper logMapper;
    @Autowired
    SysTodoMapper todoMapper;
    @Autowired
    RedisOpsUtil redisOpsUtil;
    @Autowired
    BaseConfigService baseConfigService;

    /**
     * 从企业微信后台获取设备列表
     * @return
     */
    @Override
    public CommonResult getLockList() throws BusinessException {
        String provider_access_token=baseConfigService.get_provider_token();
        return null;
    }

    /**
     * 添加设备
     * @param lock
     * @return
     * @throws BusinessException
     */
    @Override
    public CommonResult add(SysLock lock) throws BusinessException {
        if(lock==null){
            return CommonResult.failed("参数异常!");
        }
        if(StringUtils.isEmpty(lock.getModelId())||StringUtils.isEmpty(lock.getDeviceSn())||StringUtils.isEmpty(lock.getMac())){
            return CommonResult.failed("参数异常!");
        }
        if(lockMapper.checkMac(lock.getMac())>0){
            return CommonResult.failed("设备蓝牙地址已存在!");
        }
        SysLockExample example = new SysLockExample();
        example.createCriteria().andDeviceSnEqualTo(lock.getDeviceSn());
        List<SysLock> ls = lockMapper.selectByExample(example);
        if (!ls.isEmpty()) {
            return CommonResult.failed("设备编号已存在!");
        }
        SysLockTypeExample typeExample=new SysLockTypeExample();
        typeExample.createCriteria().andSbtypeEqualTo(lock.getModelId());
        SysLockType sysLockType=typeMapper.selectOneByExample(typeExample);
        if(sysLockType==null){
            return CommonResult.failed("设备类型不存在!");
        }
        String provider_token=baseConfigService.get_provider_token();
        StringBuffer strb = new StringBuffer();
        strb.append(" { ");
        strb.append("\"model_id\": \""+lock.getModelId()+"\",");
        strb.append("\"device_sn\": \""+lock.getDeviceSn()+"\"");
        strb.append(" } ");
        log.info("发送给企业微信服务器json数据包:"+strb.toString());
        try {
            String   str = Request.Post("https://qyapi.weixin.qq.com/cgi-bin/service/add_device?provider_access_token="+provider_token)
                    .bodyString(strb.toString(), ContentType.APPLICATION_JSON)
                    .execute()
                    .returnContent()
                    .asString(Charset.forName("UTF-8"));
            log.info("str==>{}",str);
            if(StringUtils.isEmpty(str)){
                return CommonResult.failed("网络异常，请刷新后重试");
            }
            if(str.indexOf("600021")!=-1){
                return CommonResult.failed("设备编号已存在");
            }
            if(str.indexOf("600020")!=-1){
                return CommonResult.failed("请求域名错误");
            }
            Gson gson = new Gson();
            Device device =gson.fromJson(str, Device.class);
            if(device==null){
                return CommonResult.failed("解析异常!");
            }
            DeviceInfo info=device.getDevice_info();
            lock.setDeviceId(info.getDevice_id());
            lock.setCreatedatetime(new Date());
            lock.setSecretNo(info.getSecret_no());
            lock.setQrCode(info.getQr_code());
            lock.setIcon(sysLockType.getImg());
            lock.setStatus(false);
            lock.setBattery("100");
            lock.setAttr4(0);
            lock.setAttr5(0);
            lock.setRootPwd("123456");
            lock.setConnectWx(false);
            lock.setConnectHt(false);
            lockMapper.insert(lock);
            return CommonResult.success(null,"添加设备成功");
        } catch (IOException e) {
            return CommonResult.success(e.getCause(),e.getMessage());
        }
    }

    /**
     * 删除设备
     * @param id
     * @return
     * @throws BusinessException
     */
    @Override
    public CommonResult delete(Integer id) throws BusinessException {
        SysLock sysLock=lockMapper.selectByPrimaryKey(id);
        if (sysLock==null) {
            return CommonResult.failed(null,"设备不存在!");
        }
        if(sysLock.getStatus()){
            return CommonResult.failed(null,"设备已经激活不能删除!");
        }
        String provider_token=baseConfigService.get_provider_token();
        StringBuffer strb = new StringBuffer();
        strb.append(" { ");
        strb.append("\"device_sn\": \""+sysLock.getDeviceSn()+"\"");
        strb.append(" } ");
        try {
            String   str = Request.Post("https://qyapi.weixin.qq.com/cgi-bin/service/del_device?provider_access_token="+provider_token)
                    .bodyString(strb.toString(), ContentType.APPLICATION_JSON)
                    .execute()
                    .returnContent()
                    .asString(Charset.forName("UTF-8"));
            log.info("str==>{}",str);
            if(StringUtils.isEmpty(str)){
                return CommonResult.failed(null,"网络异常，请刷新后重试");
            }
            Gson gson = new Gson();
            SupperBean device =gson.fromJson(str, SupperBean.class);
            if(device==null){
                return CommonResult.failed("解析异常!");
            }
            if(!"ok".equals(device.getErrmsg())){
                return CommonResult.failed(null,"设备已激活，不能删除");
            }
            lockMapper.deleteByPrimaryKey(id);
            return CommonResult.success(null,"删除设备成功");
        } catch (IOException e) {
            return CommonResult.success(e.getCause(),e.getMessage());
        }
    }

    /**
     * 初始化设备
     * 从企业微信添加设备成功后，企业微信后台会推送添加成功通知。
     * @param corpid
     * @return
     */
    @Override
    public CommonResult init(String corpid) {
        if(StringUtils.isEmpty(corpid)){
            log.info("错误信息==>参数错误");
            return CommonResult.failed("参数错误");
        }
        DealerCorpInfo dealerCorpInfo=redisOpsUtil.get(RedisKeyPrefixConst.PERMANENT_CODE+corpid,DealerCorpInfo.class);
        if(dealerCorpInfo==null){
            log.info("错误信息==>企业信息不存在");
            return CommonResult.failed("企业信息不存在");
        }
        SysCorp corp=redisOpsUtil.get(RedisKeyPrefixConst.CORP_INFO+corpid,SysCorp.class);
        if(corp==null){
            log.info("错误信息==>企业不存在");
            return CommonResult.failed("企业不存在");
        }
        InitLock init=redisOpsUtil.get(RedisKeyPrefixConst.INIT_LOCK+corpid,InitLock.class);
        if(init==null){
            log.info("错误信息==>缓存参数错误");
            return CommonResult.failed("缓存参数错误");
        }
        SysLockExample example = new SysLockExample();
        example.createCriteria().andDeviceSnEqualTo(init.getDeviceSn()).andModelIdEqualTo(init.getModelId());
        SysLock lock=lockMapper.selectOneByExample(example);
        if(lock==null){
            log.info("错误信息==>设备不存在");
            return CommonResult.failed("设备不存在");
        }
        if(lock.getStatus()){
            log.info("错误信息==>设备已经激活");
            return CommonResult.failed("设备已经激活");
        }
        lock.setStatus(true);
        lock.setLastupdatetime(new Date());
        if(!StringUtils.isEmpty(init.getRemarkName())) {
            lock.setName(init.getRemarkName());
        }
        lock.setCorpid(corpid);
        lock.setAttr4(1);
        lock.setCorpname(corp.getCorpName());
        lock.setLoginid(dealerCorpInfo.getAuth_user_info().getUserid());
        lockMapper.updateByPrimaryKey(lock);
        //TODO 将初始化设备用户添加到任务中，设置为长期使用用户
        SysTodo todo=new SysTodo();
        todo.setCreateby(dealerCorpInfo.getAuth_user_info().getUserid());
        todo.setCreateDate(new Date());
        todo.setUserId(dealerCorpInfo.getAuth_user_info().getUserid());
        todo.setCorpId(corpid);
        todo.setOpenType(1);//TODO 开锁类型，1、远程开锁。2、蓝牙开锁
        todo.setTodoType(1);//TODO 任务类型，1、长期。2、短期
        todo.setIcon(lock.getIcon());
        todo.setDeviceSn(init.getDeviceSn());
        todo.setStatus(true);
        todoMapper.insert(todo);
        //TODO 记录日志
        SysLog sysLog = new SysLog(dealerCorpInfo.getAuth_user_info().getUserid(), corpid, "ADD",
                "LockServiceImpl.init", "初始化设备", true, "初始化设备【"+init.getDeviceSn()+"】成功",
                new Date(),RedisKeyPrefixConst.JINCHU_IMG,RedisKeyPrefixConst.JIN_CHU);
        logMapper.insert(sysLog);
        return CommonResult.success(null,"初始化设备【"+init.getDeviceSn()+"】成功");
    }

    /**
     * 卸载设备
     * 从企业微信删除设备成功，企业微信后台会推送删除成功通知。
     * @param sn
     * @return
     */
    @Override
    public CommonResult unlock(String sn) {
        if(StringUtils.isEmpty(sn)){
            return CommonResult.failed("参数错误");
        }
        SysLockExample example = new SysLockExample();
        example.createCriteria().andDeviceSnEqualTo(sn);
        SysLock lock=lockMapper.selectOneByExample(example);
        if(lock==null){
            return CommonResult.failed("设备不存在");
        }
        //TODO 记录日志
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
        lock.setLastupdatetime(new Date());
        //TODO 修改设备为未激活状态
        lockMapper.updateByPrimaryKey(lock);

        sysLog.setCreatedate(new Date());
        sysLog.setMethodtype("DEL");
        sysLog.setMethodname("LockServiceImpl.unlock");
        sysLog.setSccess(true);
        sysLog.setResult("卸载设备【"+sn+"】成功");
        sysLog.setImg(RedisKeyPrefixConst.JINCHU_IMG);
        sysLog.setAttr1(RedisKeyPrefixConst.JIN_CHU);
        logMapper.insert(sysLog);
        return CommonResult.success(null,"卸载设备【"+sn+"】成功");
    }

    /**
     * 修改设备名称
     * 从企业微信修改设备备注名称成功，企业微信后台会推送设备备注名称修改通知。
     * @return
     */
    @Override
    public CommonResult uplock(String sn,String name) {
        SysLockExample example = new SysLockExample();
        example.createCriteria().andDeviceSnEqualTo(sn);
        SysLock lock=lockMapper.selectOneByExample(example);
        if(lock==null){
            return CommonResult.failed("设备不存在");
        }
        lock.setName(name);
        lockMapper.updateByPrimaryKey(lock);
        //TODO 记录日志
        SysLog sysLog = new SysLog(lock.getLoginid(), lock.getCorpid(), "UPDATE",
                "LockServiceImpl.uplock", "修改设备名称", true, "修改设备名称【"+sn+"】成功",
                new Date(),RedisKeyPrefixConst.JINCHU_IMG,RedisKeyPrefixConst.JIN_CHU);
        logMapper.insert(sysLog);
        return CommonResult.success(null,"修改设备名称【"+sn+"】成功");
    }

    /**
     * 设备网络连接成功事件
     * @param sn
     * @return
     */
    @Override
    public CommonResult connect(String sn) {
        SysLockExample example = new SysLockExample();
        example.createCriteria().andDeviceSnEqualTo(sn);
        SysLock lock=lockMapper.selectOneByExample(example);
        if(lock==null){
            return CommonResult.failed("设备不存在");
        }
        lock.setConnectWx(true);
        lockMapper.updateByPrimaryKey(lock);
        //TODO 记录日志
        SysLog sysLog = new SysLog(lock.getLoginid(), lock.getCorpid(), "CONNECT",
                "LockServiceImpl.connect", "设备网络连接成功", true, "设备网络连接【"+sn+"】成功",
                new Date(),RedisKeyPrefixConst.JINCHU_IMG,RedisKeyPrefixConst.JIN_CHU);
        logMapper.insert(sysLog);
        return CommonResult.success(null,"设备网络连接【"+sn+"】成功");
    }

    /**
     * 设备网络连接断开事件
     * @param sn
     * @return
     */
    @Override
    public CommonResult disconnect(String sn) {
        SysLockExample example = new SysLockExample();
        example.createCriteria().andDeviceSnEqualTo(sn);
        SysLock lock=lockMapper.selectOneByExample(example);
        if(lock==null){
            return CommonResult.failed("设备不存在");
        }
        lock.setConnectWx(false);
        lockMapper.updateByPrimaryKey(lock);
        //TODO 记录日志
        SysLog sysLog = new SysLog(lock.getLoginid(), lock.getCorpid(), "CONNECT",
                "LockServiceImpl.disconnect", "设备网络连接断开", true, "设备网络断开连接【"+sn+"】",
                new Date(),RedisKeyPrefixConst.JINCHU_IMG,RedisKeyPrefixConst.JIN_CHU);
        logMapper.insert(sysLog);
        return CommonResult.success(null,"设备网络断开连接【"+sn+"】");
    }

    @Override
    public BaseMapper<SysLock, Integer> getMappser() {
        return lockMapper;
    }
}
