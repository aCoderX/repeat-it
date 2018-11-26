package com.acoderx.beans.factory.support;

import com.acoderx.beans.factory.Aware;
import com.acoderx.beans.factory.BeanFactoryAware;
import com.acoderx.beans.factory.config.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description:bean工厂默认实现，主要的Bean的处理都由它完成
 *
 * @author  xudi
 * @since  2018-11-12
 */
public class DefaultListableBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableListableBeanFactory, BeanDefinitionRegistry {

    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    private List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    @Override
    public <T> T getBean(Class<T> testBeanClass) {
        String[] beanName = getBeanNamesForType(testBeanClass);
        return (T) getBean(beanName[0]);
    }

    @Override
    public Object getBean(String name) {
        BeanDefinition beanDefinition = beanDefinitionMap.get(name);
        Object bean;
        if (beanDefinition.isSingleton()) {
            bean = getSingleton(name);
            if (bean==null) {
                bean = doCreateBean(beanDefinition);
                registerSingleton(name,bean);
            }
        } else {
            bean = doCreateBean(beanDefinition);
        }
        return bean;
    }

    private Object doCreateBean(BeanDefinition beanDefinition) {
        //实例化
        Object o = createBeanInstance(beanDefinition);

        //填充属性
        populateBean(o,beanDefinition);

        //初始化
        initializeBean(o, beanDefinition);

        return o;
    }

    private void initializeBean(Object o, BeanDefinition beanDefinition) {
        invokeAwareMethods(o, beanDefinition);
    }

    private void invokeAwareMethods(Object o, BeanDefinition beanDefinition) {
        if (o instanceof Aware) {
            if (o instanceof BeanFactoryAware) {
                ((BeanFactoryAware) o).setBeanFactory(this);
            }
        }
    }

    private Object createBeanInstance(BeanDefinition beanDefinition) {
        Object o = null;
        try {
            if (beanDefinition.getFactoryMethodName() != null) {
                //如果是通过方法创建的
                String factoryMethodName = beanDefinition.getFactoryMethodName();
                Method method = beanDefinition.getFactoryMethod();
                method.setAccessible(true);
                return method.invoke(getBean(factoryMethodName));
            }
            Constructor constructor = beanDefinition.getBeanClass().getDeclaredConstructor();
            constructor.setAccessible(true);
            o = constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return o;
    }


    private void populateBean(Object o, BeanDefinition beanDefinition) {

        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                ((InstantiationAwareBeanPostProcessor) beanPostProcessor).postProcessPropertyValues(o, beanDefinition);
            }
        }
        Map<String,Object> props = beanDefinition.getPropertyValues();
        if (props != null) {
            //转换props，转成bean
            applyPropertyValues(beanDefinition.getPropertyValues());
            //设置属性
            props.forEach((k,v)->{
                try {
                    Field field = o.getClass().getDeclaredField(k);
                    field.setAccessible(true);
                    field.set(o, v);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private void applyPropertyValues(Map<String,Object> props) {
        props.forEach((name,value)->{
            if(value instanceof RuntimeBeanReference){
                props.put(name, getBean(((RuntimeBeanReference) value).getBeanName()));
            } else if (value instanceof TypedStringValue) {
                props.put(name,((TypedStringValue) value).getValue());
            }
        });
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
    public void preInstantiateSingletons() {
        beanDefinitionMap.forEach((k,v)->{
            if (v.isSingleton()) {
                getBean(k);
            }
        });
    }

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        beanPostProcessors.add(beanPostProcessor);
    }

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return beanPostProcessors;
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
