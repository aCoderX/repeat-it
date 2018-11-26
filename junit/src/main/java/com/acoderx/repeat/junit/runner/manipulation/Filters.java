package com.acoderx.repeat.junit.runner.manipulation;

import com.acoderx.repeat.junit.runner.Description;

/**
 * Description: 一些常用的过滤器工厂
 *
 * @author  xudi
 * @since  2018-10-29
 */
public class Filters {
    /**
     * 方法名过滤
     * @param description
     * @return
     */
    public static Filter matchMethod(Description description) {
        return new Filter() {
            @Override
            public boolean shouldRun(Description d) {
                if(description.equals(d)){
                    return true;
                }
                return false;
            }
        };
    }
}
