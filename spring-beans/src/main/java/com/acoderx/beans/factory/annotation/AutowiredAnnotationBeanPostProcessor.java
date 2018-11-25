package com.acoderx.beans.factory.annotation;

import com.acoderx.beans.factory.config.BeanDefinition;
import com.acoderx.beans.factory.config.ConfigurableListableBeanFactory;
import com.acoderx.beans.factory.config.InstantiationAwareBeanPostProcessor;

import java.lang.reflect.Field;

/**
 * Description:
 *
 * @author: xudi
 * @since: 2018-11-23
 */
public class AutowiredAnnotationBeanPostProcessor implements InstantiationAwareBeanPostProcessor {
    //TODO 初始化注入beanfactory
    private ConfigurableListableBeanFactory beanFactory;
    @Override
    public void postProcessPropertyValues(Object o,BeanDefinition beanDefinition) {
        Class c = beanDefinition.getBeanClass();
        Field[] fields = c.getDeclaredFields();
        for (Field field : fields) {
            if (field.getAnnotationsByType(Autowired.class) != null) {
                //目前只能注入bean，不能注入基础类型
                try {
                    field.setAccessible(true);
                    field.set(o, beanFactory.getBean(field.getName()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
