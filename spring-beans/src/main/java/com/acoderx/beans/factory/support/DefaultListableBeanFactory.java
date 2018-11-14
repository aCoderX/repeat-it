package com.acoderx.beans.factory.support;

import com.acoderx.beans.factory.BeanFactory;
import com.acoderx.beans.factory.config.BeanDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description:
 *
 * @author: xudi
 * @since: 2018-11-12
 */
public class DefaultListableBeanFactory implements BeanFactory, BeanDefinitionRegistry {

    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    @Override
    public <T> T getBean(Class<T> testBeanClass) {
        String[] beanName = getBeanNamesForType(testBeanClass);
        return (T) getBean(beanName[0]);
    }

    @Override
    public Object getBean(String name) {
        return doCreateBean(beanDefinitionMap.get(name));
    }

    private Object doCreateBean(BeanDefinition beanDefinition) {
        //实例化
        Object o = null;
        try {
            o = beanDefinition.getBeanClass().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        //填充属性

        //初始化


        return o;
    }



    @Override
    public String[] getBeanNamesForType(Class type) {

        List<String> names = new ArrayList<>();
        beanDefinitionMap.forEach((k, v) -> {
            if (type.isAssignableFrom(v.getBeanClass())) {
                names.add(k);
            }
        });
        String[] s = new String[names.size()];
        names.toArray(s);
        return s;
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        return beanDefinitionMap.get(beanName);
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        Set<String> c = beanDefinitionMap.keySet();
        String[] s = new String[c.size()];
        c.toArray(s);
        return s;
    }
}
