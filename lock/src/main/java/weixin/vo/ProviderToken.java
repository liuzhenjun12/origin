package weixin.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProviderToken extends SupperBean implements Serializable {

    private static final long serialVersionUID = -6856442251475934050L;
    private String provider_access_token;//服务商凭证
}
