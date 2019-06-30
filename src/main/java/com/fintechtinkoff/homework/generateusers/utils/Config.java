package com.fintechtinkoff.homework.generateusers.utils;

import java.io.FileInputStream;
import java.util.Properties;

public class Config {
    private static Properties defaultProps = new Properties();

    static {
        try(FileInputStream filProp = new FileInputStream("src/main/resources/config.properties")) {
            defaultProps.load(filProp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return defaultProps.getProperty(key);
    }
}
