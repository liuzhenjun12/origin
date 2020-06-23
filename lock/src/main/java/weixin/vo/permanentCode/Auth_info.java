package weixin.vo.permanentCode;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 授权信息。如果是通讯录应用，且没开启实体应用，是没有该项的。
 * 通讯录应用拥有企业通讯录的全部信息读写权限
 */
@Data
public class Auth_info implements Serializable {
    private List<Agent> agent;
}
