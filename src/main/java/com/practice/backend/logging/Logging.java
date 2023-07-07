package com.practice.backend.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.UUID;

/** Класс со статическими методами, предназначенными для логирования из любой точки проекта */
@Component
public class Logging {
    private final Logger log;

    public Logging() {
        log = LoggerFactory.getLogger(Logging.class);
    }

    /** Записывает в файл и консоль информацию о методе, его вызвавшем, и о сообщении */
    public void info(UUID uuid, String methodInfo, String message) {
        log.info(MessageFormat.format("Method ''{0}'', uuid = {2}: {1}", methodInfo, message, uuid));
    }

    /** Записывает в файл и консоль информацию о методе, его вызвавшем, и об предупреждении */
    public void warn(UUID uuid, String methodInfo, String message) {
        log.warn(MessageFormat.format("Warning in method ''{0}'', uuid = {2}: {1}", methodInfo, message, uuid));
    }

    /** Записывает в файл и консоль информацию о методе, его вызвавшем, и сообщении об ошибке */
    public void error(UUID uuid, String methodInfo, String message) {
        log.error(MessageFormat.format("Error in method ''{0}'', uuid = {2}: {1}", methodInfo, message, uuid));
    }

    public void debug(UUID uuid, String methodInfo, String message) {
        log.debug(MessageFormat.format("Method ''{0}'', uuid = {2}: {1}", methodInfo, message, uuid));
    }

}
