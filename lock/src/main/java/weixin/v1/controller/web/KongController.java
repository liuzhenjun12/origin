package weixin.v1.controller.web;

import base.constant.RedisKeyPrefixConst;
import base.util.RedisOpsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import weixin.vo.Count;
@Controller
@Slf4j
@RequestMapping("/web/kong")
public class KongController {
    @Autowired
    RedisOpsUtil redisOpsUtil;
    /**
     * 控制中心页面
     * console_home：首页
     * lockXl：设备系列
     * corp:企业信息
     * jiankong:设备监控
     * lockNum:设备编号维护
     * lockType:设备类型
     * log:日志信息
     * notAvtion:未激活设备
     * yetAvtion:已激活设备
     * user:用户管理
     * @param url
     * @return
     */
    @RequestMapping("/{url}")
    public String toLockXl(@PathVariable String url,Model model){
        return "web/kong/"+url;
    }

    /**
     * 控制台首页，因为需要统计数据，所以单独设置url
     * @param model
     * @return
     */
    @RequestMapping("/console_home")
    public String home(Model model){
        Count count=redisOpsUtil.get(RedisKeyPrefixConst.INFO_COUNT,Count.class);
        model.addAttribute("count",count);
        return "web/kong/console_home";
    }
}
