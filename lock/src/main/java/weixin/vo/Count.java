package weixin.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class Count implements Serializable {
    private static final long serialVersionUID = -7841207792399758416L;
    /**
     * 服务企业数量
     */
    private Integer corpcount;
    /**
     *激活用户数量
     */
    private Integer usercount;
    /**
     *已初始化设备数量
     */
    private Integer yeslockcount;
    /**
     *未初始化设备数量
     */
    private Integer notlockcount;
    /**
     *已联网企业微信数量
     */
    private Integer connectwx;
    /**
     *已联网服务器后台数量
     */
    private Integer connectht;
}
