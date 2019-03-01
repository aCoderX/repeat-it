package com.acoderx.spring.web.servlet.mvc.method.annotation;

import com.acoderx.beans.factory.BeanFactory;
import com.acoderx.beans.factory.BeanFactoryAware;
import com.acoderx.beans.factory.InitializingBean;
import com.acoderx.spring.web.bind.annotation.RequestMapping;
import com.acoderx.spring.web.method.HandlerMethod;
import com.acoderx.spring.web.servlet.HandlerMapping;
import com.acoderx.spring.web.servlet.mvc.method.RequestMappingInfo;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Description:
 *
 * @author xudi
 * @since 2019-01-16
 */
public class RequestMappingHandlerMapping implements HandlerMapping , InitializingBean , BeanFactoryAware {
    private Map<RequestMappingInfo, HandlerMethod> mappingRegistry = new LinkedHashMap<>();
    private BeanFactory beanFactory;
    @Override
    public HandlerMethod getHandler(HttpServletRequest request) {
        //Spring在这优先完全匹配，之后尝试占位符匹配
        for (RequestMappingInfo requestMappingInfo : mappingRegistry.keySet()) {
            if (requestMappingInfo.match(request)) {
                return mappingRegistry.get(requestMappingInfo);
            }
        }
        return null;
    }

    /**
     * 加载handlerMethod
     */
    @Override
    public void afterPropertiesSet() {
        String[] names = beanFactory.getBeanNamesForType(Object.class);
        for (String name : names) {
            Class c = beanFactory.getType(name);
            if (isHandler(c)) {
                detectHandlerMethods(name);
            }
        }
    }

    /**
     * 解析bean得到handlerMethod
     * @param beanName
     */
    private void detectHandlerMethods(String beanName) {
        Class<?> type = beanFactory.getType(beanName);
        Method[] methods = type.getMethods();
        for (Method method : methods) {
            RequestMapping annotation = method.getAnnotation(RequestMapping.class);
            if (annotation != null) {
                HandlerMethod handlerMethod = new HandlerMethod();
                handlerMethod.setMethod(method);
                handlerMethod.setBean(beanFactory.getBean(type));
                mappingRegistry.put(new RequestMappingInfo(annotation.method(),annotation.value()), handlerMethod);
            }
        }
    }

    /**
     * 判断是否需要解析成映射
     * 目前逻辑为是否为RequestMapping
     * @param type
     * @return
     */
    private boolean isHandler(Class type) {
        if (type.getAnnotation(RequestMapping.class) != null) {
            return true;
        }
        return false;

    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
}
