package weixin.vo.device;

import lombok.Data;

import java.io.Serializable;

@Data
public class Device_list implements Serializable {
    private static final long serialVersionUID = -4211415752182972967L;
    private String device_sn;
    private String model_id;
}
