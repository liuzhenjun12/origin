package weixin.v1.controller.wx.yewu;

import base.api.CommonResult;
import base.constant.RedisKeyPrefixConst;
import base.util.RedisOpsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import weixin.mapper.SysLockMapper;
import weixin.mapper.SysLogMapper;
import weixin.mapper.SysTodoMapper;
import weixin.model.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/wx/v1")
public class WxTodoController {
    @Autowired
    SysLockMapper lockMapper;
    @Autowired
    SysTodoMapper todoMapper;
    @Autowired
    SysLogMapper logMapper;
    @Autowired
    RedisOpsUtil redisOpsUtil;

    /**
     * 通过id获取任务
     * @param id
     * @return
     */
    @RequestMapping("/getTodoByid")
    public CommonResult getTodoByid(Integer id){
        SysTodo todo=todoMapper.selectByPrimaryKey(id);
        return todo==null?CommonResult.failed():CommonResult.success(todo);
    }
    /**
     * 通过设备id用户id获取任务信息
     * @param cropid
     * @param lockid
     * @param userid
     * @return
     */
    @RequestMapping("/getTodoBylockidAnduserid")
    public CommonResult getTodoBylockidAnduserid(String cropid,Integer lockid,String userid){
        if (StringUtils.isEmpty(cropid)||StringUtils.isEmpty(lockid)||StringUtils.isEmpty(userid)){
            return CommonResult.failed("参数不能为空");
        }
        SysLockExample lockExample=new SysLockExample();
        SysLock sysLock=lockMapper.selectByPrimaryKey(lockid);
        if(sysLock==null){
            return CommonResult.failed("设备id错误");
        }
        SysTodoExample todoExample=new SysTodoExample();
        todoExample.createCriteria().andCorpIdEqualTo(cropid).andUserIdEqualTo(userid).andDeviceSnEqualTo(sysLock.getDeviceSn());
        SysTodo todo=todoMapper.selectOneByExample(todoExample);
        return todo==null?CommonResult.failed():CommonResult.success(todo);
    }

    /**
     * 删除任务
     * @param todoid
     * @param corpid
     * @param userid
     * @return
     */
    @RequestMapping("/deleteTodo")
    public CommonResult deleteTodo(Integer lockid,Integer todoid, String corpid,
                                   String userid, String adminid, String sn){
        if(StringUtils.isEmpty(todoid)||StringUtils.isEmpty(userid)||StringUtils.isEmpty(corpid)
        ||StringUtils.isEmpty(adminid)||StringUtils.isEmpty(sn)){
            return CommonResult.failed("参数不全");
        }
        try {
            SysTodo todo = todoMapper.selectByPrimaryKey(todoid);
            if (todo == null) {
                return CommonResult.failed("任务不存在");
            }
            if (userid.equals(todo.getCreateby())) {
                return CommonResult.failed("设备负责人不能删除");
            }
            //TODO 删除任务
            todoMapper.deleteByPrimaryKey(todoid);
            SysLock lock=lockMapper.selectByPrimaryKey(lockid);
            lock.setAttr4(lock.getAttr4()-1);
            //TODO 修改设备使用人数
            lockMapper.updateByPrimaryKey(lock);
            //TODO 从缓存中获取操作人头像
            SysUser u1=redisOpsUtil.get(RedisKeyPrefixConst.USER_INFO+corpid+":"+adminid,SysUser.class);
            String img="";
            if(u1!=null&&!StringUtils.isEmpty(u1.getImg())){
                img=u1.getImg();
            }else {
                img=RedisKeyPrefixConst.USER_IMG;
            }
            SysLog sysLog=new SysLog(adminid,corpid,"DEL","deleteTodo","删除任务",true,"成功删除"+userid+"使用设备"+sn,new Date(),RedisKeyPrefixConst.JINCHU_IMG,RedisKeyPrefixConst.JIN_CHU);
            //TODO 操作人记录日志
            logMapper.insert(sysLog);
            SysLog sysLog1=new SysLog(userid,corpid,"DEL","deleteTodo","删除任务",true,adminid+"删除了你在设备"+sn+"的使用",new Date(),img,adminid);
            //TODO 被操作人记录日志
            logMapper.insert(sysLog1);
            return CommonResult.success(null,"成功删除");
        }catch (Exception e){
            return CommonResult.failed(e.getCause(),e.getMessage());
        }
    }

