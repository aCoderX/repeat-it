package com.aoderx.spring.context.support;

import com.acoderx.beans.factory.BeanFactory;
import com.acoderx.beans.factory.config.BeanFactoryPostProcessor;
import com.acoderx.beans.factory.support.BeanDefinitionRegistry;
import com.acoderx.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import com.aoderx.spring.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @author: xudi
 * @since: 2018-11-19
 */
public abstract class AbstractApplicationContext implements ApplicationContext, BeanFactory{

    private List<BeanFactoryPostProcessor> beanFactoryPostProcessors = new ArrayList<>();

    public abstract BeanFactory getBeanFactory();

    protected abstract void refreshBeanFactory();

    public List<BeanFactoryPostProcessor> getBeanFactoryPostProcessors() {
        return beanFactoryPostProcessors;
    }

    @Override
    public void refresh() {
        //解析，加载

        //获取beanFactory
        BeanFactory beanFactory = obtainFreshBeanFactory();

        //调用BeanFactory的后置处理器
        invokeBeanFactoryPostProcessors(beanFactory);

        //初始化


    }

    protected BeanFactory obtainFreshBeanFactory() {
        refreshBeanFactory();
        return getBeanFactory();
    }

    protected void invokeBeanFactoryPostProcessors(BeanFactory beanFactory) {
        if (beanFactory instanceof BeanDefinitionRegistry) {
            BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
            //先调用BeanDefinitionRegistryPostProcessor
            for (BeanFactoryPostProcessor beanFactoryPostProcessor : getBeanFactoryPostProcessors()) {
                if (beanFactoryPostProcessor instanceof BeanDefinitionRegistryPostProcessor) {
                    ((BeanDefinitionRegistryPostProcessor) beanFactoryPostProcessor).postProcessBeanDefinitionRegistry(registry);
                }
            }
            List<BeanDefinitionRegistryPostProcessor> currentRegistryProcessors = new ArrayList<>();
            String[] postProcessorNames = beanFactory.getBeanNamesForType(BeanDefinitionRegistryPostProcessor.class);
            for (String postProcessorName : postProcessorNames) {
                BeanDefinitionRegistryPostProcessor postProcessor = (BeanDefinitionRegistryPostProcessor) beanFactory.getBean(postProcessorName);
                postProcessor.postProcessBeanDefinitionRegistry(registry);
                currentRegistryProcessors.add(postProcessor);
            }
            //后调用BeanFactoryPostProcessor
            for (BeanFactoryPostProcessor beanFactoryPostProcessor : getBeanFactoryPostProcessors()) {
                beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
            }
            for (BeanDefinitionRegistryPostProcessor currentRegistryProcessor : currentRegistryProcessors) {
                currentRegistryProcessor.postProcessBeanDefinitionRegistry(registry);
            }
        } else {
            for (BeanFactoryPostProcessor beanFactoryPostProcessor : getBeanFactoryPostProcessors()) {
                beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
            }
        }
    }

    @Override
    public <T> T getBean(Class<T> testBeanClass) {
        return getBeanFactory().getBean(testBeanClass);
    }

    @Override
    public Object getBean(String name) {
        return getBeanFactory().getBean(name);
    }

    @Override
    public String[] getBeanNamesForType(Class type) {
        return getBeanFactory().getBeanNamesForType(type);
    }

}
