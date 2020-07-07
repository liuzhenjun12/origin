package weixin.listener;

import base.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import weixin.service.BaseConfigService;
import weixin.vo.BaseConfig;

@Slf4j
@Component
public class MyApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        /**
         * 初始化时缓存基本凭证配置
         */
        BaseConfig base=new BaseConfig();
        base.setSToken("nkY9OLaKm2CWC5xV");
        base.setSCorpID("wwabb136cec3204033");
        base.setSuiteID("wwb526f40753f8d692");
        base.setSuiteSecret("G37Up3BBRCjAtENbU35JTxcSnUBeByy21gI9K-gM6PU");
        base.setSEncodingAESKey("IWuYHINjV9RRXQSjmF2TusYo0AGPm4SwCrgLPgi5AQx");
        base.setSecret("vp-GtVh7qW5PycZwug0V8RpBb-A8hFq03znwyvzEGdY");
        log.info("初始化时缓存基本凭证配置==>{}",base.toString());
        BaseConfigService baseConfigService = SpringUtil.getBean("baseConfigService");
        baseConfigService.set_base_config(base);
    }
}
