package weixin.vo.permanentCode;

import lombok.Data;

import java.io.Serializable;

/**
 * 授权的应用信息，注意是一个数组，但仅旧的多应用套件授权时会返回多个agent，
 * 对新的单应用授权，永远只返回一个agent
 */
@Data
public class Agent implements Serializable {
    private Integer agentid;//授权方应用id
    private String name;//授权方应用名字
    private String round_logo_url;//授权方应用方形头像
    private String square_logo_url;//授权方应用圆形头像
    private String appid;//旧的多应用套件中的对应应用id，新开发者请忽略
    private Privilege privilege;//应用对应的权限
}
