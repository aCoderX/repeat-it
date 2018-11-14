package com.aoderx.spring.context.annotation;

import com.acoderx.beans.factory.annotation.AnnotatedBeanDefinition;
import com.acoderx.beans.factory.config.BeanDefinition;
import com.acoderx.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import com.acoderx.beans.factory.support.DefaultListableBeanFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Description:
 *
 * @author: xudi
 * @since: 2018-11-13
 */
public class ConfigurationClassPostProcessor implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanFactory(DefaultListableBeanFactory beanFactory) {

    }

    @Override
    public void postProcessBeanDefinitionRegistry(DefaultListableBeanFactory beanFactory) {
        //简单解析Configuration
        String[] names = beanFactory.getBeanDefinitionNames();
        for (String name : names) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(name);
            if (beanDefinition instanceof AnnotatedBeanDefinition) {
                Annotation[] annotations = ((AnnotatedBeanDefinition) beanDefinition).getAnnotations();
                for (Annotation annotation : annotations) {
                    if (annotation instanceof Configuration) {
                        Class c = beanDefinition.getBeanClass();
                        Method[] methods = c.getDeclaredMethods();
                        for (Method method : methods) {
                            Annotation a = method.getAnnotation(Bean.class);
                            if (a != null) {
                                Class returnClass = method.getReturnType();
                                beanFactory.registerBeanDefinition(returnClass.getName(), new AnnotatedBeanDefinition(returnClass));
                            }
                        }
                    }
                }
            }
        }

    }
}
