package com.acoderx.spring.http;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 *
 * @author xudi
 * @since 2019-03-01
 */
public enum HttpMethod {
    GET,POST,PUT,DELETE;

    private static final Map<String, HttpMethod> mappings = new HashMap<>();
    static {
        for (HttpMethod value : values()) {
            mappings.put(value.name(), value);
        }
    }
    public static HttpMethod resolve(String method) {
        return mappings.get(method);
    }
    public boolean matches(String method) {
        return this == resolve(method);
    }
}
