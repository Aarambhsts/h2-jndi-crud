package com.unisys.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.unisys.controller.*.*(..))")
    public void logBeforeControllerMethods(JoinPoint joinPoint) {
        if (logger.isInfoEnabled()) {
            logger.info("Controller method invoked: {}", joinPoint.getSignature());
            logger.info("Arguments: {}", Arrays.toString(joinPoint.getArgs()));
        }
    }

    @Before("execution(* com.unisys.service.*.*(..))")
    public void logBeforeServiceMethods(JoinPoint joinPoint) {
        if (logger.isInfoEnabled()) {
            logger.info("Service method invoked: {}", joinPoint.getSignature());
            logger.info("Arguments: {}", Arrays.toString(joinPoint.getArgs()));
        }
    }
}
