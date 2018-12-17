package com.acoderx.beans.factory.xml;

import com.acoderx.beans.BeanUtils;
import com.acoderx.repeat.spring.core.io.support.PropertiesLoaderUtils;
import com.acoderx.repeat.spring.util.CollectionUtils;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description:解析META-INF/spring.handlers下的自定义xml解析器
 *
 * @author xudi
 * @since 2018-12-14
 */
public class DefaultNamespaceHandlerResolver implements NamespaceHandlerResolver {
    public static final String DEFAULT_HANDLER_MAPPINGS_LOCATION = "META-INF/spring.handlers";
    private volatile Map<String, Object> handlerMappings;
    @Override
    public NamespaceHandler resolve(String namespaceUri) {
        Object handler = getHandlerMappings().get(namespaceUri);
        if (handler instanceof NamespaceHandler) {
            return (NamespaceHandler) handler;
        } else {
            try {
                Class<?> handlerClass = Class.forName((String) handler);
                NamespaceHandler namespaceHandler = (NamespaceHandler) BeanUtils.instantiate(handlerClass);
                namespaceHandler.init();
                handlerMappings.put(namespaceUri, namespaceHandler);
                return namespaceHandler;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private Map<String,Object> getHandlerMappings() {
        Map<String, Object> map = handlerMappings;
        if (map==null) {
            synchronized (this) {
                map = this.handlerMappings;
                if (map == null) {
                    map = new ConcurrentHashMap<>();
                    try {
                        Properties properties = PropertiesLoaderUtils.loadProperties(DEFAULT_HANDLER_MAPPINGS_LOCATION);
                        CollectionUtils.mergeProertiesIntoMap(properties, map);
                        this.handlerMappings = map;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return handlerMappings;
    }
}
