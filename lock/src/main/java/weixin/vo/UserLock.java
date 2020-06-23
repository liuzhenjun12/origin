package weixin.vo;

import lombok.Data;

import java.io.Serializable;
@Data
public class UserLock implements Serializable {
    private String userid;
    private Boolean has;
}
