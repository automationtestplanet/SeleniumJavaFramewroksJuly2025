package org.openmrs.pageobjects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class TestProperties {
    public static Properties properties;
    public static File propertiesFilePath;
    public static FileOutputStream fos;

    static {
        try {
            propertiesFilePath = new File(System.getProperty("user.dir") + "\\src\\test\\resources\\application.properties");
            properties = new Properties();
            properties.load(new FileInputStream(propertiesFilePath));
        } catch (Exception e) {
            System.out.println("Exception Occurred while reading the properties file: " + e.getMessage());
        }
    }

    public static String getTestProperty(String propertyName) {
        return properties.getProperty(propertyName).trim();
    }

    public static void setTestProperty(String propertyName, String propertyValue, String comment) {
        try {
            properties.setProperty(propertyName, propertyValue);
            fos = new FileOutputStream(propertiesFilePath);
            properties.store(fos, comment);
            fos.close();
        } catch (Exception e) {
            System.out.println("Exception Occurred while Setting the property to properties file: " + e.getMessage());
        }

    }
}
