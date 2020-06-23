package weixin.vo.userxiangxi;

import lombok.Data;

import java.io.Serializable;

@Data
public class ExternalAttr implements Serializable {
    private static final long serialVersionUID = -3760304732912727486L;
    private Integer type;
    private String name;
    private Text text;
    private Web web;
    private Miniprogram miniprogram;
}
