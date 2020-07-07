package weixin.vo;

import lombok.Data;

import java.io.Serializable;
@Data
public class XiaoLoginInfo extends SupperBean implements Serializable {
    private static final long serialVersionUID = -4967735164003266428L;
    private String corpid;
    private String userid;
    private String session_key;
}
