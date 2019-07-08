package com.fintechtinkoff.homework.generateusers.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.stream.Stream;

public final class FileHelper {
    private static Properties defaultProps = new Properties();
    private static String commonDirectory = System.getProperty("user.dir");

    static {
        try (var filProp =
                    new FileInputStream("src/main/resources/config.properties")) {
            defaultProps.load(filProp);
        } catch (Exception e) {
            LoggerHelper.error(e.getMessage());
        }
    }

    private FileHelper() { }

    public static String getProperty(String key) {
        return defaultProps.getProperty(key);
    }
    public static String getFullPath(String propertyName) {
        return commonDirectory + getProperty(propertyName);
    }
    public static Stream<String> readFile(String propertyName)
            throws IOException {
        return Files.lines(Paths.get(getFullPath(propertyName)));
    }
}
