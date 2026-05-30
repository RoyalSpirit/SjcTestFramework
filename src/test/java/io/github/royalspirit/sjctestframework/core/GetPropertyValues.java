package io.github.royalspirit.sjctestframework.core;

import java.io.InputStream;
import java.util.Properties;

public class GetPropertyValues {

    private static final String PROPERTY_FILE = "configuration/config.properties";
    private static final Properties properties = loadProperties();

    public static String getProperty(String propertyName) throws Exception {
        return getRequiredProperty(propertyName);
    }

    public static String getRequiredProperty(String propertyName) throws Exception {
        String value = getOptionalProperty(propertyName);

        if (value == null) {
            throw new Exception("Property value '" + propertyName + "' can not be empty.");
        }

        return value;
    }

    public static String getOptionalProperty(String propertyName) {
        String propertyValue = System.getProperty(propertyName);

        if (propertyValue == null) {
            propertyValue = properties.getProperty(propertyName);
        }

        return normalizePropertyValue(propertyValue);
    }

    public static boolean getBooleanProperty(String propertyName, boolean defaultValue) {
        String value = getOptionalProperty(propertyName);
        return value == null ? defaultValue : Boolean.parseBoolean(value);
    }

    private static String normalizePropertyValue(String propertyValue) {
        if (propertyValue == null || propertyValue.isBlank() || propertyValue.equalsIgnoreCase("null")) {
            return null;
        }

        return propertyValue;
    }

    private static Properties loadProperties() {
        Properties loadedProperties = new Properties();
        ClassLoader loader = ClassLoader.getSystemClassLoader();

        try (InputStream inputStream = loader.getResourceAsStream(PROPERTY_FILE)) {
            if (inputStream == null) {
                throw new IllegalStateException("Configuration file not found: " + PROPERTY_FILE);
            }

            loadedProperties.load(inputStream);
            return loadedProperties;
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load configuration file: " + PROPERTY_FILE, e);
        }
    }

}