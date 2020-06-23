package com.vo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LockBean {
    private long socketid;
    private String sn;
    private long userid; //存放当前请求人的id
    private String address;

    public LockBean(long socketid, String sn, long userid, String address) {
        this.socketid = socketid;
        this.sn = sn;
        this.userid = userid;
        this.address = address;
    }
}
