package weixin.vo.admin;

import lombok.Data;

import java.io.Serializable;
@Data
public class Ad implements Serializable {
    private static final long serialVersionUID = 1638501319706722738L;
    private String userid;
    private Integer auth_type;
}
