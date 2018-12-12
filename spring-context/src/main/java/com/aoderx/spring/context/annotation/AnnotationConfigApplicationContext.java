package com.aoderx.spring.context.annotation;

import com.acoderx.beans.factory.config.ConfigurableListableBeanFactory;
import com.acoderx.beans.factory.support.DefaultListableBeanFactory;
import com.aoderx.spring.context.support.AbstractApplicationContext;

/**
 * Description:注解的应用上下文，可用于扫描某一包下所有bean或者解析@Configuration注解的bean
 *
 * @author  xudi
 * @since  2018-11-09
 */
public class AnnotationConfigApplicationContext extends AbstractApplicationContext {

    private AnnotatedBeanDefinitionReader reader;

    private ClassPathBeanDefinitionScanner scanner;

    private DefaultListableBeanFactory beanFactory;

    public AnnotationConfigApplicationContext(Class... classes) {
        this();
        register(classes);
        refresh();
    }

    public AnnotationConfigApplicationContext(String... basePackage) {
        this();
        scan(basePackage);
        refresh();
    }

    public AnnotationConfigApplicationContext() {
        this.beanFactory = new DefaultListableBeanFactory();
        reader = new AnnotatedBeanDefinitionReader(beanFactory);
        scanner = new ClassPathBeanDefinitionScanner(beanFactory);
    }

    public void register(Class... classes) {
        reader.register(classes);
    }

    public void scan(String... basePackage) {
        scanner.scan(basePackage);
    }

    @Override
    public ConfigurableListableBeanFactory getBeanFactory() {
        return beanFactory;
    }

    @Override
    protected void refreshBeanFactory() {
    }
}
