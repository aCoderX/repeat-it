package com.aoderx.spring.context.support;

import com.acoderx.beans.factory.config.BeanFactoryPostProcessor;
import com.acoderx.beans.factory.config.BeanPostProcessor;
import com.acoderx.beans.factory.config.ConfigurableListableBeanFactory;
import com.acoderx.beans.factory.support.BeanDefinitionRegistry;
import com.acoderx.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import com.aoderx.spring.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @author  xudi
 * @since  2018-11-19
 */
public abstract class AbstractApplicationContext implements ApplicationContext{

    private List<BeanFactoryPostProcessor> beanFactoryPostProcessors = new ArrayList<>();

    public abstract ConfigurableListableBeanFactory getBeanFactory();

    protected abstract void refreshBeanFactory();

    public List<BeanFactoryPostProcessor> getBeanFactoryPostProcessors() {
        return beanFactoryPostProcessors;
    }

    @Override
    public void refresh() {
        //解析，加载

        //获取beanFactory
        ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();

        //调用BeanFactory的后置处理器
        invokeBeanFactoryPostProcessors(beanFactory);

        //注册BeanPostProcessor
        registerBeanPostProcessors(beanFactory);

        //初始化
        finishBeanFactoryInitialization(beanFactory);

    }

    protected void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory){
        String[] postProcessorNames = beanFactory.getBeanNamesForType(BeanPostProcessor.class);
        for (String postProcessorName : postProcessorNames) {
            beanFactory.addBeanPostProcessor((BeanPostProcessor) beanFactory.getBean(postProcessorName));
        }
    }

    protected void finishBeanFactoryInitialization(ConfigurableListableBeanFactory beanFactory){
        beanFactory.preInstantiateSingletons();
    }

    protected ConfigurableListableBeanFactory obtainFreshBeanFactory() {
        refreshBeanFactory();
        return getBeanFactory();
    }

    protected void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
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
