package weixin.vo.permanentCode;

import lombok.Data;

import java.io.Serializable;

/**
 * 应用对应的权限
 */
@Data
public class Privilege implements Serializable {

    private static final long serialVersionUID = 7964205053249354824L;
    /**
     * level权限等级。
     * 1:通讯录基本信息只读
     * 2:通讯录全部信息只读
     * 3:通讯录全部信息读写
     * 4:单个基本信息只读
     * 5:通讯录全部信息只写
     */
    private String level;
    private String[] allow_party;//应用可见范围（部门）
    private String[] allow_user;//应用可见范围（成员）
    private String[] allow_tag;//应用可见范围（标签）
    private String[] extra_party;//额外通讯录（部门）
    private String[] extra_user;//额外通讯录（成员）
    private String[] extra_tag;//额外通讯录（标签）
}
