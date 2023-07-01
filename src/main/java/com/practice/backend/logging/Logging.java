package com.practice.backend.logging;

import java.text.MessageFormat;

/** Класс со статическими методами, предназначенными для логирования из любой точки проекта */
public class Logging {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Logging.class);
    /** Записывает в файл и консоль информацию о методе, его вызвавшем, и о сообщении */
    public static void info(String methodInfo, String message) {
        log.info(MessageFormat.format("Method ''{0}'': {1}", methodInfo, message));
    }

    /** Записывает в файл и консоль информацию о методе, его вызвавшем, и об предупреждении */
    public static void warn(String methodInfo, String message) {
        log.warn(MessageFormat.format("Warning in method ''{0}'': {1}", methodInfo, message));
    }

    /** Записывает в файл и консоль информацию о методе, его вызвавшем, и сообщении об ошибке */
    public static void error(String methodInfo, String message) {
        log.error(MessageFormat.format("Error in method ''{0}'': {1}", methodInfo, message));
    }

}
