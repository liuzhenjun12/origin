package weixin.service;

import base.api.CommonResult;
import base.exception.BusinessException;
import base.mybatis.BaseService;
import weixin.mapper.SysUserMapper;
import weixin.model.SysUser;

public interface UserService extends BaseService<SysUser,Integer> {
    /**
     *添加成员
     * @param corpid
     * @return
     */
    public CommonResult addUser(String corpid);

    /**
     * 添加管理员
     * @param corpid
     * @return
     */
    public CommonResult addAdmin(String corpid) throws BusinessException;

    /**
     * 修改用户信息
     * @param corpid
     * @return
     */
    public CommonResult updateUser(String corpid) throws BusinessException;

    /**
     * 第三方可通过如下接口，获取登录用户的信息。建议用户以返回信息中的corpid及userid为主键匹配用户。
     * 请求方式：POST（HTTPS）
     * 请求地址： https://qyapi.weixin.qq.com/cgi-bin/service/get_login_info?access_token=PROVIDER_ACCESS_TOKEN
     * @return
     */
    public CommonResult get_login_info(String authCode) throws BusinessException;
}
