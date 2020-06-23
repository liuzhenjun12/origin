package weixin.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class BaseConfig implements Serializable {
    private static final long serialVersionUID = -5875750172671473394L;
    private String sToken;//令牌
    private String sCorpID;//企业
    private String suiteID;//应用
    private String suiteSecret;//应用凭证
    private String sEncodingAESKey;//编码
    private String secret;//外部联系人钥匙
}
