package ru.asmirnov.accservice.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;

/**
 * @author Alexey Smirnov at 28/03/2018
 */
@Aspect
@Configuration
public class ControllerAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Around("execution(* (@org.springframework.web.bind.annotation.RestController *).*(..))")
    public Object aroundControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        try {
            Object proceed = joinPoint.proceed();
            logger.debug("{} executed in {}", joinPoint.getSignature().getName(), stopWatch.getTotalTimeMillis());
            return proceed;
        } catch (Throwable throwable) {
            logger.debug("{} executed in {}", joinPoint.getSignature().getName(), stopWatch.getTotalTimeMillis());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
