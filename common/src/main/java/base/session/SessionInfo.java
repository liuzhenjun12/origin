package base.session;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class SessionInfo implements Serializable {

    private static final long serialVersionUID = 2548191509997169279L;

    /**
     * 企业微信id
     */
    private String loginId;

    /**
     * 角色id,1、管理员，2、成员，3、超级管理员（服务商内部人员之内）
     */
    private Integer roleid;

    /**
     * 企业id
     */
    private String corpids;

    /**
     * 企业名称
     */
    private String corpname;

}
