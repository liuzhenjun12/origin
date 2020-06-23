package weixin.service;

import base.api.CommonResult;
import base.exception.BusinessException;
import base.mybatis.BaseService;
import weixin.model.SysLockXl;

public interface LockXlService extends BaseService<SysLockXl,Integer> {

    /**
     * 查询所有设备系列
     * @return
     */
     CommonResult getList();

    /**
     * 添加设备系列
     * @param lockXl
     * @return
     */
     CommonResult add(SysLockXl lockXl) throws BusinessException;

    /**
     * 修改设备系列
     * @param lockXl
     * @return
     * @throws BusinessException
     */
     CommonResult update(SysLockXl lockXl)throws BusinessException;

    /**
     * 删除设备系列
     * @param id
     * @return
     * @throws BusinessException
     */
     CommonResult delete(Integer id) throws BusinessException;
}
