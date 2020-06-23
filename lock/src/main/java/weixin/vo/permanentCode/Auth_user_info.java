package weixin.vo.permanentCode;

import lombok.Data;

import java.io.Serializable;

/**
 * 授权管理员信息
 */
@Data
public class Auth_user_info implements Serializable {

    private static final long serialVersionUID = 5280797593223534467L;
    private String userid;//授权管理员的userid，可能为空（内部管理员一定有，不可更改）
    private String name;//授权管理员的name，可能为空（内部管理员一定有，不可更改）
    private String avatar;//授权管理员的头像url
}
