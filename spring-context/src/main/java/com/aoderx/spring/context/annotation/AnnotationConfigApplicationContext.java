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
import com.aoderx.spring.context.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;

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

    public AnnotationConfigApplicationContext(String... basePackage) {
        this();
        scan(basePackage);
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
            registerBeanDefinition(aClass.getName(), beanDefinition);
        }
    }

    public void scan(String... basePackage) {
        for (String s : basePackage) {
            doScan(s);
        }
    }

    private void doScan(String s) {
        Set<Class> classes = getClasses(s);
        for (Class aClass : classes) {
            Annotation[] annotations = aClass.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation.annotationType().isAnnotationPresent(Component.class)) {
                    registerBeanDefinition(aClass.getName(), new AnnotatedBeanDefinition(aClass));
                }
            }
        }
    }

    private static Set<Class> getClasses(String pack) {
        Set<Class> classes = new LinkedHashSet<>();
        String resource = pack.replaceAll("\\.", "\\/");
        try {
            Enumeration<URL> urls = ClassLoader.getSystemResources(resource);
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if ("file".equals(url.getProtocol())) {
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    // 以文件的方式扫描整个包下的文件 并添加到集合中
                    findAndAddClassesInPackageByFile(pack, filePath, classes);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classes;
    }
    private static void findAndAddClassesInPackageByFile(String packageName, String packagePath, Set<Class> classes) {
        File dir = new File(packagePath);
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        File[] dirfiles = dir.listFiles(file -> file.isDirectory() || (file.getName().endsWith(".class")));
        if (null != dirfiles && dirfiles.length > 0) {
            for (File file : dirfiles) {
                // 如果是目录
                if (file.isDirectory()) {
                    findAndAddClassesInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), classes);
                } else {
                    String className = file.getName().substring(0, file.getName().length() - 6);
                    try {
                        classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
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
