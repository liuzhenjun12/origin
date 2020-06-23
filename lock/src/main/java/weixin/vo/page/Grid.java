package weixin.vo.page;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class Grid implements Serializable {
    private static final long serialVersionUID = -7292980232206725256L;
    private Integer total = 0;
    private List rows = new ArrayList();
}
