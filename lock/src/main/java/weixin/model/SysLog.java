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

    private static final long serialVersionUID = 1L;

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
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}