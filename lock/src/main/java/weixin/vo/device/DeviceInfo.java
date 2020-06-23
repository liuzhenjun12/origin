package weixin.vo.device;

import lombok.Data;

import java.io.Serializable;

@Data
public class DeviceInfo implements Serializable {
    private static final long serialVersionUID = 448672460801776525L;
    private String model_id;
    private String device_sn;
    private String secret_no;
    private String device_id;
    private String qr_code;
    private Long create_time;

}
