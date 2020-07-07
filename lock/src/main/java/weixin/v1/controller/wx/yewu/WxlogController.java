package weixin.v1.controller.wx.yewu;

import base.api.CommonResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import weixin.mapper.SysLogMapper;
import weixin.model.SysLog;
import weixin.model.SysLogExample;
import weixin.vo.page.PageFilter;

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

    /**
     * 统计当天开锁次数
     * @param corpid
     * @param userid
     * @param methodtype
     * @param date
     * @return
     */
    @RequestMapping("/openNumber")
    public CommonResult openNumber(@RequestParam String corpid, @RequestParam String userid, @RequestParam String methodtype, @RequestParam String date){
        try{
            int count=logMapper.getLogsByuseridCount(corpid,userid,methodtype,date);
            return CommonResult.success(count);
        }catch (Exception e){
            return CommonResult.failed(e.getCause(),e.getMessage());
        }
    }

    /**
     * 分页获取日志
     * @param corpid
     * @param userid
     * @param methodtype
     * @param ph
     * @return
     */
    @RequestMapping("/fenGetList")
    public CommonResult fenGetList(@RequestParam String corpid, @RequestParam String userid, @RequestParam String methodtype, PageFilter ph){
        List<SysLog> pageInfo =logMapper.getPageLogsByuserid(corpid,userid,(ph.getPage()-1)*ph.getRows(),ph.getRows());
        if(!pageInfo.isEmpty()){
            return CommonResult.success(pageInfo,pageInfo.size()+"");
        }else {
            return CommonResult.failed();
        }
    }

    /**
     * 获取日志总数
     * @param corpid
     * @param userid
     * @return
     */
    @RequestMapping("/getLogCount")
    public CommonResult getLogCount(@RequestParam String corpid, @RequestParam String userid){
        SysLogExample example = new SysLogExample();
        example.createCriteria().andLoginidEqualTo(userid).andCorpidEqualTo(corpid);
        int count =logMapper.selectCountByExample(example);
        if(count==0){
            return CommonResult.failed();
        }else {
            return CommonResult.success(count);
        }
    }
}
