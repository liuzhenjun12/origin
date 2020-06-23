package weixin.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

public class SysTodo implements Serializable {
    @Id
    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "设备编号")
    private String deviceSn;

    @ApiModelProperty(value = "设备负责人")
    private String createby;

    @ApiModelProperty(value = "设备图片")
    private String icon;

    @ApiModelProperty(value = "企业id")
    private String corpId;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "任务类型，1、长期。2、短期")
    private Integer todoType;

    @ApiModelProperty(value = "开锁类型，1、远程开锁。2、蓝牙开锁")
    private Integer openType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "任务创建日期")
    private Date createDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "任务开始日期")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "任务结束日期")
    private Date endDate;

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

    @ApiModelProperty(value = "是否有效，1、有效。0、无效")
    private Boolean status;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeviceSn() {
        return deviceSn;
    }

    public void setDeviceSn(String deviceSn) {
        this.deviceSn = deviceSn;
    }

    public String getCreateby() {
        return createby;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getTodoType() {
        return todoType;
    }

    public void setTodoType(Integer todoType) {
        this.todoType = todoType;
    }

    public Integer getOpenType() {
        return openType;
    }

    public void setOpenType(Integer openType) {
        this.openType = openType;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", deviceSn=").append(deviceSn);
        sb.append(", createby=").append(createby);
        sb.append(", icon=").append(icon);
        sb.append(", corpId=").append(corpId);
        sb.append(", userId=").append(userId);
        sb.append(", todoType=").append(todoType);
        sb.append(", openType=").append(openType);
        sb.append(", createDate=").append(createDate);
        sb.append(", startDate=").append(startDate);
        sb.append(", endDate=").append(endDate);
        sb.append(", attr1=").append(attr1);
        sb.append(", attr2=").append(attr2);
        sb.append(", attr3=").append(attr3);
        sb.append(", attr4=").append(attr4);
        sb.append(", attr5=").append(attr5);
        sb.append(", attr6=").append(attr6);
        sb.append(", attr7=").append(attr7);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
