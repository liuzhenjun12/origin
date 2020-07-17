package weixin.v1.controller.wx.yewu;

import base.api.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import weixin.mapper.SysCorpMapper;
import weixin.model.SysCorp;
import weixin.model.SysCorpExample;

@RestController
@Slf4j
@RequestMapping("/wx/v1")
public class WxCorpController {
    @Autowired
    SysCorpMapper corpMapper;

    /**
     * 获取企业信息
     * @param cropid
     * @return
     */
    @RequestMapping("/getCorpinfo")
    public CommonResult getCorpinfo(String cropid){
        SysCorpExample corpExample=new SysCorpExample();
        corpExample.createCriteria().andCorpidEqualTo(cropid);
        SysCorp corp=corpMapper.selectOneByExample(corpExample);
        if(corp==null){
            return CommonResult.failed();
        }
        return CommonResult.success(corp);
    }
}
