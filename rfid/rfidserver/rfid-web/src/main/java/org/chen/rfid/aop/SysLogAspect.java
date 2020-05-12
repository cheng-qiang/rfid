package org.chen.rfid.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.chen.rfid.model.annotation.SysLog;
import org.chen.rfid.model.system.OpLog;
import org.chen.rfid.service.system.impl.SysLogServiceImpl;
import org.chen.rfid.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * @author 言少钱
 * @date 2020年05月05日 12:10
 * @GitHub： https://github.com/cheng-qiang
 * @参考资料：https://www.cnblogs.com/shihaiming/p/9565892.html
 * @Description: 系统日志-切面处理类
 */
@Aspect
@Component
public class SysLogAspect {

    @Autowired
    private SysLogServiceImpl sysLogService;

    /**
     * 在@SysLog注解的位置切入标注logCut方法的代码
     */
    @Pointcut("@annotation(org.chen.rfid.model.annotation.SysLog)")
    public void logCut() {
    }

    @AfterReturning("logCut()")
    public void saveSysLog(JoinPoint joinPoint) {
        OpLog sysLog = new OpLog();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SysLog myLog = method.getAnnotation(SysLog.class);
        if (myLog != null) {
            String value = myLog.value();
            sysLog.setOperate(value);
        }
        sysLog.setAddDate(new Date());
        sysLog.setUserId(UserUtil.getCurrentUser().getId());
        sysLog.setUsername(UserUtil.getCurrentUser().getName());
        sysLogService.saveSysLog(sysLog);
    }
}
