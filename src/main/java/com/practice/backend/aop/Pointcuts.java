package com.practice.backend.aop;

import org.aspectj.lang.annotation.Pointcut;

/** Здесь перечислены все точки приложения (методы классов), действия над которыми отслеживаются в Aspects */
public class Pointcuts {

    /**  Любые методы классов *Controller (любых контроллеров) с любыми модификаторами доступа и возвращаемыми значениями;
        с любыми параметрами в сигнатуре, среди которых есть UUID. */
    @Pointcut("execution(* com.practice.backend.controller.*Controller.*(.., java.util.UUID, ..))")
    public void allControllersMethodsWithUUID() {}

}
