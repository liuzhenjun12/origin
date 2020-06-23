package weixin.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

public class SysCorp implements Serializable {
    @Id
    private Integer id;

    @ApiModelProperty(value = "企业id")
    private String corpid;

    @ApiModelProperty(value = "企业简称")
    private String corpName;

    @ApiModelProperty(value = "企业logo")
    private String corpSquareLogoUrl;

    @ApiModelProperty(value = "企业全称")
    private String corpFullName;

    @ApiModelProperty(value = "企业二维码")
    private String corpWxqrcode;

    @ApiModelProperty(value = "企业规模")
    private String corpScale;

    @ApiModelProperty(value = "企业行业")
    private String corpIndustry;

    @ApiModelProperty(value = "企业地址")
    private String location;

    @ApiModelProperty(value = "第三方授权id")
    private Integer agentid;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建日期")
    private Date createDate;

    @ApiModelProperty(value = "创建人绑定负责人")
    private String createBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "更新日期")
    private Date updateDate;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCorpid() {
        return corpid;
    }

    public void setCorpid(String corpid) {
        this.corpid = corpid;
    }

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public String getCorpSquareLogoUrl() {
        return corpSquareLogoUrl;
    }

    public void setCorpSquareLogoUrl(String corpSquareLogoUrl) {
        this.corpSquareLogoUrl = corpSquareLogoUrl;
    }

    public String getCorpFullName() {
        return corpFullName;
    }

    public void setCorpFullName(String corpFullName) {
        this.corpFullName = corpFullName;
    }

    public String getCorpWxqrcode() {
        return corpWxqrcode;
    }

    public void setCorpWxqrcode(String corpWxqrcode) {
        this.corpWxqrcode = corpWxqrcode;
    }

    public String getCorpScale() {
        return corpScale;
    }

    public void setCorpScale(String corpScale) {
        this.corpScale = corpScale;
    }

    public String getCorpIndustry() {
        return corpIndustry;
    }

    public void setCorpIndustry(String corpIndustry) {
        this.corpIndustry = corpIndustry;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getAgentid() {
        return agentid;
    }

    public void setAgentid(Integer agentid) {
        this.agentid = agentid;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", corpid=").append(corpid);
        sb.append(", corpName=").append(corpName);
        sb.append(", corpSquareLogoUrl=").append(corpSquareLogoUrl);
        sb.append(", corpFullName=").append(corpFullName);
        sb.append(", corpWxqrcode=").append(corpWxqrcode);
        sb.append(", corpScale=").append(corpScale);
        sb.append(", corpIndustry=").append(corpIndustry);
        sb.append(", location=").append(location);
        sb.append(", agentid=").append(agentid);
        sb.append(", createDate=").append(createDate);
        sb.append(", createBy=").append(createBy);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}