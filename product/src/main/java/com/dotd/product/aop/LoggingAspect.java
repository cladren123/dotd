package com.dotd.product.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    // 성능 측정 AOP : API 경과 시간 계산
    @Around("execution(* com.dotd.product.controller..*(..))")
    public Object calculateApiElapsedTime(ProceedingJoinPoint joinPoint) throws Throwable{

        long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long elapsedTime = System.currentTimeMillis() - startTime;
        log.info("위치 : {} / 경과 시간 : {}ms", joinPoint.getSignature().toShortString(), elapsedTime);
        return result;
    }

}
