package com.acoderx.repeat.junit.runner.manipulation;

/**
 * Description:当类实现该接口，表面该类是可被过滤的
 *
 * @author  xudi
 * @since  2018-10-29
 */
public interface Filterable {
    /**
     * 使用filter进行相应过略
     * @param filter
     */
    void filter(Filter filter);
}
