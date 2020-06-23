package weixin.service;

import base.api.CommonResult;
import base.mybatis.BaseService;
import weixin.model.SysCorp;

public interface CorpService extends BaseService<SysCorp,Integer> {
    /**
     * 添加企业
     * @param corpid
     * @return
     */
    public CommonResult addCorp(String corpid);

    /**
     * 添加部门
     * @param corpid
     * @return
     */
    public CommonResult addDrep(String corpid);
}
