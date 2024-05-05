package com.cts.wishlistservice.aspects;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Log4j2
public class LoggingAspect {

    @Pointcut("execution(* com.cts.*.*(..))")
    public void allMethods() {}

    @AfterReturning(pointcut = "allMethods()", returning = "result")
    public void logAfterReturning(Object result) {
        log.info("Method returned: {}", result);
    }
}
