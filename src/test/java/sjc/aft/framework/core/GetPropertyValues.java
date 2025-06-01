package sjc.aft.framework.core;

import java.io.InputStream;
import java.util.Properties;

public class GetPropertyValues {

    public static Properties properties = new Properties();

    public static Properties getProp() throws Exception {
        String propertyFile = "configuration/config.properties";
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        try (InputStream fileInputStream = loader.getResourceAsStream(propertyFile)) {
            properties.load(fileInputStream);
            return properties;
        }
    }

    public static String getProperty(String propertyName) throws Exception {
        String propertyValue = getProp().getProperty(propertyName);
        if (propertyValue.isEmpty()) {
            throw new Exception("Значение проперти '" + propertyName + "' не может быть пустым.");
        }
        return (propertyValue.equals("null")) ? null : propertyValue;
    }



}
