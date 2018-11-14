package com.aoderx.spring.context.annotation;

import com.acoderx.beans.factory.*;
import com.acoderx.beans.factory.annotation.AnnotatedBeanDefinition;
import com.acoderx.beans.factory.config.BeanDefinition;
import com.acoderx.beans.factory.config.BeanFactoryPostProcessor;
import com.acoderx.beans.factory.support.BeanDefinitionRegistry;
import com.acoderx.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import com.acoderx.beans.factory.support.DefaultListableBeanFactory;
import com.acoderx.beans.factory.support.RootBeanDefinition;
import com.aoderx.spring.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @author: xudi
 * @since: 2018-11-09
 */
public class AnnotationConfigApplicationContext implements ApplicationContext, BeanFactory, BeanDefinitionRegistry {

    private DefaultListableBeanFactory beanFactory;

    private List<BeanFactoryPostProcessor> beanFactoryPostProcessors = new ArrayList<>();

    public AnnotationConfigApplicationContext(Class... classes) {
        this();
        register(classes);
        refresh();
    }


    public AnnotationConfigApplicationContext() {
        this.beanFactory = new DefaultListableBeanFactory();
        registerAnnotationConfigProcessors();
    }


    private void registerAnnotationConfigProcessors() {
        beanFactory.registerBeanDefinition(ConfigurationClassPostProcessor.class.getName(),
                new RootBeanDefinition(ConfigurationClassPostProcessor.class));
    }

    public void register(Class... classes) {
        for (Class aClass : classes) {
            AnnotatedBeanDefinition beanDefinition = new AnnotatedBeanDefinition(aClass);
            this.registerBeanDefinition(aClass.getName(), beanDefinition);
        }
    }


    public void refresh() {
        //调用BeanFactory的后置处理器
        //先调用BeanDefinitionRegistryPostProcessor
        for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessors) {
            if (beanFactoryPostProcessor instanceof BeanDefinitionRegistryPostProcessor) {
                ((BeanDefinitionRegistryPostProcessor) beanFactoryPostProcessor).postProcessBeanDefinitionRegistry(beanFactory);
            }
        }
        List<BeanDefinitionRegistryPostProcessor> currentRegistryProcessors = new ArrayList<>();
        String[] postProcessorNames = beanFactory.getBeanNamesForType(BeanDefinitionRegistryPostProcessor.class);
        for (String postProcessorName : postProcessorNames) {
            BeanDefinitionRegistryPostProcessor postProcessor = (BeanDefinitionRegistryPostProcessor) beanFactory.getBean(postProcessorName);
            postProcessor.postProcessBeanDefinitionRegistry(beanFactory);
            currentRegistryProcessors.add(postProcessor);
        }
        //后调用BeanFactoryPostProcessor
        for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessors) {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }
        for (BeanDefinitionRegistryPostProcessor currentRegistryProcessor : currentRegistryProcessors) {
            currentRegistryProcessor.postProcessBeanDefinitionRegistry(beanFactory);
        }

        //

        //解析，加载




        //初始化


    }



    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        return beanFactory.getBeanDefinition(beanName);
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanFactory.registerBeanDefinition(beanName, beanDefinition);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return beanFactory.getBeanDefinitionNames();
    }

    @Override
    public <T> T getBean(Class<T> testBeanClass) {
        return beanFactory.getBean(testBeanClass);
    }

    @Override
    public Object getBean(String name) {
        return beanFactory.getBean(name);
    }

    @Override
    public String[] getBeanNamesForType(Class type) {
        return beanFactory.getBeanNamesForType(type);
    }
}
