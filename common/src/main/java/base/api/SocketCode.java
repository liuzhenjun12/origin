package base.api;

public enum SocketCode {
    OPEN_CONNENT("1000","请求建立连接"),
    OPEN_CONNENT_SUCCESS("1001","连接成功"),
    OPEN_CONNENT_FILA("1002","连接断开"),
    OPEN_CONNENT_TIMEOUT("1003","连接超时"),
    XIN_TIAO("1004","发送心跳包"),
    OPEN_LOCK("1005","请求开锁"),
    OPEN_LOCK_SUCCCESS("1006","开锁成功"),
    OPEN_LOCK_FILA("1007","开锁失败"),
    UN_LOCK_SUCCCESS("1008","闭锁成功"),
    UN_LOCK_FILA("1009","闭锁失败"),
    UPDATE_PSK("1010","请求修改秘钥"),
    UPDATE_PSK_SUCCESS("1011","修改秘钥成功"),
    UPDATE_PSK_FILA("1012","修改秘钥失败"),
    DATA_FORMAT_ERROR("1013","报文格式错误或者参数不全");

    private String code;
    private String message;

    private SocketCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
