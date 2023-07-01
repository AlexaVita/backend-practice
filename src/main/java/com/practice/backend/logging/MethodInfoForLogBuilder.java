package com.practice.backend.logging;

import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

/** Для быстрого получения строки-описания вызываемого метода в виде:
 * classpath.methodName( argType argName = argValue, ... ) */
public class MethodInfoForLogBuilder {

    MethodSignature methodSignature;
    Object[] methodArguments;

    public MethodInfoForLogBuilder(Signature signature, Object[] methodArguments) {
        this.methodSignature = (MethodSignature) signature;
        this.methodArguments = methodArguments;
    }

    public String buildMethodInfoForLog() {
        StringBuilder methodInfo = new StringBuilder(methodSignature.getDeclaringTypeName() + "." + methodSignature.getName() + "(");

        Class[] parametersTypes = methodSignature.getParameterTypes();

        String[] parametersNames = methodSignature.getParameterNames();


        for (int i = 0; i < parametersTypes.length; i++) {
            methodInfo.append(parametersTypes[i].toString())
                    .append(' ')
                    .append(parametersNames[i])
                    .append(" = ")
                    .append(methodArguments[i]);
            if (i != parametersTypes.length - 1) {
                methodInfo.append(", ");
            }
        }

        methodInfo.append(')');

        return String.valueOf(methodInfo);
    }

}
