package com.fintechtinkoff.homework.generateusers.utils;

import java.util.logging.Logger;

public class LoggerHelper {
    private static Logger loggerHelper =
            Logger.getLogger(LoggerHelper.class.getName());

    private LoggerHelper() { }

    public static void info(String message) {
        loggerHelper.info(message);
    }
    public static void warn(String message) {
        loggerHelper.severe(message);
    }
    public static void error(String message) {
        loggerHelper.warning(message);
    }
}
