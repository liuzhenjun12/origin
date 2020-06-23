package weixin.service;

import base.api.CommonResult;
import base.exception.BusinessException;
import weixin.vo.BaseConfig;
import weixin.vo.PreAuthCode;

public interface BaseConfigService {

    /**
     * 缓存基本配置
     * @param baseConfig
     */
    public void set_base_config(BaseConfig baseConfig);

    /**
     * 缓存每10分钟，微信服务器发送的凭证
     * @param suiteTicket
     */
    public  void set_suite_ticket(String suiteTicket);

    /**
     * 获取第三方应用凭证
     * 请求方式POST
     * @param {
     *     "suite_id":"wwf814af55e9ec28p0" ,
     *     "suite_secret": "ldAE_H9anCRN21GKXVVmqeJsFHrIAUXzv9OT5frYpeh",
     *     "suite_ticket": "Cfp0_givEagXcYJIztF6sfbdmIZCmpaR8ZBsvJEFFNBrWmnD5-CGYJ3_NhYexMyw"
     * }
     * suite_id 以ww或wx开头应用id（对应于旧的以tj开头的套件id）
     * suite_secret 应用secret
     * suite_ticket 企业微信后台推送的ticket
     * @return{
     *     "errcode":0 ,
     *     "errmsg":"ok" ,
     *     "suite_access_token":"61W3mEpU66027wgNZ_MhGHNQDHnFATkDa9-2llMBjUwxRSNPbVsMmyD-yq8wZETSoE5NQgecigDrSHkPtIYA",
     *     "expires_in":7200
     * }
     */
    public String get_suite_access_token() throws BusinessException;

    /**
     *获取预授权码
     * @param
     * @return{
     *     "errcode":0 ,
     *     "errmsg":"ok" ,
     *     "pre_auth_code":"Cx_Dk6qiBE0Dmx4EmlT3oRfArPvwSQ-oa3NL_fwHM7VI08r52wazoZX2Rhpz1dEw",
     *     "expires_in":1200
     * }
     */
    public String get_pre_auth_code() throws BusinessException;

    /**
     * 该接口可对某次授权进行配置。可支持测试模式（应用未发布时）。
     * @param auth_type 授权类型：0 正式授权， 1 测试授权。 默认值为0。注意，请确保应用在正式发布后的授权类型为“正式授权”
     * @return {
     *     "errcode": 0,
     *     "errmsg": "ok"
     * }
     */
    public String SetSessionInfo(String auth_type) throws BusinessException;

    /**
     * I用于使用临时授权码换取授权方的永久授权码，并换取授权信息、企业access_token，临时授权码一次有效。
     * 建议第三方以userid为主键，来建立自己的管理员账号。
     * 请求方式：POST（HTTPS）
     * 请求包体：
     * {
     *     "auth_code": "auth_code_value"
     * }
     * auth_code:临时授权码，会在授权成功时附加在redirect_uri中跳转回第三方服务商网站，
     * 或通过回调推送给服务商。长度为64至512个字节
     */
    public String get_permanent_code(String auth_code) throws BusinessException;

    /**
     * 获取授权企业token
     * @param auth_cropid
     * @return
     */
    public String getAccessToken(String auth_cropid) throws BusinessException;

    /**
     * 用户删除第三方应用
     * @param auth_code
     * @return
     */
    public CommonResult deleteAll(String auth_code) throws BusinessException;

    /**
     * 服务商的token
     *
     * 以corpid、provider_secret（获取方法为：登录服务商管理后台->标准应用服务->通用开发参数，
     * 可以看到）换取provider_access_token，代表的是服务商的身份，而与应用无关。请求单点登录、
     * 注册定制化等接口需要用到该凭证。
     * @return
     */
    public String get_provider_token() throws BusinessException;

}
