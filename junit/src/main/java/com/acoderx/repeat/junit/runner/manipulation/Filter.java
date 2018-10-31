package com.acoderx.repeat.junit.runner.manipulation;

import com.acoderx.repeat.junit.runner.Description;

/**
 * Description:过滤器
 *
 * @author: xudi
 * @since: 2018-10-29
 */
public abstract class Filter {
    public boolean apply(Object each){
        if (each instanceof Filterable) {
            ((Filterable) each).filter(this);
        }
        return true;
    }

    public abstract boolean shouldRun(Description description);
}
