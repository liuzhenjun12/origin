package weixin.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Accesstoken extends SupperBean implements Serializable {

    private static final long serialVersionUID = -5379470452159226914L;

    private String access_token;//授权方（企业）access_token

    private String expires_in;//有效期
}
