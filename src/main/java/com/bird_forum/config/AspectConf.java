package com.bird_forum.config;

import com.bird_forum.annotation.SetTime;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.time.LocalDateTime;

@Aspect
@Slf4j
public class AspectConf {
    @Pointcut("@annotation(com.bird_forum.annotation.SetTime) && execution(* com.bird_forum.service..*.*(..))")
    public void setTime() {
    }

    @Around("setTime() && args(obj)")
    public Object around(ProceedingJoinPoint prj, Object obj) throws Throwable {
        log.info("更新前的obj: {}", obj);

        // 方法参数
        Object[] args = prj.getArgs();
        // 方法所在类
        Class<?> clazz = prj.getTarget().getClass();
        SetTime annotation = clazz.getMethod(prj.getSignature().getName(), args[0].getClass()).getAnnotation(SetTime.class);
        obj.getClass()
                .getMethod("insert".equals(annotation.value()) ? "setCreateTime" : "setUpdateTime", LocalDateTime.class)
                .invoke(obj, LocalDateTime.now());

        log.info("更新后的obj: {}", obj);
        return prj.proceed();
    }
}
