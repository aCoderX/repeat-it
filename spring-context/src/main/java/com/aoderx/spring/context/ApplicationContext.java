package com.aoderx.spring.context;

import com.acoderx.beans.factory.BeanFactory;

/**
 * Description:
 *
 * @author  xudi
 * @since  2018-11-09
 */
public interface ApplicationContext extends BeanFactory {
    void refresh();
}
