package weixin.vo.userinfo;

import lombok.Data;
import weixin.vo.SupperBean;

import java.io.Serializable;
import java.util.List;

@Data
public class LoginInfo extends SupperBean implements Serializable {

    private static final long serialVersionUID = 6245936782891753101L;
    private Integer usertype;//登录用户的类型：1.创建者 2.内部系统管理员 3.外部系统管理员 4.分级管理员 5.成员
    private UserInfo user_info;//登录用户的信息
    private CorpInfo corp_info;//授权方企业信息
    private List<Agent> agent;//该管理员在该提供商中能使用的应用列表，当登录用户为管理员时返回
    private AuthInfo auth_info;//该管理员拥有的通讯录权限，当登录用户为管理员时返回
    private String isAdmin;//是否是管理员
    private String org_name;//公司名称
}
