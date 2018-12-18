package com.acoderx.spring.context.annotation;

import com.acoderx.beans.factory.annotation.AnnotatedBeanDefinition;
import com.acoderx.beans.factory.config.BeanDefinition;
import com.acoderx.beans.factory.support.BeanDefinitionRegistry;
import com.acoderx.beans.factory.support.BeanNameGenerator;
import com.acoderx.beans.factory.support.DefaultBeanNameGenerator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Description:解析@configuration注解，并注册
 *
 * @author xudi
 * @since 2018-12-18
 */
public class ConfigurationClassParser {
    private BeanNameGenerator beanNameGenerator = new DefaultBeanNameGenerator();
    private ComponentScanAnnotationParser componentScanAnnotationParser = new ComponentScanAnnotationParser();
    public void parse(BeanDefinition beanDefinition, BeanDefinitionRegistry registry) {
        Annotation[] annotations = ((AnnotatedBeanDefinition) beanDefinition).getAnnotations();
        for (Annotation annotation : annotations) {
            //自动扫描
            if (annotation instanceof ComponentScan) {
                componentScanAnnotationParser.parse(registry);
            }
        }

        Class c = beanDefinition.getBeanClass();
        Method[] methods = c.getDeclaredMethods();
        for (Method method : methods) {
            Annotation a = method.getAnnotation(Bean.class);
            if (a != null) {
                Class returnClass = method.getReturnType();
                AnnotatedBeanDefinition b = new AnnotatedBeanDefinition(returnClass);
                //决定了使用工厂方法来创建
                b.setFactoryMethod(method);
                b.setFactoryMethodName(beanNameGenerator.generateBeanName(beanDefinition));
                registry.registerBeanDefinition(beanNameGenerator.generateBeanName(b), b);
            }
        }
    }
}
