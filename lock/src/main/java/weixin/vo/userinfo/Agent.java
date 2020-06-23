package weixin.vo.userinfo;

import lombok.Data;

@Data
public class Agent {
    private Integer agentid;//应用id
    private Integer auth_type;//该管理员对应用的权限：1.管理权限，0.使用权限
}
