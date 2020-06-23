package weixin.vo.permanentCode;

import lombok.Data;

import java.io.Serializable;

/**
 * 代理服务商企业信息
 */
@Data
public class Dealer_corp_info implements Serializable {
    private static final long serialVersionUID = -4849828721713473175L;
    private String corpid;//代理服务商企业微信id
    private String corp_name;//代理服务商企业微信名称
}
