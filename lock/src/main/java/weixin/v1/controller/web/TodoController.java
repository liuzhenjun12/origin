package weixin.v1.controller.web;

import base.aop.Log;
import base.api.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import weixin.mapper.SysTodoMapper;
import weixin.model.SysTodo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@Slf4j
@RequestMapping("/web/todo")
public class TodoController {
    @Autowired
    SysTodoMapper todoMapper;

    /**
     * 添加任务
     * @param todo
     * @param start
     * @param end
     * @return
     * @throws ParseException
     */
    @Log(desc = "添加任务",type = Log.LOG_TYPE.ADD)
    @RequestMapping("/add")
    public CommonResult add(SysTodo todo,String start,String end) throws ParseException {
        log.info("start:{},eng:{}",start,end);
        log.info("todo:{}",todo.toString());
        if(todo==null){
            return CommonResult.failed();
        }
        if(todo.getTodoType()==2){
            if(StringUtils.isEmpty(start)||StringUtils.isEmpty(end)){
                return CommonResult.failed("开始时间或者结束时间不能为空");
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date curr=new Date();
            Date sta=sdf.parse(start+":00");
            Date en =sdf.parse(end+":00");
            if(sta.getTime()<(curr.getTime()-60000)){
                return CommonResult.failed("开始时间不能小于当前时间");
            }
            if(sta.getTime()>=en.getTime()){
                return CommonResult.failed("开始时间不能大于或者等于结束时间");
            }
            if(en.getTime()<(sta.getTime()+300000)){
                return CommonResult.failed("开始时间与结束时间的差额不能低于5分钟");
            }
            todo.setStartDate(sta);
            todo.setEndDate(en);
        }
        todo.setStatus(true);
        todo.setCreateDate(new Date());
        todoMapper.insert(todo);
        return CommonResult.success("添加成功");
    }
}
