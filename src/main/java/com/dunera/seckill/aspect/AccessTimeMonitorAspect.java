package com.dunera.seckill.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

/**
 * @author lyx
 * @date 2018/11/24
 */
@Aspect
@Component
public class AccessTimeMonitorAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(public * com.dunera.seckill.service.*.*(..))")
    public void service() {
    }

    @Pointcut("execution(public * com.dunera.seckill.controller.*.*(..))")
    public void controller() {
    }

    private ThreadLocal<Long> controllerStartTime = new ThreadLocal<>();
    private ThreadLocal<Long> serviceStartTime = new ThreadLocal<>();

    @Before("service()")
    public void beforeService(JoinPoint joinPoint) throws Throwable {
        serviceStartTime.set(Instant.now().toEpochMilli());
    }

    @Before("controller()")
    public void beforeController(JoinPoint joinPoint) throws Throwable {
        controllerStartTime.set(Instant.now().toEpochMilli());
    }

    @After("service()")
    public void afterService(JoinPoint joinPoint) throws Throwable {
        //处理完请求后，返回内容
        logger.debug("access_method: {}, access_time: {}", joinPoint.getSignature().getName(), (Instant.now().toEpochMilli() - serviceStartTime.get()));
        serviceStartTime.remove();
    }

    @After("controller()")
    public void afterController(JoinPoint joinPoint) throws Throwable {
        //接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            //记录请求的内容
            logger.debug("request_url: {}, request_method: {}, access_time: {}", request.getRequestURL().toString(), request.getMethod(), (Instant.now().toEpochMilli() - controllerStartTime.get()));
        }
        controllerStartTime.remove();
    }
}
