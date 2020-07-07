package weixin.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

public class SysLog implements Serializable {
    @Id
    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "登录id")
    private String loginid;

    @ApiModelProperty(value = "企业id")
    private String corpid;

    @ApiModelProperty(value = "方法类型")
    private String methodtype;

    @ApiModelProperty(value = "方法名称")
    private String methodname;

    @ApiModelProperty(value = "方法简称")
    private String methodjc;

    @ApiModelProperty(value = "是否成功")
    private Boolean sccess;

    @ApiModelProperty(value = "返回结果")
    private String result;

    @ApiModelProperty(value = "调用者ip")
    private String ip;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建日期")
    private Date createdate;

    @ApiModelProperty(value = "来自头像")
    private String img;

    @ApiModelProperty(value = "来自某人")
    private String attr1;

    @ApiModelProperty(value = "备用2")
    private String attr2;

    @ApiModelProperty(value = "备用3")
    private Integer attr3;

    @ApiModelProperty(value = "备用4")
    private Integer attr4;

    @ApiModelProperty(value = "备用5")
    private Boolean attr5;

    @ApiModelProperty(value = "备用6")
    private Boolean attr6;

    private static final long serialVersionUID = 1L;

    public SysLog() {
    }

    public SysLog(String loginid, String corpid, String methodtype, String methodname, String methodjc, Boolean sccess, String result,  Date createdate, String img, String attr1) {
        this.loginid = loginid;
        this.corpid = corpid;
        this.methodtype = methodtype;
        this.methodname = methodname;
        this.methodjc = methodjc;
        this.sccess = sccess;
        this.result = result;
        this.createdate = createdate;
        this.img = img;
        this.attr1 = attr1;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginid() {
        return loginid;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }

    public String getCorpid() {
        return corpid;
    }

    public void setCorpid(String corpid) {
        this.corpid = corpid;
    }

    public String getMethodtype() {
        return methodtype;
    }

    public void setMethodtype(String methodtype) {
        this.methodtype = methodtype;
    }

    public String getMethodname() {
        return methodname;
    }

    public void setMethodname(String methodname) {
        this.methodname = methodname;
    }

    public String getMethodjc() {
        return methodjc;
    }

    public void setMethodjc(String methodjc) {
        this.methodjc = methodjc;
    }

    public Boolean getSccess() {
        return sccess;
    }

    public void setSccess(Boolean sccess) {
        this.sccess = sccess;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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

    public Integer getAttr3() {
        return attr3;
    }

    public void setAttr3(Integer attr3) {
        this.attr3 = attr3;
    }

    public Integer getAttr4() {
        return attr4;
    }

    public void setAttr4(Integer attr4) {
        this.attr4 = attr4;
    }

    public Boolean getAttr5() {
        return attr5;
    }

    public void setAttr5(Boolean attr5) {
        this.attr5 = attr5;
    }

    public Boolean getAttr6() {
        return attr6;
    }

    public void setAttr6(Boolean attr6) {
        this.attr6 = attr6;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", loginid=").append(loginid);
        sb.append(", corpid=").append(corpid);
        sb.append(", methodtype=").append(methodtype);
        sb.append(", methodname=").append(methodname);
        sb.append(", methodjc=").append(methodjc);
        sb.append(", sccess=").append(sccess);
        sb.append(", result=").append(result);
        sb.append(", ip=").append(ip);
        sb.append(", createdate=").append(createdate);
        sb.append(", img=").append(img);
        sb.append(", attr1=").append(attr1);
        sb.append(", attr2=").append(attr2);
        sb.append(", attr3=").append(attr3);
        sb.append(", attr4=").append(attr4);
        sb.append(", attr5=").append(attr5);
        sb.append(", attr6=").append(attr6);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
