package base.api;


/**
 * 枚举了一些常用API操作码
 * Created by macro on 2019/4/19.
 */
public enum ResultCode implements IErrorCode {
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    VALIDATE_FAILED(404, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    LIST(100, "当前连接列表"),
    ONOPEN(101, "连接建立"),
    ONCLOSE(102, "连接关闭"),
    ONMESSAGE(103, "收到客户端消息"),
    JSONFORMATERROR(104, "消息格式错误，请用json格式"),
    SOCKETERROR(105,"SOCKEET连接错误"),
    SENDCLINE(106,"发送数据到客户端"),
    XIN_TIAO(107,"发送心跳包"),
    OPEN_LOCK(108,"开锁请求"),
    OPEN_LOCK_SUCCCESS(109,"开锁成功"),
    UN_LOCK_SUCCCESS(110,"闭锁成功"),
    FORBIDDEN(403, "没有相关权限");
    private long code;
    private String message;

    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
