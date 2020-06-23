package weixin.service;

import base.api.CommonResult;
import base.exception.BusinessException;
import weixin.model.SysLock;

public interface LockService {
    /**
     * 从企业微信后台获取设备列表
     * @return
     */
    CommonResult getLockList() throws BusinessException;

    /**
     * 添加设备并写入企业微信后台
     * @param lock
     * @return
     */
    CommonResult add(SysLock lock) throws BusinessException;

    /**
     * 删除设备
     * @param id
     * @return
     */
    CommonResult delete(Integer id) throws BusinessException;

    /**
     * 初始化设备
     * 从企业微信添加设备成功后，企业微信后台会推送添加成功通知。
     * @param corpid
     * @return
     */
    CommonResult init(String corpid);

    /**
     * 卸载设备
     * 从企业微信删除设备成功，企业微信后台会推送删除成功通知。
     * @param sn
     * @return
     */
    CommonResult unlock(String sn);

    /**
     * 修改设备名称
     * 从企业微信修改设备备注名称成功，企业微信后台会推送设备备注名称修改通知。
     * @return
     */
    CommonResult uplock(String sn,String name);

    /**
     * 设备网络连接成功事件
     * @param sn
     * @return
     */
    CommonResult connect(String sn);

    /**
     * 设备网络连接断开事件
     * @param sn
     * @return
     */
    CommonResult disconnect(String sn);

}
