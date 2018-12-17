package com.acoderx.beans;

/**
 * Description:
 *
 * @author xudi
 * @since 2018-12-17
 */
public class BeanUtils {
    public static <T> T instantiate(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
