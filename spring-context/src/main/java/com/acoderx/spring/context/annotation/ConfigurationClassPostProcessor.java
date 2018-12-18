package com.acoderx.spring.context.annotation;

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
 * Description:BeanFactory后处理器，在applicationContext refresh中被调用。
 * 用于解析@Configuration注解标注的类，将被@Bean注解的内容解析为BeanDefinition，并注册
 *
 * @author  xudi
 * @since  2018-11-13
 */
public class ConfigurationClassPostProcessor implements BeanDefinitionRegistryPostProcessor {
    private BeanNameGenerator beanNameGenerator = new DefaultBeanNameGenerator();
    private ConfigurationClassParser configurationClassParser = new ConfigurationClassParser();
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
                        configurationClassParser.parse(beanDefinition, beanFactory);
                    }
                }
            }
        }

    }
}
