package com.acoderx.repeat.spring.util;

/**
 * Description:
 *
 * @author xudi
 * @since 2019-01-16
 */
public class ArrayUtils {

    public static String[] concat(String[] a, String[] b) {
        String[] c= new String[a.length+b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }

}
