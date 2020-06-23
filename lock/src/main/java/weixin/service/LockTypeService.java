package weixin.service;

import base.api.CommonResult;
import base.exception.BusinessException;
import base.mybatis.BaseService;
import weixin.model.SysLockType;

public interface LockTypeService extends BaseService<SysLockType,Integer> {
    /**
     * 查询所有设备类型
     * @return
     */
    CommonResult getList();

    /**
     * 添加设备类型
     * @param type
     * @return
     */
    CommonResult add(SysLockType type) throws BusinessException;

    /**
     * 修改设备类型
     * @param type
     * @return
     * @throws BusinessException
     */
    CommonResult update(SysLockType type)throws BusinessException;

    /**
     * 删除设备类型
     * @param id
     * @return
     * @throws BusinessException
     */
    CommonResult delete(Integer id) throws BusinessException;
}
