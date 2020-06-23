package weixin.v1.controller.web;

import base.aop.Log;
import base.api.CommonResult;
import base.exception.BusinessException;
import base.util.QRcode_Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import weixin.mapper.SysLockMapper;
import weixin.model.SysLock;
import weixin.model.SysLockExample;
import weixin.service.LockService;
import weixin.vo.page.Grid;
import weixin.vo.page.PageFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/web/lock")
public class LockController {
    @Autowired
    SysLockMapper sysLockMapper;
    @Autowired
    LockService lockService;
    /**
     * 查询所有设备
     * @return
     */
    @RequestMapping("/list")
    public Grid getList(SysLock text, PageFilter ph){
        Grid grid=new Grid();
        SysLockExample example = new SysLockExample();
        if(!StringUtils.isEmpty(text.getCorpid())){
            example.createCriteria().andCorpidEqualTo(text.getCorpid());
        }
        if(!StringUtils.isEmpty(text.getDeviceSn())){
            example.createCriteria().andDeviceSnLike("%"+text.getDeviceSn()+"%");
        }
        if(!StringUtils.isEmpty(text.getStatus())){
            log.info("status==>{}",text.getStatus());
            example.createCriteria().andStatusEqualTo(text.getStatus());
        }
        RowBounds rowBounds = new RowBounds(ph.getPage()-1,ph.getRows());
        List<SysLock> ls = sysLockMapper.selectByExampleAndRowBounds(example,rowBounds);
        grid.setRows(ls);
        int count=sysLockMapper.selectCountByExample(example);
        grid.setTotal(count);
        return grid;
    }
    /**
     * 添加设备类型
     * @return
     */
    @Log(desc = "添加设备",type = Log.LOG_TYPE.ADD)
    @RequestMapping("/add")
    public CommonResult add(HttpServletRequest request, SysLock lock) throws BusinessException {
     return lockService.add(lock);
    }

    /**
     * 通过id获取设备
     * @param id
     * @return
     */
    @RequestMapping("/get")
    public CommonResult get(Integer id){
        SysLock lock=sysLockMapper.selectByPrimaryKey(id);
        if(lock==null){
            return CommonResult.failed("没有此设备");
        }else {
            return CommonResult.success(lock);
        }
    }

    /**
     * 动态生成可安装二维码
     * @param response
     * @param id
     */
    @RequestMapping(value = "/creatRrCode", method = {RequestMethod.GET, RequestMethod.POST} )
    public void creatRrCode(HttpServletResponse response, Integer id){
        log.info("id==>{}",id);
        SysLock lock=sysLockMapper.selectByPrimaryKey(id);
        if(lock==null){
            return;
        }
        QRcode_Util.creatRrCode(lock.getQrCode(),200,200,response);
    }

    /**
     * 删除设备
     * @param id
     * @return
     */
    @Log(desc = "删除设备",type = Log.LOG_TYPE.DEL)
    @RequestMapping("/delete")
    public CommonResult delete(Integer id) throws BusinessException {
        log.info("id==>{}",id);
        return lockService.delete(id);
    }

    /**
     * 通过企业id、用户id获取设备列表
     * @param corpid
     * @param userid
     * @return
     */
    @RequestMapping("/getByCorpAndUser")
    public CommonResult getByCorpAndUser(String corpid,String userid){
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
    @RequestMapping("/getByCorp")
    public CommonResult getByCorp(String corpid){
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
    @RequestMapping("/openLock")
    @Log(desc = "开锁",type = Log.LOG_TYPE.OPEN)
    public CommonResult openLock(String corpid,String userid,String deviceSn){
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
    @RequestMapping("/updateNm")
    @Log(desc = "修改设备名称、秘钥",type = Log.LOG_TYPE.UPDATE)
    public CommonResult updateNm(Integer id,String name,String pwd){
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

    public CommonResult fenpeisn(String corpid,String userid,String lockid){
        return null;
    }
}
