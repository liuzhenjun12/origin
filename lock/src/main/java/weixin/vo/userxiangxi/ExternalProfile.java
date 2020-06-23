package weixin.vo.userxiangxi;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ExternalProfile implements Serializable {
    private static final long serialVersionUID = -1265958362834211492L;
    private String external_corp_name;
    private List<ExternalAttr>external_attr;
}
