package com.ecore.roleapi.annotation;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
@Aspect
@Slf4j
public class ApilogAspect {

    @Pointcut("@annotation(com.ecore.roleapi.annotation.Apilog)")
    public void logPointcut(){
    }


    @Around("logPointcut()")
    public Object logAllMethodCallsAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("BEGIN Method - {}",joinPoint.getSignature().getName());
        LocalDateTime initRequest = LocalDateTime.now();
        Object response = joinPoint.proceed();
        log.info("END Method - {} - Total Time Execution: {} miliseconds",joinPoint.getSignature().getName(),
                ChronoUnit.MILLIS.between(initRequest, LocalDateTime.now()));

        return response;
    }
}
