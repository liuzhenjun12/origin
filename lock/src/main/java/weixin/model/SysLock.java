package weixin.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

public class SysLock implements Serializable {
    @Id
    @ApiModelProperty(value = "设备id")
    private Integer id;

    @ApiModelProperty(value = "设备自定义名称")
    private String name;

    @ApiModelProperty(value = "设备型号id")
    private String modelId;

    @ApiModelProperty(value = "设备sn")
    private String deviceSn;

    @ApiModelProperty(value = "设备的secret_no")
    private String secretNo;

    @ApiModelProperty(value = "设备的deviceid")
    private String deviceId;

    @ApiModelProperty(value = "设备蓝牙地址")
    private String mac;

    @ApiModelProperty(value = "设备的静态二维码")
    private String qrCode;

    @ApiModelProperty(value = "状态0，正常1，禁用")
    private Boolean status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createdatetime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "修改时间")
    private Date lastupdatetime;

    @ApiModelProperty(value = "绑定用户id")
    private String loginid;

    @ApiModelProperty(value = "绑定用户名称")
    private String username;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "电池电量")
    private String battery;

    @ApiModelProperty(value = "根秘钥")
    private String rootPwd;

    @ApiModelProperty(value = "绑定企业id")
    private String corpid;

    @ApiModelProperty(value = "绑定企业名称")
    private String corpname;

    @ApiModelProperty(value = "设备网络是否连接企业微信")
    private Boolean connectWx;

    @ApiModelProperty(value = "设备网络是否连接后台服务器")
    private Boolean connectHt;

    @ApiModelProperty(value = "备用字段")
    private String attr1;

    @ApiModelProperty(value = "备用字段")
    private String attr2;

    @ApiModelProperty(value = "备用字段")
    private String attr3;

    @ApiModelProperty(value = "绑定使用人数")
    private Integer attr4;

    @ApiModelProperty(value = "备用字段")
    private Integer attr5;

    @ApiModelProperty(value = "操作模式，true:wifi,false:mac")
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

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getDeviceSn() {
        return deviceSn;
    }

    public void setDeviceSn(String deviceSn) {
        this.deviceSn = deviceSn;
    }

    public String getSecretNo() {
        return secretNo;
    }

    public void setSecretNo(String secretNo) {
        this.secretNo = secretNo;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Date getCreatedatetime() {
        return createdatetime;
    }

    public void setCreatedatetime(Date createdatetime) {
        this.createdatetime = createdatetime;
    }

    public Date getLastupdatetime() {
        return lastupdatetime;
    }

    public void setLastupdatetime(Date lastupdatetime) {
        this.lastupdatetime = lastupdatetime;
    }

    public String getLoginid() {
        return loginid;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getBattery() {
        return battery;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }

    public String getRootPwd() {
        return rootPwd;
    }

    public void setRootPwd(String rootPwd) {
        this.rootPwd = rootPwd;
    }

    public String getCorpid() {
        return corpid;
    }

    public void setCorpid(String corpid) {
        this.corpid = corpid;
    }

    public String getCorpname() {
        return corpname;
    }

    public void setCorpname(String corpname) {
        this.corpname = corpname;
    }

    public Boolean getConnectWx() {
        return connectWx;
    }

    public void setConnectWx(Boolean connectWx) {
        this.connectWx = connectWx;
    }

    public Boolean getConnectHt() {
        return connectHt;
    }

    public void setConnectHt(Boolean connectHt) {
        this.connectHt = connectHt;
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
        sb.append(", modelId=").append(modelId);
        sb.append(", deviceSn=").append(deviceSn);
        sb.append(", secretNo=").append(secretNo);
        sb.append(", deviceId=").append(deviceId);
        sb.append(", mac=").append(mac);
        sb.append(", qrCode=").append(qrCode);
        sb.append(", status=").append(status);
        sb.append(", createdatetime=").append(createdatetime);
        sb.append(", lastupdatetime=").append(lastupdatetime);
        sb.append(", loginid=").append(loginid);
        sb.append(", username=").append(username);
        sb.append(", icon=").append(icon);
        sb.append(", battery=").append(battery);
        sb.append(", rootPwd=").append(rootPwd);
        sb.append(", corpid=").append(corpid);
        sb.append(", corpname=").append(corpname);
        sb.append(", connectWx=").append(connectWx);
        sb.append(", connectHt=").append(connectHt);
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
