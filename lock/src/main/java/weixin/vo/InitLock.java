package weixin.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class InitLock implements Serializable {
    private static final long serialVersionUID = -4124603955400228080L;
    private String AuthCorpId;
    private String DeviceSn;
    private String RemarkName;
    private String ModelId;
    private boolean status;
}
