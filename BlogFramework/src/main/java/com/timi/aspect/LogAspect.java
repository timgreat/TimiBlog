package com.timi.aspect;

import com.alibaba.fastjson.JSON;
import com.timi.annotation.SystemLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Component
@Aspect
@Slf4j
public class LogAspect {
    @Pointcut("@annotation(com.timi.annotation.SystemLog)")
    public void pt(){
    }
    @Around("pt()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object ret = null;
        try {
            handleBefore(joinPoint);
            ret=joinPoint.proceed();
            handleAfter(ret);
        }finally {
            //结束后换行
            log.info("========END======="+System.lineSeparator());
        }
        return ret;
    }

    private void handleAfter(Object ret) {
        //打印出参
        log.info("Response        :{}",JSON.toJSONString(ret));
     }

    private void handleBefore(ProceedingJoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request=requestAttributes.getRequest();
        //获取被增强方法的注解对象
        SystemLog systemLog=getSystemLog(joinPoint);

        log.info("=======Start=======");
        //获取URL
        log.info("URL             :{}",request.getRequestURL());
        //获取描述信息
        log.info("BusinessName    :{}",systemLog.businessName());
        //获取Http方法
        log.info("HTTP Method     :{}",request.getMethod());
        //获取controller的全路径及执行方法
        log.info("Class Method    :{}.{}",joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        log.info("IP              :{}",request.getRemoteHost());
        log.info("Request Args    :{}", JSON.toJSONString(joinPoint.getArgs()));
    }

    private SystemLog getSystemLog(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature=(MethodSignature) joinPoint.getSignature();
        SystemLog systemLog=methodSignature.getMethod().getAnnotation(SystemLog.class);
        return systemLog;
    }

}
