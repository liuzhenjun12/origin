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
        base.setSuiteID("ww120bb8bfb3277e5c");
        base.setSuiteSecret("Vqeo3zA_1-lSqukesxxLe93CtudwRNAwmAp80kEPEzs");
        base.setSEncodingAESKey("IWuYHINjV9RRXQSjmF2TusYo0AGPm4SwCrgLPgi5AQx");
        base.setSecret("5P_zRG4iBY0K_4l03yLccXMzhLzeESiVdjom6bYiW1I");
        log.info("初始化时缓存基本凭证配置==>{}",base.toString());
        BaseConfigService baseConfigService = SpringUtil.getBean("baseConfigService");
        baseConfigService.set_base_config(base);
    }
}
