package com.test.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * ConfigurationReader, Utility-Klasse für die Konfiguration
 */
public class ConfigurationReader {
    private static Properties properties;

    /**
     * Konstruktor für die ConfigurationReader-Klasse
     */
    static {
        try {
            String path = "configuration.properties";
            FileInputStream input = new FileInputStream(path);
            properties = new Properties();
            properties.load(input);
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load configuration.properties file!");
        }
    }

    /**
     * Liest eine Eigenschaft aus der Konfigurationsdatei
     * @param key Schlüssel der Eigenschaft
     * @return Wert der Eigenschaft
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
} 