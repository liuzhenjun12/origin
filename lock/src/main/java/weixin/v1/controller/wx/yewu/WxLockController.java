package weixin.v1.controller.wx.yewu;

import base.aop.Log;
import base.api.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import weixin.mapper.SysLockMapper;
import weixin.model.SysLock;
import weixin.model.SysLockExample;

import java.util.List;
@RestController
@Slf4j
@RequestMapping("/wx/v1")
public class WxLockController {
    @Autowired
    SysLockMapper sysLockMapper;
    /**
     * 通过企业id、用户id获取设备列表
     * @param corpid
     * @param userid
     * @return
     */
    @RequestMapping("/wxgetByCorpAndUser")
    public CommonResult wxgetByCorpAndUser(String corpid, String userid){
        SysLockExample lockExample=new SysLockExample();
        lockExample.createCriteria().andCorpidEqualTo(corpid).andLoginidEqualTo(userid);
        List<SysLock> locks=sysLockMapper.selectByExample(lockExample);
        if(locks.isEmpty()){
            return CommonResult.failed("未找到设备");
        }else {
            return CommonResult.success(locks);
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
     * 通过企业id、用户id、设备编号开启设备
     * @param corpid
     * @param userid
     * @param deviceSn
     * @return
     */
    @RequestMapping("/wxopenLock")
    @Log(desc = "开锁",type = Log.LOG_TYPE.OPEN)
    public CommonResult wxopenLock(String corpid,String userid,String deviceSn){
        log.info("corpid:{},userid:{},deviceSn:{}",corpid,userid,deviceSn);
        return CommonResult.success(null,"开锁成功");
//        return CommonResult.failed("开锁失败");
    }

    /**
     * 修改设备名称、秘钥
     * @param id
     * @param name
     * @param pwd
     * @return
     */
    @RequestMapping("/wxupdateNm")
    @Log(desc = "修改设备名称、秘钥",type = Log.LOG_TYPE.UPDATE)
    public CommonResult wxupdateNm(Integer id,String name,String pwd){
        SysLock lock=sysLockMapper.selectByPrimaryKey(id);
        if(lock!=null){
            lock.setRootPwd(pwd);
            lock.setName(name);
            sysLockMapper.updateByPrimaryKey(lock);
            return CommonResult.success("修改成功");
        }else {
            return CommonResult.failed();
        }
    }
}
