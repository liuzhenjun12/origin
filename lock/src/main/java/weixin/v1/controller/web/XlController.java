package weixin.v1.controller.web;

import base.aop.Log;
import base.api.CommonResult;
import base.exception.BusinessException;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import weixin.model.SysLockXl;
import weixin.service.LockXlService;

@Slf4j
@RestController
@RequestMapping("/web/xl")
public class XlController {
    @Autowired
    LockXlService lockXlService;

    /**
     * 查询所有设备系列
     * @return
     */
    @RequestMapping("/list")
    public CommonResult getList(){
        return lockXlService.getList();
    }

    /**
     * 添加设备系列
     * @param lockXl
     * @return
     * @throws BusinessException
     */
    @Log(desc = "添加设备系列",type = Log.LOG_TYPE.ADD)
    @RequestMapping("/add")
    public CommonResult add(SysLockXl lockXl) throws BusinessException {
        return lockXlService.add(lockXl);
    }

    /**
     * 修改设备系列
     * @param lockXl
     * @return
     * @throws BusinessException
     */
    @Log(desc = "修改设备系列",type = Log.LOG_TYPE.UPDATE)
    @RequestMapping("/update")
    public CommonResult update(SysLockXl lockXl) throws BusinessException {
        return lockXlService.update(lockXl);
    }

    /**
     * 删除设备系列
     * @param id
     * @return
     * @throws BusinessException
     */
    @Log(desc = "删除设备系列",type = Log.LOG_TYPE.DEL)
    @RequestMapping("/delete")
    public CommonResult delete(Integer id) throws BusinessException {
        return lockXlService.delete(id);
    }
}
