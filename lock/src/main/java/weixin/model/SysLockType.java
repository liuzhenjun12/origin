package weixin.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

public class SysLockType implements Serializable {
    @Id
    private Integer id;

    @ApiModelProperty(value = "设备型号")
    private String sbtype;

    @ApiModelProperty(value = "型号简称")
    private String sbjc;

    @ApiModelProperty(value = "设备系列id")
    private Integer sbxlid;

    @ApiModelProperty(value = "设备系列名称")
    private String sbxlname;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建日期")
    private Date createdate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "修改日期")
    private Date updatedate;

    @ApiModelProperty(value = "创建人id")
    private String createby;

    @ApiModelProperty(value = "修改人id")
    private String updateby;

    @ApiModelProperty(value = "图片样例")
    private String img;

    @ApiModelProperty(value = "wifi功能")
    private Boolean wifi;

    @ApiModelProperty(value = "备用字段1")
    private String attr1;

    @ApiModelProperty(value = "备用字段2")
    private Integer attr2;

    @ApiModelProperty(value = "备用字段3")
    private Boolean attr3;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSbtype() {
        return sbtype;
    }

    public void setSbtype(String sbtype) {
        this.sbtype = sbtype;
    }

    public String getSbjc() {
        return sbjc;
    }

    public void setSbjc(String sbjc) {
        this.sbjc = sbjc;
    }

    public Integer getSbxlid() {
        return sbxlid;
    }

    public void setSbxlid(Integer sbxlid) {
        this.sbxlid = sbxlid;
    }

    public String getSbxlname() {
        return sbxlname;
    }

    public void setSbxlname(String sbxlname) {
        this.sbxlname = sbxlname;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    public String getCreateby() {
        return createby;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }

    public String getUpdateby() {
        return updateby;
    }

    public void setUpdateby(String updateby) {
        this.updateby = updateby;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Boolean getWifi() {
        return wifi;
    }

    public void setWifi(Boolean wifi) {
        this.wifi = wifi;
    }

    public String getAttr1() {
        return attr1;
    }

    public void setAttr1(String attr1) {
        this.attr1 = attr1;
    }

    public Integer getAttr2() {
        return attr2;
    }

    public void setAttr2(Integer attr2) {
        this.attr2 = attr2;
    }

    public Boolean getAttr3() {
        return attr3;
    }

    public void setAttr3(Boolean attr3) {
        this.attr3 = attr3;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", sbtype=").append(sbtype);
        sb.append(", sbjc=").append(sbjc);
        sb.append(", sbxlid=").append(sbxlid);
        sb.append(", sbxlname=").append(sbxlname);
        sb.append(", createdate=").append(createdate);
        sb.append(", updatedate=").append(updatedate);
        sb.append(", createby=").append(createby);
        sb.append(", updateby=").append(updateby);
        sb.append(", img=").append(img);
        sb.append(", wifi=").append(wifi);
        sb.append(", attr1=").append(attr1);
        sb.append(", attr2=").append(attr2);
        sb.append(", attr3=").append(attr3);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
