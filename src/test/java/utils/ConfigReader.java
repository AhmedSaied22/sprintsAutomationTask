package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private Properties properties;
    private final String CONFIG_FILE_PATH = "src/test/resources/config.properties";

    public ConfigReader() {
        loadProperties();
    }

    private void loadProperties() {
        try {
            properties = new Properties();
            FileInputStream fis = new FileInputStream(CONFIG_FILE_PATH);
            properties.load(fis);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Configuration file not found at " + CONFIG_FILE_PATH);
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}