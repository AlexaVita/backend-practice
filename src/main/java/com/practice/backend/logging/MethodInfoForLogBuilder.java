package com.practice.backend.logging;

import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.UUID;

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

        String classOfMethod = methodSignature.getDeclaringTypeName();
        // Отсечь всё кроме класса
        classOfMethod = classOfMethod.substring(classOfMethod.lastIndexOf('.') + 1);

        StringBuilder methodInfo = new StringBuilder(classOfMethod + "." + methodSignature.getName() + "(");

        Class[] parametersTypes = methodSignature.getParameterTypes();

        String[] parametersNames = methodSignature.getParameterNames();


        for (int i = 0; i < parametersTypes.length; i++) {

            if (parametersTypes[i] == UUID.class) {
                continue;
            }

            String parameterType = parametersTypes[i].toString();
            // Отсечь всё кроме класса
            parameterType = parameterType.substring(parameterType.lastIndexOf('.') + 1);

            methodInfo.append(parameterType)
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

    public Object getFieldFromMethod(String fieldName) {
        String[] parametersNames = methodSignature.getParameterNames();

        for (int i = 0; i < parametersNames.length; i++) {
            if (parametersNames[i].equals(fieldName)) {
                return methodArguments[i];
            }
        }

        return null;
    }

}
