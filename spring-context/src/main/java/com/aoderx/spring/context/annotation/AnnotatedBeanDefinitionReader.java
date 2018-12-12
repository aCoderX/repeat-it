package com.aoderx.spring.context.annotation;

import com.acoderx.beans.factory.annotation.AnnotatedBeanDefinition;
import com.acoderx.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.acoderx.beans.factory.support.BeanDefinitionRegistry;
import com.acoderx.beans.factory.support.BeanNameGenerator;
import com.acoderx.beans.factory.support.DefaultBeanNameGenerator;
import com.acoderx.beans.factory.support.RootBeanDefinition;

/**
 * Description:将配置bean注册成BeanDefinition
 *
 * @author xudi
 * @since 2018-12-12
 */
public class AnnotatedBeanDefinitionReader {
    private BeanNameGenerator beanNameGenerator = new DefaultBeanNameGenerator();

    private BeanDefinitionRegistry registry;

    public AnnotatedBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
        registerAnnotationConfigProcessors();
    }

    public void register(Class... classes) {
        for (Class aClass : classes) {
            AnnotatedBeanDefinition beanDefinition = new AnnotatedBeanDefinition(aClass);
            this.registry.registerBeanDefinition(beanNameGenerator.generateBeanName(beanDefinition), beanDefinition);
        }
    }

    /**
     * 注册注解需要用到的后处理器
     */
    private void registerAnnotationConfigProcessors() {
        //注册扫描@Configuration注解的BeanFactoryPostProcessor
        registry.registerBeanDefinition(ConfigurationClassPostProcessor.class.getName(),
                new RootBeanDefinition(ConfigurationClassPostProcessor.class));
        //注册扫描@Autowired注解的BeanPostProcessor
        registry.registerBeanDefinition(AutowiredAnnotationBeanPostProcessor.class.getName(),
                new RootBeanDefinition(AutowiredAnnotationBeanPostProcessor.class));
    }
}
