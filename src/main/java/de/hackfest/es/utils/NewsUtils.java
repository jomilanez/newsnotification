package de.hackfest.es.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: abaranga
 * Date: 28.05.14
 * Time: 13:28
 * To change this template use File | Settings | File Templates.
 */
public class NewsUtils {

    public static Properties loadResourceProperties(final String name) {
        Properties properties = new Properties();
        try (InputStream inputStream = NewsUtils.class.getClassLoader().getResourceAsStream(name)) {
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new IOException("Cannot open properties file " + name);
            }
        } catch (IOException e) {

            throw new IllegalArgumentException("Could not load property file" + name);
        }
        return properties;
    }
}
