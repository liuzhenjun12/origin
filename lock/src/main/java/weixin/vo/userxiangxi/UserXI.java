package weixin.vo.userxiangxi;

import lombok.Data;
import lombok.ToString;
import weixin.vo.SupperBean;

import java.io.Serializable;

@Data
@ToString
public class UserXI extends SupperBean implements Serializable {
    private static final long serialVersionUID = 951404767735176712L;
    private String userid;//登录用户的userid
    private String name;//登录用户的名字
    private String avatar;//登录用户的头像
    private Integer[] department;//成员所属部门id列表，仅返回该应用有查看权限的部门id
    private Integer[] order;//部门内的排序值，默认为0。数量必须和department一致，数值越大排序越前面。值范围是[0, 2^32)
    private String position;//	职务信息；第三方仅通讯录应用可获取
    private String mobile;//手机号码
    private String gender;//性别。0表示未定义，1表示男性，2表示女性
    private String email;//邮箱，第三方仅通讯录应用可获取
    private String[] is_leader_in_dept;//表示在所在的部门内是否为上级。；第三方仅通讯录应用可获取
    private String telephone;//座机。第三方仅通讯录应用可获取
    private Integer enable;//成员启用状态。1表示启用的成员，0表示被禁用。注意，服务商调用接口不会返回此字段
    private String alias;//别名；第三方仅通讯录应用可获取
    private String address;//地址
    private Integer status;//激活状态: 1=已激活，2=已禁用，4=未激活,已激活代表已激活企业微信或已关注微工作台（原企业号）。未激活代表既未激活企业微信又未关注微工作台（原企业号）。
    private String qr_code;//员工个人二维码，扫描可添加为外部联系人(注意返回的是一个url，可在浏览器上打开该url以展示二维码)；第三方仅通讯录应用可获取
    private String external_position;//
    private Extattr extattr;
    private ExternalProfile external_profile;
    private Integer main_department;

}
