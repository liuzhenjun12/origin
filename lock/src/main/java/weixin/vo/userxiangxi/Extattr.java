package weixin.vo.userxiangxi;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Extattr implements Serializable {
    private static final long serialVersionUID = -1915801315308375285L;
    private List<Attrs> attrs;
}
