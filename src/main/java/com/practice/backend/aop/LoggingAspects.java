package com.practice.backend.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
@Aspect
@Slf4j
public class LoggingAspects {

    // Аннотация-advice, которая обеспечивает выполнение метода beforeMainControllerMethod перед каждым вызовом методов,
    // помеченных в Pointcuts как allMainControllerMethodsWithUUID().
    // С помощью JoinPoint прокидываем сигнатуру метода и, соответственно, UUID
    @Before("Pointcuts.allMainControllerMethodsWithUUID()")
    public void beforeAllMainControllerMethodsWithUUID(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Object[] arguments = joinPoint.getArgs();

        String startMessage = MessageFormat.format("Request named ''{0}'' with ID {1} has been started",
                methodSignature.getName(), arguments[0].toString()); // arguments[0] - UUID, так как Pointcut следит за методами, где первый параметр - UUID

        log.trace(startMessage);
    }

    @AfterReturning("Pointcuts.allMainControllerMethodsWithUUID()")
    public void afterAllMainControllerMethodsWithUUID(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Object[] arguments = joinPoint.getArgs();

        String startMessage = MessageFormat.format("Request named ''{0}'' with ID {1} has been finished with success",
                methodSignature.getName(), arguments[0].toString());

        log.trace(startMessage);
    }

    @AfterThrowing(pointcut = "Pointcuts.allMainControllerMethodsWithUUID()", throwing = "e")
    public void afterThrowingInAllMainControllerMethodsWithUUID(JoinPoint joinPoint, Exception e) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Object[] arguments = joinPoint.getArgs();

        String startMessage = MessageFormat.format("Request named ''{0}'' with ID {1} has been failed with error: {2}",
                methodSignature.getName(), arguments[0].toString(), e.getMessage());

        log.error(startMessage);
    }
}