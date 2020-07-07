package weixin.v1.controller.web;

import base.aop.Log;
import base.api.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import weixin.mapper.SysLogMapper;
import weixin.model.SysLock;
import weixin.model.SysLockExample;
import weixin.model.SysLog;
import weixin.model.SysLogExample;
import weixin.vo.page.Grid;
import weixin.vo.page.PageFilter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/web/log")
public class LogController {
    @Autowired
    SysLogMapper logMapper;
    /**
     * 分页条件查询日志
     * @return
     */
    @RequestMapping("/list")
    public Grid getList(SysLog sysLog, String datetimepicker1,String datetimepicker2,PageFilter ph) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Grid grid=new Grid();
        SysLogExample example = new SysLogExample();
        SysLogExample.Criteria tiao = example.createCriteria();
        if(!StringUtils.isEmpty(sysLog.getCorpid())){
            tiao.andCorpidEqualTo(sysLog.getCorpid());
        }
        if(!StringUtils.isEmpty(sysLog.getMethodtype())){
            tiao.andMethodtypeEqualTo(sysLog.getMethodtype());
        }
        if(!StringUtils.isEmpty(sysLog.getLoginid())){
            tiao.andLoginidLike("%"+sysLog.getLoginid()+"%");
        }
        if(!StringUtils.isEmpty(datetimepicker1)&&!StringUtils.isEmpty(datetimepicker2)){
            tiao.andCreatedateBetween(sdf.parse(datetimepicker1),sdf.parse(datetimepicker2));
        }else if(!StringUtils.isEmpty(datetimepicker1)&&StringUtils.isEmpty(datetimepicker2)){
            tiao.andCreatedateGreaterThanOrEqualTo(sdf.parse(datetimepicker1));
        }else if(StringUtils.isEmpty(datetimepicker1)&&!StringUtils.isEmpty(datetimepicker2)){
            tiao.andCreatedateLessThanOrEqualTo(sdf.parse(datetimepicker2));
        }
        example.setOrderByClause("id DESC");
        RowBounds rowBounds = new RowBounds(ph.getPage()-1,ph.getRows());
        List<SysLog> ls = logMapper.selectByExampleAndRowBounds(example,rowBounds);
        grid.setRows(ls);
        int count=logMapper.selectCountByExample(example);
        grid.setTotal(count);
        return grid;
    }

    /**
     * 通过日志id删除
     * @param id
     * @return
     */
    @RequestMapping("/delete")
//    @Log(desc = "删除日志",type = Log.LOG_TYPE.DEL)
    public CommonResult delete(Integer id){
        try{
            logMapper.deleteByPrimaryKey(id);
            return CommonResult.success(null,"删除成功");
        }catch (Exception e){
            return CommonResult.failed(e.getCause(),e.getMessage());
        }
    }

    /**
     * 通过id获取日志
     * @param id
     * @return
     */
    @RequestMapping("/get")
    public CommonResult get(Integer id){
        try{
            SysLog log=logMapper.selectByPrimaryKey(id);
            return CommonResult.success(log);
        }catch (Exception e){
            return CommonResult.failed(e.getCause(),e.getMessage());
        }
    }

    /**
     * 通过企业id、用户id、日期获取日志列表
     * @param corpid
     * @param userid
     * @param date
     * @return
     */
    @RequestMapping("/getLogsByuserid")
    public CommonResult getLogsByuserid(String corpid,String userid,String methodtype,String date){
        if(StringUtils.isEmpty(corpid)||StringUtils.isEmpty(userid)||
        StringUtils.isEmpty(methodtype)||StringUtils.isEmpty(date)){
            return CommonResult.failed("参数错误");
        }
        List<SysLog>  logList=logMapper.getLogsByuserid(corpid,userid,methodtype,date);
        return logList.isEmpty()?CommonResult.failed("没有匹配数据"):CommonResult.success(logList);
    }

}
