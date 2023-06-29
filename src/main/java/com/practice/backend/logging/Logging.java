package com.practice.backend.logging;

import java.text.MessageFormat;
import java.util.UUID;

/** Класс со статическими методами, предназначенными для логирования из любой точки проекта */
public class Logging {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Logging.class);
    /** Записывает в файл и консоль информацию о методе, его вызвавшем, его id и сообщении */
    public static void info(UUID id, String methodName, String message) {
        log.info(MessageFormat.format("Method ''{0}'' with ID {1}: {2}", methodName, id, message));
    }

    /** Записывает в файл и консоль информацию о методе, его вызвавшем, его id и предупреждении */
    public static void warn(UUID id, String methodName, String message) {
        log.warn(MessageFormat.format("Warning in method ''{0}'' with ID {1}: {2}", methodName, id, message));
    }

    /** Записывает в файл и консоль информацию о методе, его вызвавшем, его id и сообщении об ошибке */
    public static void error(UUID id, String methodName, String message) {
        log.error(MessageFormat.format("Error in method ''{0}'' with ID {1}: {2}", methodName, id, message));
    }

}
