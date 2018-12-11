package com.aoderx.spring.context.support;

import com.acoderx.beans.factory.config.ConfigurableListableBeanFactory;
import com.acoderx.beans.factory.support.DefaultListableBeanFactory;

/**
 * Description:在spring上下文refresh时，自动初始化上下文（加载BeanDefinition）
 *
 * @author xudi
 * @since 2018-12-11
 */
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {
    private DefaultListableBeanFactory beanFactory;

    @Override
    protected void refreshBeanFactory() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        loadBeanDefinitions(beanFactory);
        if (this.beanFactory == null) {
            this.beanFactory = beanFactory;
        }
    }

    @Override
    public ConfigurableListableBeanFactory getBeanFactory() {
        return beanFactory;
    }

    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory);

}
