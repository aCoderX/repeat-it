package com.aoderx.spring.context.annotation;

import com.acoderx.beans.factory.BeanFactory;
import com.acoderx.beans.factory.annotation.AnnotatedBeanDefinition;
import com.acoderx.beans.factory.config.BeanDefinition;
import com.acoderx.beans.factory.support.BeanDefinitionRegistry;
import com.acoderx.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import com.acoderx.beans.factory.support.BeanNameGenerator;
import com.acoderx.beans.factory.support.DefaultBeanNameGenerator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Description:
 *
 * @author: xudi
 * @since: 2018-11-13
 */
public class ConfigurationClassPostProcessor implements BeanDefinitionRegistryPostProcessor {
    private BeanNameGenerator beanNameGenerator = new DefaultBeanNameGenerator();
    @Override
    public void postProcessBeanFactory(BeanFactory beanFactory) {
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanFactory) {
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
                                AnnotatedBeanDefinition b = new AnnotatedBeanDefinition(returnClass);
                                b.setFactoryMethod(method);
                                b.setFactoryMethodName(beanNameGenerator.generateBeanName(beanDefinition));
                                beanFactory.registerBeanDefinition(beanNameGenerator.generateBeanName(b), b);
                            }
                        }
                    }
                }
            }
        }

    }
}
