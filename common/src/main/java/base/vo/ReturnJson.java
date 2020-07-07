package base.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class ReturnJson implements Serializable {
    private static final long serialVersionUID = -5203034740029722859L;
    private String code;
    private String dete;
    private String sn;
    private String content;
    private String openCode;
    private String psk;

    public ReturnJson() {
    }

    public ReturnJson(String code, String dete, String sn, String content, String openCode, String psk) {
        this.code = code;
        this.dete = dete;
        this.sn = sn;
        this.content = content;
        this.openCode = openCode;
        this.psk = psk;
    }
}
