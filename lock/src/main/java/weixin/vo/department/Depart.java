package weixin.vo.department;

import lombok.Data;
import weixin.vo.SupperBean;

import java.io.Serializable;
import java.util.List;
@Data
public class Depart extends SupperBean implements Serializable {
    private static final long serialVersionUID = -1254319001242928254L;
    private List<Department> department;
}
