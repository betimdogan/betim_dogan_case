package com.insider.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;

    static {
        try {
            String configPath = "src/test/resources/config.properties";
            FileInputStream fileInputStream = new FileInputStream(configPath);
            properties = new Properties();
            properties.load(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties file!", e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
