package weixin.vo.admin;

import lombok.Data;
import weixin.vo.SupperBean;

import java.io.Serializable;
import java.util.List;
@Data
public class Admin extends SupperBean implements Serializable {
    private static final long serialVersionUID = 2367850147726931256L;
    private List<Ad> admin;
}
