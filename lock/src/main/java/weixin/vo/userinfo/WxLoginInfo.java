package weixin.vo.userinfo;

import lombok.Data;
import weixin.vo.SupperBean;

import java.io.Serializable;

@Data
public class WxLoginInfo extends SupperBean implements Serializable {
    private static final long serialVersionUID = -6576803813705666401L;
    private String CorpId;
    private String UserId;
    private String DeviceId;
    private String user_ticket;
    private Integer expires_in;
}
