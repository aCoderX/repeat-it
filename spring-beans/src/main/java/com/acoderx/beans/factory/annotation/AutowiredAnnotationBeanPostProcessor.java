package com.acoderx.beans.factory.annotation;

import com.acoderx.beans.factory.BeanFactory;
import com.acoderx.beans.factory.BeanFactoryAware;
import com.acoderx.beans.factory.config.BeanDefinition;
import com.acoderx.beans.factory.config.ConfigurableListableBeanFactory;
import com.acoderx.beans.factory.config.InstantiationAwareBeanPostProcessor;

import java.lang.reflect.Field;

/**
 * Description:用于被Autowired的参数注入
 *
 * @author  xudi
 * @since  2018-11-23
 */
public class AutowiredAnnotationBeanPostProcessor implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {
    private ConfigurableListableBeanFactory beanFactory;
    @Override
    public void postProcessPropertyValues(Object o,BeanDefinition beanDefinition) {
        Class c = beanDefinition.getBeanClass();
        Field[] fields = c.getDeclaredFields();
        for (Field field : fields) {
            if (field.getAnnotation(Autowired.class) != null) {
                try {
                    field.setAccessible(true);
                    field.set(o, beanFactory.getBean(field.getName()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        if(beanFactory instanceof ConfigurableListableBeanFactory){
            this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
        }
    }
}
