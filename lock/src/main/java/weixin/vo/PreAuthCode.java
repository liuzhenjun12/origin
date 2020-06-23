package weixin.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PreAuthCode extends SupperBean implements Serializable {
    private static final long serialVersionUID = 5687601526423604760L;
    private String pre_auth_code;//预授权码
}
