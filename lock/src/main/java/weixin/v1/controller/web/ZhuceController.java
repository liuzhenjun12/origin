package weixin.v1.controller.web;

import base.constant.RedisKeyPrefixConst;
import base.util.RedisOpsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import weixin.service.BaseConfigService;
import weixin.vo.BaseConfig;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
@RequestMapping("/web/v1")
public class ZhuceController {
    @Autowired
    BaseConfigService baseConfigService;
    @Autowired
    RedisOpsUtil redisOpsUtil;
    /**
     * 安装到企业微信页面
     * @return
     */
    @GetMapping("/auth")
    public String auth(HttpServletRequest request) {
        BaseConfig baseConfig=redisOpsUtil.get(RedisKeyPrefixConst.BASE_CONFIG,BaseConfig.class);
            try {
                String pre=baseConfigService.get_pre_auth_code();
                String backUrl="https://qy.weixin.qq.com/cgi-bin/loginpage?suite_id="
                        +baseConfig.getSuiteID()+"&pre_auth_code="+pre
                        +"&redirect_uri=https%3a%2f%2fsafe.jinchu.com.cn%2fwx%2fv1%2findex&state=";
                return "redirect:" + backUrl;
            }catch (Exception e){
                String backUrl="https://qy.weixin.qq.com/cgi-bin/loginpage?suite_id="
                        +baseConfig.getSuiteID()+"&pre_auth_code="+baseConfig.getSuiteSecret()
                        +"&redirect_uri=https%3a%2f%2fsafe.jinchu.com.cn%2fwx%2fv1%2findex&state=";
                return "redirect:" + backUrl;
            }

    }
}
