package com.aoderx.spring.context.annotation;

import com.acoderx.beans.factory.annotation.AnnotatedBeanDefinition;
import com.acoderx.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.acoderx.beans.factory.config.ConfigurableListableBeanFactory;
import com.acoderx.beans.factory.support.BeanNameGenerator;
import com.acoderx.beans.factory.support.DefaultBeanNameGenerator;
import com.acoderx.beans.factory.support.DefaultListableBeanFactory;
import com.acoderx.beans.factory.support.RootBeanDefinition;
import com.aoderx.spring.context.stereotype.Component;
import com.aoderx.spring.context.support.AbstractApplicationContext;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Description:
 *
 * @author: xudi
 * @since: 2018-11-09
 */
public class AnnotationConfigApplicationContext extends AbstractApplicationContext {

    private DefaultListableBeanFactory beanFactory;

    private BeanNameGenerator beanNameGenerator = new DefaultBeanNameGenerator();


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
        beanFactory.registerBeanDefinition(AutowiredAnnotationBeanPostProcessor.class.getName(),
                new RootBeanDefinition(AutowiredAnnotationBeanPostProcessor.class));
    }

    public void register(Class... classes) {
        for (Class aClass : classes) {
            AnnotatedBeanDefinition beanDefinition = new AnnotatedBeanDefinition(aClass);
            this.beanFactory.registerBeanDefinition(beanNameGenerator.generateBeanName(beanDefinition), beanDefinition);
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
            if(aClass.isInterface()){
                return;
            }
            Annotation[] annotations = aClass.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation.annotationType().equals(Component.class)||annotation.annotationType().isAnnotationPresent(Component.class)) {
                    AnnotatedBeanDefinition beanDefinition = new AnnotatedBeanDefinition(aClass);
                    this.beanFactory.registerBeanDefinition(beanNameGenerator.generateBeanName(beanDefinition), beanDefinition);
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

    @Override
    public ConfigurableListableBeanFactory getBeanFactory() {
        return beanFactory;
    }

    @Override
    protected void refreshBeanFactory() {
    }
}
