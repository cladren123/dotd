package com.dotd.user.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    // Around :  실행 전, 후
    // controller 단에서 실행하고 응답까지 시간을 표시
    @Around("execution(* com.dotd.user.controller..*(..))")
    public Object logAroundControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        // log.info("Around 시작 : {}", joinPoint.getSignature().toShortString());

        Object result = joinPoint.proceed();  // 실제 메서드 실행

        long elapsedTime = System.currentTimeMillis() - startTime;
        log.info("위치 : {} / 걸린 시간 :  {} ms", joinPoint.getSignature().toShortString(), elapsedTime);
        return result;
    }



    /*
    // Before : 메소드 실행 전
    @Before("execution(* com.dotd.user.controller..*(..))")
    public void logBefore(JoinPoint joinPoint) {
        log.info("Before 실행 : {}", joinPoint.getSignature().toShortString() );
    }

    // After : 메소드 실행 후
    @After("execution(* com.dotd.user.controller..*(..))")
    public void logAfter(JoinPoint joinPoint) {
        log.info("After 실행 : {}", joinPoint.getSignature().toShortString() );
    }

    // AfterReturning : 메소드가 실행하고 반환된 후 실행
    @AfterReturning(value = "execution(* com.dotd.user.controller..*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        log.info("AfterReturning 실행 : {} + 결과 : {}", joinPoint.getSignature().toShortString(), result);
    }

    // AfterThrowing : 메소드에 예외가 발생했을 때 실행
    @AfterThrowing(pointcut = "execution(* com.dotd.user.controller..*(..))", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        log.info("AfterThrowing 실행 : {} + 예외 : {}", joinPoint.getSignature().toShortString(), e);
    }
     */



}
