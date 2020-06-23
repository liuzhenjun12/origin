package com.vo;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
@Data
@ToString
public class SocketInfo  implements Serializable {
    private static final long serialVersionUID = 7522812421024743023L;
    private String sockid;
    private String ip;
}
