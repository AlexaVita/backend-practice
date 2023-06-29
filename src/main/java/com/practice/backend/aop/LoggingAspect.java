package com.practice.backend.aop;

import com.practice.backend.logging.Logging;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.UUID;

/** Логика логирования, вынесенная как сквозная для проекта */
@Component
@Aspect
public class LoggingAspect {

    // Аннотация-advice, которая обеспечивает выполнение метода beforeMainControllerMethod перед каждым вызовом методов,
    // помеченных в Pointcuts как allMainControllerMethodsWithUUID().
    // С помощью JoinPoint прокидываем сигнатуру метода и, соответственно, UUID
    @Before("Pointcuts.allControllersMethodsWithUUID()")
    public void beforeAllMainControllerMethodsWithUUID(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Logging.info((UUID) joinPoint.getArgs()[0], methodSignature.getName(), "request start");
    }

    // После return (а значит и успеха операции) после методов контроллеров
    @AfterReturning("Pointcuts.allControllersMethodsWithUUID()")
    public void afterAllMainControllerMethodsWithUUID(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Logging.info((UUID) joinPoint.getArgs()[0], methodSignature.getName(), "request finished with success");
    }

    // После ошибок в любом классе контроллере
    @AfterThrowing(pointcut = "Pointcuts.allControllersMethodsWithUUID()", throwing = "e")
    public void afterThrowingInAllMainControllerMethodsWithUUID(JoinPoint joinPoint, Exception e) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Logging.info((UUID) joinPoint.getArgs()[0], methodSignature.getName(), e.getMessage());
    }
}