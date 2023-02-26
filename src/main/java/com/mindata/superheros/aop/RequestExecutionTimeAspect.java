package com.mindata.superheros.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import static java.lang.System.currentTimeMillis;
import static org.slf4j.LoggerFactory.getLogger;

@Aspect
@Component
public class RequestExecutionTimeAspect {

    Logger logger = getLogger(RequestExecutionTimeAspect.class);

    @Around("@annotation(LogRequestExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = currentTimeMillis() - startTime;
        logger.info("Request {} executed in {}ms", joinPoint.getSignature().getName(), executionTime);
        return proceed;
    }

}
