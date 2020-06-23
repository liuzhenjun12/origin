package weixin.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SupperBean implements Serializable {
    private static final long serialVersionUID = 2929359174863838401L;
    private Integer errcode;
    private String errmsg;
}
