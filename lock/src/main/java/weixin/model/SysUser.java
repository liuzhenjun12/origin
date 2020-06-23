package weixin.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

public class SysUser implements Serializable {
    @Id
    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "微信名称")
    private String loginId;

    @ApiModelProperty(value = "密码")
    private String password;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createdatetime;

    @ApiModelProperty(value = "电话")
    private String phone;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "修改时间")
    private Date lastupdatetime;

    @ApiModelProperty(value = "是否禁用")
    private Boolean status;

    @ApiModelProperty(value = "角色id,1、管理员，2、成员，3、超级管理员")
    private Integer roleId;

    @ApiModelProperty(value = "企业id")
    private String corpids;

    @ApiModelProperty(value = "头像")
    private String img;

    @ApiModelProperty(value = "部门id")
    private Integer department;

    @ApiModelProperty(value = "上级部门id")
    private Integer mainDepartment;

    @ApiModelProperty(value = "企业名称")
    private String corpname;

    @ApiModelProperty(value = "用户邮箱")
    private String email;

    @ApiModelProperty(value = "备用字段")
    private String attr1;

    @ApiModelProperty(value = "备用字段")
    private String attr2;

    @ApiModelProperty(value = "备用字段")
    private String attr3;

    @ApiModelProperty(value = "备用字段")
    private Integer attr4;

    @ApiModelProperty(value = "备用字段")
    private Integer attr5;

    @ApiModelProperty(value = "备用字段")
    private Boolean attr6;

    @ApiModelProperty(value = "备用字段")
    private Boolean attr7;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedatetime() {
        return createdatetime;
    }

    public void setCreatedatetime(Date createdatetime) {
        this.createdatetime = createdatetime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getLastupdatetime() {
        return lastupdatetime;
    }

    public void setLastupdatetime(Date lastupdatetime) {
        this.lastupdatetime = lastupdatetime;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getCorpids() {
        return corpids;
    }

    public void setCorpids(String corpids) {
        this.corpids = corpids;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getDepartment() {
        return department;
    }

    public void setDepartment(Integer department) {
        this.department = department;
    }

    public Integer getMainDepartment() {
        return mainDepartment;
    }

    public void setMainDepartment(Integer mainDepartment) {
        this.mainDepartment = mainDepartment;
    }

    public String getCorpname() {
        return corpname;
    }

    public void setCorpname(String corpname) {
        this.corpname = corpname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAttr1() {
        return attr1;
    }

    public void setAttr1(String attr1) {
        this.attr1 = attr1;
    }

    public String getAttr2() {
        return attr2;
    }

    public void setAttr2(String attr2) {
        this.attr2 = attr2;
    }

    public String getAttr3() {
        return attr3;
    }

    public void setAttr3(String attr3) {
        this.attr3 = attr3;
    }

    public Integer getAttr4() {
        return attr4;
    }

    public void setAttr4(Integer attr4) {
        this.attr4 = attr4;
    }

    public Integer getAttr5() {
        return attr5;
    }

    public void setAttr5(Integer attr5) {
        this.attr5 = attr5;
    }

    public Boolean getAttr6() {
        return attr6;
    }

    public void setAttr6(Boolean attr6) {
        this.attr6 = attr6;
    }

    public Boolean getAttr7() {
        return attr7;
    }

    public void setAttr7(Boolean attr7) {
        this.attr7 = attr7;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", loginId=").append(loginId);
        sb.append(", password=").append(password);
        sb.append(", createdatetime=").append(createdatetime);
        sb.append(", phone=").append(phone);
        sb.append(", lastupdatetime=").append(lastupdatetime);
        sb.append(", status=").append(status);
        sb.append(", roleId=").append(roleId);
        sb.append(", corpids=").append(corpids);
        sb.append(", img=").append(img);
        sb.append(", department=").append(department);
        sb.append(", mainDepartment=").append(mainDepartment);
        sb.append(", corpname=").append(corpname);
        sb.append(", email=").append(email);
        sb.append(", attr1=").append(attr1);
        sb.append(", attr2=").append(attr2);
        sb.append(", attr3=").append(attr3);
        sb.append(", attr4=").append(attr4);
        sb.append(", attr5=").append(attr5);
        sb.append(", attr6=").append(attr6);
        sb.append(", attr7=").append(attr7);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}