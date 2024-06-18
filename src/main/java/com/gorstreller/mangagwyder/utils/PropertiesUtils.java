package com.gorstreller.mangagwyder.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils {
    private static PropertiesUtils instance;

    private PropertiesUtils() {}

    public static PropertiesUtils get() {
        if (instance == null) {
            instance = new PropertiesUtils();
        }
        return instance;
    }

    public String getProperty(String propertyFileName, String propertyName) {
        try (InputStream input = new FileInputStream(propertyFileName)) {
            Properties prop = new Properties();
            prop.load(input);
            return prop.getProperty(propertyName);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
