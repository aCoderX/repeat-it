package com.acoderx.repeat.spring.core.io.support;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Description:
 *
 * @author xudi
 * @since 2018-12-17
 */
public class PropertiesLoaderUtils {
    public static Properties loadProperties(String resourceName) throws IOException {
        Properties properties = new Properties();
        Enumeration<URL> urls = ClassLoader.getSystemResources(resourceName);
        while (urls.hasMoreElements()) {
            URL url = urls.nextElement();
            URLConnection connection = url.openConnection();
            InputStream is = connection.getInputStream();
            try {
                if (resourceName.endsWith(".xml")) {
                    properties.loadFromXML(is);
                } else {
                    properties.load(is);
                }
            } finally {
                is.close();
            }
        }
        return properties;
    }
}
