package weixin.vo.permanentCode;

import lombok.Data;
import weixin.vo.SupperBean;

import java.io.Serializable;

@Data
public class DealerCorpInfo extends SupperBean implements Serializable {

    private static final long serialVersionUID = 7364248255919005208L;
    private String access_token;//授权方（企业）access_token,最长为512字节
    private String expires_in;//授权方（企业）access_token超时时间
    private String permanent_code;//企业微信永久授权码,最长为512字节
    private DealerCorpInfo dealer_corp_info;//代理服务商企业信息
    private Auth_corp_info auth_corp_info;//授权方企业信息
    private Auth_info auth_info;//授权信息。如果是通讯录应用，且没开启实体应用，是没有该项的。通讯录应用拥有企业通讯录的全部信息读写权限
    private Auth_user_info auth_user_info;//授权管理员的信息
}
