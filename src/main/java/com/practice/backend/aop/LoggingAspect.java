package com.practice.backend.aop;

import com.practice.backend.logging.Logging;
import com.practice.backend.logging.MethodInfoForLogBuilder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.UUID;

/** Логика логирования, вынесенная как сквозная для проекта */
@Component
@Aspect
public class LoggingAspect {

    Logging logger = new Logging();

    @Around("Pointcuts.allControllersMethodsWithUUID()")
    public Object aroundAllMainControllerMethodsWithUUID(ProceedingJoinPoint proceedingJoinPoint) {

        MethodInfoForLogBuilder methodInfoBuilder = new MethodInfoForLogBuilder(proceedingJoinPoint.getSignature(), proceedingJoinPoint.getArgs());

        String methodInfo = methodInfoBuilder.buildMethodInfoForLog();

        UUID uuid = (UUID) methodInfoBuilder.getFieldFromMethod("uuid");

        logger.info(uuid, methodInfo, "request starts");

        Long startTime = System.currentTimeMillis();

        Object result;

        try {
            result = proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            logger.error(uuid, methodInfo, e.getMessage());
            throw new RuntimeException(e);
        }

        Long endTime = System.currentTimeMillis();

        logger.info(uuid, methodInfo, "request finished with success with result: " + result + " by " + (endTime - startTime) + " millis");

        return result;

    }

}