package com.practice.backend.aop;

import org.aspectj.lang.annotation.Pointcut;

/** Здесь перечислены все точки приложения (методы классов), действия над которыми отслеживаются в Aspects */
public class Pointcuts {

    /**  Любые методы класса MainController с любыми модификаторами доступа и возвращаемыми значениями;
        с любыми параметрами в сигнатуре, но первый параметр всегда типа UUID. */
    @Pointcut("execution(* com.practice.backend.controller.MainRestController.*(java.util.UUID, ..))")
    public void allMainControllerMethodsWithUUID() {}

}
