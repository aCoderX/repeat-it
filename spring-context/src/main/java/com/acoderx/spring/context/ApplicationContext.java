package com.acoderx.spring.context;

import com.acoderx.beans.factory.HierachicalBeanFactory;

/**
 * Description:
 *
 * @author  xudi
 * @since  2018-11-09
 */
public interface ApplicationContext extends HierachicalBeanFactory {
    ApplicationContext getParent();

    void setParent(ApplicationContext applicationContext);

    void refresh();
}