    /**
     * 添加任务
     * @return
     */
    @RequestMapping("/addTodo")
    public CommonResult addTodo(String createid,Integer lockid,String start,String end,SysTodo todo) throws ParseException {
        if(todo==null||StringUtils.isEmpty(lockid)){
            return CommonResult.failed("参数不全");
        }
        if(StringUtils.isEmpty(todo.getTodoType())||StringUtils.isEmpty(todo.getOpenType())){
            return CommonResult.failed("参数不全");
        }
        try {
            SysLock lock = lockMapper.selectByPrimaryKey(lockid);
            if (lock == null) {
                return CommonResult.failed("设备不存在");
            }
            SysTodoExample todoExample=new SysTodoExample();
            todoExample.createCriteria().andDeviceSnEqualTo(todo.getDeviceSn()).andUserIdEqualTo(todo.getUserId()).andCorpIdEqualTo(todo.getCorpId());
            List<SysTodo> todoList=todoMapper.selectByExample(todoExample);
            if(!todoList.isEmpty()){
                return CommonResult.failed("任务已存在");
            }
            if ("2".equals(todo.getTodoType().toString())) {
                if (StringUtils.isEmpty(start) || StringUtils.isEmpty(end)) {
                    return CommonResult.failed("任务时间段不能为空");
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if(((sdf.parse(start).getTime())+300000) >=sdf.parse(end).getTime()){
                    return CommonResult.failed("结束时间至少大于开始时间5分钟");
                }
                todo.setStartDate(sdf.parse(start));
                todo.setEndDate(sdf.parse(end));
            }
            todo.setCreateDate(new Date());
            todo.setStatus(true);
            todo.setIcon(lock.getIcon());
            //TODO 添加任务
            todoMapper.insert(todo);
            lock.setAttr4(lock.getAttr4()+1);
            //TODO 修改设备使用人数
            lockMapper.updateByPrimaryKey(lock);
            //TODO 从缓存中获取操作人头像
            SysUser u1=redisOpsUtil.get(RedisKeyPrefixConst.USER_INFO+ todo.getCorpId()+":"+todo.getCreateby(),SysUser.class);
            String img="";
            if(u1!=null&&!StringUtils.isEmpty(u1.getImg())){
                img=u1.getImg();
            }else {
                img=RedisKeyPrefixConst.USER_IMG;
            }
            SysLog sysLog = new SysLog(lock.getLoginid(), todo.getCorpId(), "ADD", "addTodo", "添加任务", true, "成功添加" + todo.getUserId() + "使用设备" + todo.getDeviceSn(), new Date(),RedisKeyPrefixConst.JINCHU_IMG,RedisKeyPrefixConst.JIN_CHU);
            //TODO 操作人记录日志
            logMapper.insert(sysLog);
            SysLog sysLog1 = new SysLog(todo.getUserId(), todo.getCorpId(), "ADD", "addTodo", "添加任务", true, lock.getLoginid() + "添加了你在设备" + todo.getDeviceSn() + "的使用", new Date(),img,todo.getCreateby());
            //TODO 被操作人记录日志
            logMapper.insert(sysLog1);
            return CommonResult.success(null,"添加成功");
        }catch (Exception e){
            return CommonResult.failed(e.getCause(),e.getMessage());
        }
    }

    /**
     * 修改任务
     * @param start
     * @param end
     * @param todo
     * @return
     * @throws ParseException
     */
    @RequestMapping("/updateTodo")
    public CommonResult updateTodo(String start,String end,SysTodo todo) throws ParseException {
        if(StringUtils.isEmpty(todo.getTodoType())||StringUtils.isEmpty(todo.getOpenType())||StringUtils.isEmpty(todo.getId())){
            return CommonResult.failed("参数不全");
        }
        try {
            SysTodo sysTodo = todoMapper.selectByPrimaryKey(todo.getId());
            if (sysTodo == null) {
                return CommonResult.failed("任务不存在");
            }
            sysTodo.setTodoType(todo.getTodoType());
            sysTodo.setOpenType(todo.getOpenType());
            sysTodo.setCreateby(todo.getCreateby());
            if ("2".equals(todo.getTodoType().toString())) {
                if (StringUtils.isEmpty(start) || StringUtils.isEmpty(end)) {
                    return CommonResult.failed("任务时间段不能为空");
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if(((sdf.parse(start).getTime())+300000) >=sdf.parse(end).getTime()){
                    return CommonResult.failed("结束时间至少大于开始时间5分钟");
                }
                sysTodo.setStartDate(sdf.parse(start));
                sysTodo.setEndDate(sdf.parse(end));
            } else {
                sysTodo.setStartDate(null);
                sysTodo.setEndDate(null);
            }
            //TODO 修改任务
            todoMapper.updateByPrimaryKey(sysTodo);
            //TODO 从缓存中获取操作人头像
            SysUser u1=redisOpsUtil.get(RedisKeyPrefixConst.USER_INFO+ todo.getCorpId()+":"+todo.getCreateby(),SysUser.class);
            String img="";
            if(u1!=null&&!StringUtils.isEmpty(u1.getImg())){
                img=u1.getImg();
            }else {
                img=RedisKeyPrefixConst.USER_IMG;
            }
            SysLog sysLog = new SysLog(sysTodo.getCreateby(), sysTodo.getCorpId(), "UPDATE", "updateTodo", "修改任务", true, "成功修改" + sysTodo.getUserId() + "使用设备" + sysTodo.getDeviceSn(), new Date(),RedisKeyPrefixConst.JINCHU_IMG,RedisKeyPrefixConst.JIN_CHU);
            logMapper.insert(sysLog);
            SysLog sysLog1 = new SysLog(sysTodo.getUserId(), sysTodo.getCorpId(), "UPDATE", "updateTodo", "修改任务", true, sysTodo.getCreateby() + "修改了你在设备" + sysTodo.getDeviceSn() + "的使用", new Date(),img,todo.getCreateby());
            logMapper.insert(sysLog1);
            return CommonResult.success(null,"修改成功");
        }catch (Exception e){
            return CommonResult.failed(e.getCause(),e.getMessage());
        }
    }
}
