package com.acoderx.beans.factory.support;

import com.acoderx.beans.factory.Aware;
import com.acoderx.beans.factory.BeanFactory;
import com.acoderx.beans.factory.BeanFactoryAware;
import com.acoderx.beans.factory.InitializingBean;
import com.acoderx.beans.factory.config.*;
import com.acoderx.repeat.spring.util.ArrayUtils;

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
    private BeanFactory parentBeanFactory;

    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    private List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    @Override
    public <T> T getBean(Class<T> testBeanClass) {
        String[] beanName = getBeanNamesForType(testBeanClass);
        if (beanName.length > 0) {
            return (T) getBean(beanName[0]);
        }
        throw new RuntimeException(testBeanClass.getName()+" can't found");
    }

    @Override
    public Object getBean(String name) {
        Object parentBean = getParentBeanFactory().getBean(name);
        if (parentBean != null) {
            return parentBean;
        }
        BeanDefinition beanDefinition = beanDefinitionMap.get(name);
        Object bean;
        if (beanDefinition.isSingleton()) {
            bean = getSingleton(name);
            if (bean == null) {
                bean = doCreateBean(beanDefinition);
                registerSingleton(name, bean);
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
        if (o instanceof InitializingBean) {
            ((InitializingBean) o).afterPropertiesSet();
        }
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
        String[] beanNamesForType = getParentBeanFactory().getBeanNamesForType(type);
        List<String> names = new ArrayList<>();
        beanDefinitionMap.forEach((k, v) -> {
            if (type.isAssignableFrom(v.getBeanClass())) {
                names.add(k);
            }
        });
        String[] s = new String[names.size()];
        return ArrayUtils.concat(s,beanNamesForType);
    }

    @Override
    public Class<?> getType(String name) {
        Class<?> type = getParentBeanFactory().getType(name);
        if (type != null) {
            return type;
        }
        BeanDefinition beanDefinition = beanDefinitionMap.get(name);
        return beanDefinition.getBeanClass();
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

    @Override
    public BeanFactory getParentBeanFactory() {
        return parentBeanFactory;
    }

    public void setParentBeanFactory(BeanFactory parentBeanFactory) {
        this.parentBeanFactory = parentBeanFactory;
    }
}
