package com.user.userprofileservice.aspects;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.user..*.*(..))")
    public void allMethods() {}

    @AfterReturning(pointcut = "allMethods()", returning = "result")
    public void logAfterReturning(Object result) {
        logger.info("Method returned: {}", result);
    }
}
