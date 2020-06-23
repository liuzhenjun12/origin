package weixin.vo.permanentCode;

import lombok.Data;

import java.io.Serializable;

/**
 * 授权方企业信息
 */
@Data
public class Auth_corp_info implements Serializable {

    private static final long serialVersionUID = 3725528008021759045L;
    private String corpid;//授权方企业微信id
    private String corp_name;//授权方企业名称，即企业简称
    private String corp_type;//授权方企业类型，认证号：verified, 注册号：unverified
    private String corp_square_logo_url;//授权方企业方形头像
    private String corp_user_max;//授权方企业用户规模
    private String corp_full_name;//授权方企业的主体名称(仅认证或验证过的企业有)，即企业全称。
    private String verified_end_time;//认证到期时间
    private String subject_type;//企业类型，1. 企业; 2. 政府以及事业单位; 3. 其他组织, 4.团队号
    private String corp_wxqrcode;//授权企业在微工作台（原企业号）的二维码，可用于关注微工作台
    private String corp_scale;//企业规模。当企业未设置该属性时，值为空
    private String corp_industry;//企业所属行业。当企业未设置该属性时，值为空
    private String corp_sub_industry;//企业所属子行业。当企业未设置该属性时，值为空
    private String location;//企业所在地信息, 为空时表示未知
}
