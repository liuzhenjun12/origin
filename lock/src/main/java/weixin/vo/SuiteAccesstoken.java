package weixin.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SuiteAccesstoken  implements Serializable {
    private static final long serialVersionUID = 2753960390303874921L;
    private String suite_access_token;//第三方应用凭证
    private String expires_in;//有效期



	
	
	
}
