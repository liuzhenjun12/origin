package weixin.vo.userinfo;

import lombok.Data;

@Data
public class UserInfo  {
    private String userid;//登录用户的userid，登录用户在通讯录中时返回
    private String name;//登录用户的名字，登录用户在通讯录中时返回
    private String avatar;//登录用户的头像，登录用户在通讯录中时返回
}
