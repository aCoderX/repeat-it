package com.acoderx.beans.factory.annotation;

import com.acoderx.beans.factory.config.BeanDefinition;

import java.lang.annotation.Annotation;

/**
 * Description:
 *
 * @author: xudi
 * @since: 2018-11-13
 */
public class AnnotatedBeanDefinition implements BeanDefinition {
    private Class aClass;
    private Annotation[] annotations;

    public AnnotatedBeanDefinition(Class aClass) {
        this.aClass = aClass;
        annotations = aClass.getAnnotations();
    }

    @Override
    public Class getBeanClass() {
        return aClass;
    }

    public Annotation[] getAnnotations() {
        return annotations;
    }
}
