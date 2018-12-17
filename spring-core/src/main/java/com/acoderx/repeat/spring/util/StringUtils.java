package com.acoderx.repeat.spring.util;

/**
 * Description:
 *
 * @author  xudi
 * @since  2018-11-22
 */
public class StringUtils {
    public static boolean isEmpty(Object str) {
        return (str == null || "".equals(str));
    }

    public static boolean isNotEmpty(Object str) {
        return !isEmpty(str);
    }
}
