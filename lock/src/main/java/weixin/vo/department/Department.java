package weixin.vo.department;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
@Data
@ToString
public class Department implements Serializable {
    private static final long serialVersionUID = -5615249620964243912L;
    private Integer id;
    private String name;
    private String name_en;
    private Integer parentid;
    private Integer order;
}
