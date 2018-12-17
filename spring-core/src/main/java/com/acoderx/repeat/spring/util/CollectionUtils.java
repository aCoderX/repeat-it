package com.acoderx.repeat.spring.util;

import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

/**
 * Description:
 *
 * @author xudi
 * @since 2018-12-17
 */
public class CollectionUtils {
    public static void mergeProertiesIntoMap(Properties properties, Map map) {
        if (properties != null) {
            Enumeration names = properties.propertyNames();
            while (names.hasMoreElements()) {
                String key = (String) names.nextElement();
                Object value = properties.get(key);
                map.put(key, value);
            }
        }
    }
}
