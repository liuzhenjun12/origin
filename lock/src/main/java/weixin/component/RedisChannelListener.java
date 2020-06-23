package weixin.component;

import base.api.CommonResult;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import weixin.service.BaseConfigService;
import weixin.service.CorpService;
import weixin.service.LockService;
import weixin.service.UserService;

import java.nio.charset.StandardCharsets;

@Slf4j
public class RedisChannelListener implements MessageListener {
    @Autowired
    UserService userService;
    @Autowired
    CorpService corpService;
    @Autowired
    LockService lockService;
    @Autowired
    BaseConfigService baseConfigService;

    @SneakyThrows
    @Override
    public void onMessage(Message message, @Nullable byte[] pattern) {
        log.info("收到消息");
        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("授权公司企业微信id==>{}",msg);
        if (!StringUtils.isEmpty(msg)){
            msg=msg.replaceAll("\"","");
            String[] arr=msg.split(",");
            log.info(arr[1]+","+arr[0]);
            if("add".equals(arr[1])){
                CommonResult c1= corpService.addCorp(arr[0]);
                log.info("添加企业信息==>{}",c1.getMessage());
                CommonResult c3=userService.addUser(arr[0]);
                log.info("添加用户信息==>{}",c3.getMessage());
                CommonResult c4=userService.addAdmin(arr[0]);
                log.info("添加管理员信息==>{}",c4.getMessage());
            }else if("del".equals(arr[1])){
                CommonResult c1=baseConfigService.deleteAll(arr[0]);
                log.info("删除企业信息==>{}",c1.getMessage());
            }else if("change".equals(arr[1])){
                CommonResult c1=userService.updateUser(arr[0]);
                log.info("修改用户信息==>{}",c1.getMessage());
                CommonResult c2=userService.addAdmin(arr[0]);
                log.info("添加管理员信息==>{}",c2.getMessage());
            }else if("init".equals(arr[1])){
                CommonResult c1=lockService.init(arr[0]);
                log.info("初始化设备==>{}",c1.getMessage());
            }else if("unlock".equals(arr[1])){
                CommonResult c1=lockService.unlock(arr[2]);
                log.info("卸载设备==>{}",c1.getMessage());
            }else if("uplock".equals(arr[1])){
                CommonResult c1=lockService.uplock(arr[0],arr[2]);
                log.info("修改设备名称==>{}",c1.getMessage());
            }else if("connect".equals(arr[1])){
                CommonResult c1=lockService.connect(arr[2]);
            }else if("disconnect".equals(arr[1])){

            }
        }
    }
}
