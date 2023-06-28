package com.practice.backend.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogRequestController {

    // Аннотация, которая обеспечивает выполнение метода beforeControllerMethod перед каждым вызовом методов
    // с любыми модификаторами доступа и возвращаемыми значениями из любых классов пакета controller с
    // любыми параметрами в сигнатуре. С помощью JoinPoint прокидываем сигнатуру метода и, соответственно, UUID
    @Before("execution(* com.practice.backend.controller.MainRestController.*(..))")
    public void beforeControllerMethod(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Object[] arguments = joinPoint.getArgs();

        /* TODO логирование в файл (Пока только вывод в консоль) */
        System.out.printf("Request with method signature %s and ID %s has been started\n",
                methodSignature.toString(), arguments[0].toString()); // Подразумевается, что arguments[0] - UUID
    }

}
