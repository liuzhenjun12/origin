package weixin.v1.controller.wx.yewu;

import base.api.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import weixin.mapper.SysLogMapper;
import weixin.model.SysLog;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/wx/v1")
public class WxlogController {
    @Autowired
    SysLogMapper logMapper;
    /**
     * 通过企业id、用户id、日期获取日志列表
     * @param corpid
     * @param userid
     * @param date
     * @return
     */
    @RequestMapping("/getwxLogsByuserid")
    public CommonResult getwxLogsByuserid(@RequestParam String corpid, @RequestParam String userid, @RequestParam String methodtype, @RequestParam String date){
        log.info(corpid+","+userid+","+methodtype+","+date);
        if(StringUtils.isEmpty(corpid)||StringUtils.isEmpty(userid)||
                StringUtils.isEmpty(methodtype)||StringUtils.isEmpty(date)){
            return CommonResult.failed("参数错误");
        }
        List<SysLog> logList=logMapper.getLogsByuserid(corpid,userid,methodtype,date);
        return logList.isEmpty()?CommonResult.failed("没有匹配数据"):CommonResult.success(logList);
    }

    /**
     * 通过日志id删除
     * @param id
     * @return
     */
    @RequestMapping("/wxdelete")
    public CommonResult wxdelete(Integer id){
        try{
            logMapper.deleteByPrimaryKey(id);
            return CommonResult.success(null,"删除成功");
        }catch (Exception e){
            return CommonResult.failed(e.getCause(),e.getMessage());
        }
    }
}
