package weixin.vo.userxiangxi;

import lombok.Data;

import java.io.Serializable;

@Data
public class Attrs implements Serializable {
    private static final long serialVersionUID = -4528943695777516223L;
    private Integer type;
    private String name;
    private Text text;
    private Web web;
}
