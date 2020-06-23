package weixin.model;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import java.io.Serializable;

public class SysDepart implements Serializable {
    @Id
    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "部门名称")
    private String name;

    @ApiModelProperty(value = "部门英文名称")
    private String nameEn;

    @ApiModelProperty(value = "部门id")
    private Integer deparid;

    @ApiModelProperty(value = "部门上级id")
    private Integer parentid;

    @ApiModelProperty(value = "排序号")
    private Integer order;

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

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public Integer getDeparid() {
        return deparid;
    }

    public void setDeparid(Integer deparid) {
        this.deparid = deparid;
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", nameEn=").append(nameEn);
        sb.append(", deparid=").append(deparid);
        sb.append(", parentid=").append(parentid);
        sb.append(", order=").append(order);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}