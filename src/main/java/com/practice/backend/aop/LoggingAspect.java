package com.practice.backend.aop;

import com.practice.backend.logging.Logging;
import com.practice.backend.logging.MethodInfoForLogBuilder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/** Логика логирования, вынесенная как сквозная для проекта */
@Component
@Aspect
public class LoggingAspect {

    @Around("Pointcuts.allControllersMethodsWithUUID()")
    public Object aroundAllMainControllerMethodsWithUUID(ProceedingJoinPoint proceedingJoinPoint) {

        String methodInfo = new MethodInfoForLogBuilder(proceedingJoinPoint.getSignature(), proceedingJoinPoint.getArgs())
                .buildMethodInfoForLog();

        Logging.info(methodInfo, "request starts");

        Long startTime = System.currentTimeMillis();

        Object result;

        try {
            result = proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            Logging.error(methodInfo, e.getMessage());
            throw new RuntimeException(e);
        }

        Long endTime = System.currentTimeMillis();

        Logging.info(methodInfo, "request finished with success with result: " + result + " by " + (endTime - startTime) + " millis");

        return result;

    }

}