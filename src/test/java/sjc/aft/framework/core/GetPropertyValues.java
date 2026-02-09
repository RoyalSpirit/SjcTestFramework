package sjc.aft.framework.core;

import java.io.InputStream;
import java.util.Properties;

public class GetPropertyValues {

    public static Properties properties = new Properties();

    /**
     * Loads properties from the configuration file.
     * @return loaded properties
     * @throws Exception if the properties file cannot be loaded
     */
    public static Properties getProp() throws Exception {
        String propertyFile = "configuration/config.properties";
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        try (InputStream fileInputStream = loader.getResourceAsStream(propertyFile)) {
            properties.load(fileInputStream);
            return properties;
        }
    }

    /**
     * Gets a property value by name from the configuration.
     * @param propertyName the name of the property
     * @return the property value, or null if the value is "null"
     * @throws Exception if the property value is empty or cannot be loaded
     */
    public static String getProperty(String propertyName) throws Exception {
        String propertyValue = getProp().getProperty(propertyName);
        if (propertyValue.isEmpty()) {
            throw new Exception("Property value '" + propertyName + "' can not be empty.");
        }
        return (propertyValue.equals("null")) ? null : propertyValue;
    }



}
