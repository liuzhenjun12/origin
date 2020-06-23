package base.constant;

public interface RedisKeyPrefixConst {

    /**
     * 企业微信基本配置
     */
    String BASE_CONFIG = "wx:base:config";

    /**
     * 缓存每10分钟，微信服务器发送的凭证
     */
    String SUITE_TICKET = "wx:base:suite_ticket";

    /**
     * 缓存第三方应用凭证
     */
    String SUITE_ACCESS_TOKEN = "wx:base:suite_access_token";

    /**
     * 缓存预授权码
     */
    String PRE_AUTH_CODE = "wx:base:pre_auth_code";

    /**
     * 缓存永久授权码
     */
    String PERMANENT_CODE =  "wx:base:crop:permanent:";

    /**
     * 缓存消息编号
     */
    String MSG_SN = "cleanNoStockCache";

    /**
     * 企业的access_token
     */
    String ACCESS_TOKEN = "wx:base:crop:accessToken:";

    /**
     * 企业信息
     */
    String CORP_INFO = "wx:base:crop:info:";

    /**
     * 用户信息
     */
    String USER_INFO = "wx:base:crop:user:info:";

    /**
     * 当库存减到0时,需要做一次库存同步,存在预减
     */
    String ADMIN_INFO = "wx:base:crop:admin:info:";

    /**
     * provider_access_token
     * redis布隆过滤器key
     */
    String PROVIDER_ACCESS_TOKEN = "wx:base:provider_access_token";

    /**
     * 会话缓存
     */
    public static final String SESSION_INFO = "sessionInfo";
    /**
     * 初始化设备参数
     */
    public static final String INIT_LOCK = "wx:base:initlock:";
    /**
     * 统计企业、用户、设备激活、联网数量情况
     */
    public static final String INFO_COUNT = "wx:base:count";

}
