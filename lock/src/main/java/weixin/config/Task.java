package weixin.config;

import base.constant.RedisKeyPrefixConst;
import base.util.RedisOpsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StringUtils;
import weixin.mapper.*;
import weixin.model.*;
import weixin.vo.Count;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Slf4j
@Configuration
@EnableScheduling
public class Task {
    @Autowired
    SysCorpMapper corpMapper;
    @Autowired
    SysLockMapper lockMapper;
    @Autowired
    SysUserMapper userMapper;
    @Autowired
    SysTodoMapper todoMapper;
    @Autowired
    SysLogMapper logMapper;
    @Autowired
    RedisOpsUtil redisOpsUtil;

    @Scheduled(cron = "0 */1 * * * ?")
    private void configureTasks() throws ParseException {
        //TODO 统计企业、用户、设备激活、联网数量情况
        getCount();
        //TODO 统计用户设备数量，开锁次数
        countUserLockAndLog();
        //TODO 删除过期任务
        deleteTodo();
    }

    /**
     * 删除过期任务
     */
    private void deleteTodo() {
    SysTodoExample todoExample=new SysTodoExample();
    todoExample.createCriteria().andTodoTypeEqualTo(2);
    List<SysTodo> todos=todoMapper.selectByExample(todoExample);
    if(!todos.isEmpty()){
        Long now=(new Date()).getTime();
        for(SysTodo t:todos){
            if(t.getStartDate()!=null&&t.getEndDate()!=null){
                log.info("now:{},end:{}",now,t.getEndDate().getTime());
                if(now>t.getEndDate().getTime()){
                    //TODO 从缓存中获取操作人头像
                    SysUser u1=redisOpsUtil.get(RedisKeyPrefixConst.USER_INFO+ t.getCorpId()+":"+t.getCreateby(),SysUser.class);
                    String img="";
                    if(u1!=null&&!StringUtils.isEmpty(u1.getImg())){
                        img=u1.getImg();
                    }else {
                        img=RedisKeyPrefixConst.USER_IMG;
                    }
                    SysLog sysLog=new SysLog(t.getCreateby(),t.getCorpId(),"DEL","deleteTodo","删除任务",true,t.getUserId()+"使用设备"+t.getDeviceSn()+"已过期，任务已删除",new Date(),RedisKeyPrefixConst.JINCHU_IMG,RedisKeyPrefixConst.JIN_CHU);
                    logMapper.insert(sysLog);
                    SysLog sysLog1=new SysLog(t.getUserId(),t.getCorpId(),"DEL","deleteTodo","删除任务",true,"你使用的设备"+t.getDeviceSn()+"权限已过期，任务已删除",new Date(),img,t.getCreateby());
                    logMapper.insert(sysLog1);
                    todoMapper.deleteByPrimaryKey(t.getId());
                }
            }
        }
    }
    }

    /**
     * 统计企业的用户有哪些设备
     */
    private void countUserLock() {
        SysLockExample lockExample=new SysLockExample();
        lockExample.createCriteria().andStatusEqualTo(true);
        List<SysLock> locks=lockMapper.selectByExample(lockExample);
        if(!locks.isEmpty()){
            for(SysLock lock:locks){
                SysUserExample userExample=new SysUserExample();
                userExample.createCriteria().andCorpidsEqualTo(lock.getCorpid());
            }
        }
    }

    /**
     * 统计用户设备数量，开锁次数
     */
    private void countUserLockAndLog() {
        List<SysUser> userList=userMapper.selectAll();
        if(!userList.isEmpty()){
            for(SysUser u:userList){
                //TODO 统计用户开锁次数
                SysLogExample logExample=new SysLogExample();
                logExample.createCriteria().andCorpidEqualTo(u.getCorpids()).andLoginidEqualTo(u.getLoginId()).andMethodtypeEqualTo("OPEN");
                int logcount=logMapper.selectCountByExample(logExample);
                //TODO 统计设备数量
                SysTodoExample todoExample=new SysTodoExample();
                todoExample.createCriteria().andCorpIdEqualTo(u.getCorpids()).andUserIdEqualTo(u.getLoginId()).andStatusEqualTo(true);
                int lockcount=todoMapper.selectCountByExample(todoExample);

                if(redisOpsUtil.hasKey(RedisKeyPrefixConst.USER_INFO+u.getCorpids()+":"+u.getLoginId())){
                    SysUser user=redisOpsUtil.get(RedisKeyPrefixConst.USER_INFO+u.getCorpids()+":"+u.getLoginId(),SysUser.class);
                    user.setAttr4(lockcount);//TODO 设备数量
                    user.setAttr5(logcount);//TODO 开锁次数
                    redisOpsUtil.set(RedisKeyPrefixConst.USER_INFO+u.getCorpids()+":"+u.getLoginId(),user);
                }else {
                    u.setAttr4(lockcount);
                    u.setAttr5(logcount);
                    redisOpsUtil.set(RedisKeyPrefixConst.USER_INFO+u.getCorpids()+":"+u.getLoginId(),u);
                }
            }
        }
        userList.clear();
    }

    /**
     * 统计企业、用户、设备激活、联网数量情况
     */
    private void getCount() {
        Count count=new Count();
        //TODO 统计企业数量
        count.setCorpcount(corpMapper.selectAll().size());
        //TODO 统计用户数量
        count.setUsercount(userMapper.selectAll().size());
        //TODO 统计已初始化设备数量
        SysLockExample lockExample=new SysLockExample();
        lockExample.createCriteria().andStatusEqualTo(true);
        count.setYeslockcount(lockMapper.selectCountByExample(lockExample));
        //TODO 统计未初始化设备数量
        SysLockExample lockExample1=new SysLockExample();
        lockExample1.createCriteria().andStatusEqualTo(false);
        count.setNotlockcount(lockMapper.selectCountByExample(lockExample1));
        //TODO 统计微信联网设备数量
        SysLockExample lockExample2=new SysLockExample();
        lockExample2.createCriteria().andConnectWxEqualTo(true);
        count.setConnectwx(lockMapper.selectCountByExample(lockExample2));
        //TODO 统计服务器后台联网设备数量
        SysLockExample lockExample3=new SysLockExample();
        lockExample3.createCriteria().andConnectHtEqualTo(true);
        count.setConnectht(lockMapper.selectCountByExample(lockExample3));
        //TODO 缓存到redis
        redisOpsUtil.set(RedisKeyPrefixConst.INFO_COUNT,count);
        count=null;
    }


}
