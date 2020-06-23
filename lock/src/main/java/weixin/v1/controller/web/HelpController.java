package weixin.v1.controller.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@RequestMapping("/web/v1")
public class HelpController {

    /**
     * 去帮助中心页面
     *pang：首页
     * mai：如何购买
     * use：如何使用
     * new：新人必读
     * @return
     */
    @RequestMapping(value = "/{b}", method = {RequestMethod.GET} )
    public String toPang(@PathVariable String b){
        return "web/help/"+b;
    }
}
