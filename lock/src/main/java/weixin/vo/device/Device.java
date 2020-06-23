package weixin.vo.device;

import lombok.Data;
import weixin.vo.SupperBean;

import java.io.Serializable;
import java.util.List;

@Data
public class Device extends SupperBean implements Serializable {
    private static final long serialVersionUID = -9053850438885540865L;
    private DeviceInfo device_info;
}
