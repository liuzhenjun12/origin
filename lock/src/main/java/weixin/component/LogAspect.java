package weixin.component;


import base.aop.Log;
import base.api.CommonResult;
import base.constant.RedisKeyPrefixConst;
import base.session.SessionInfo;
import base.util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import weixin.mapper.SysLogMapper;
import weixin.model.SysLog;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 为增删改添加监控
 */
@Slf4j
@Aspect
@Component
public class LogAspect {
   @Autowired
   SysLogMapper sysLogMapper;

    @Pointcut("@annotation(base.aop.Log)")
    private void pointcut() {
    }

    @AfterReturning(returning="rvt",pointcut = "pointcut()")
    public void insertLogSuccess(JoinPoint jp, Object rvt) {
        addLog(jp, getDesc(jp),rvt);
    }

    /**
     * 记录异常
     *
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(value = "pointcut()", throwing = "e")
    public void afterException(JoinPoint joinPoint, Exception e) {
        log.info("发现异常==>{}",e.getMessage());
        addLog(joinPoint, getDesc(joinPoint) ,e.getMessage());
    }

    private void addLog(JoinPoint jp, String desc, Object rvt) {
        log.info("开始记录日志==>{}",desc);
        Log.LOG_TYPE type = getType(jp);
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            SysLog sysLog=new SysLog();
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(RedisKeyPrefixConst.SESSION_INFO);
            if ((sessionInfo != null) && (sessionInfo.getLoginId()!= null)) {
                sysLog.setLoginid(sessionInfo.getLoginId());
                sysLog.setCorpid(sessionInfo.getCorpids());
            }else {
                sysLog.setLoginid("LiuZhenJun");
                sysLog.setCorpid("wwabb136cec3204033");
            }
            sysLog.setIp(IpUtil.getIp(request));
            sysLog.setMethodname(jp.getSignature().toShortString());
            sysLog.setMethodtype(type.toString());
            sysLog.setMethodjc(desc);
            sysLog.setImg(RedisKeyPrefixConst.JINCHU_IMG);
            sysLog.setAttr1(RedisKeyPrefixConst.JIN_CHU);
            sysLog.setCreatedate(new Date());
            if(rvt instanceof String) {
                sysLog.setResult(rvt.toString());
                sysLog.setSccess(false);
            }else {
                CommonResult base = (CommonResult) rvt;
                if(base!=null) {
                    sysLog.setResult(((CommonResult) rvt).getMessage());
                    if (base.getCode() == 200) {
                        sysLog.setSccess(true);
                    }else {
                        sysLog.setSccess(false);
                    }
                }else {
                    sysLog.setResult(rvt.toString());
                    sysLog.setSccess(false);
                }
            }
            sysLogMapper.insert(sysLog);
        }
    }

    private String getDesc(JoinPoint joinPoint) {
        MethodSignature methodName = (MethodSignature) joinPoint.getSignature();
        Method method = methodName.getMethod();
            return method.getAnnotation(Log.class).desc();
    }
    private Log.LOG_TYPE getType(JoinPoint joinPoint) {
        MethodSignature methodName = (MethodSignature) joinPoint.getSignature();
        Method method = methodName.getMethod();
        return method.getAnnotation(Log.class).type();
    }
}
