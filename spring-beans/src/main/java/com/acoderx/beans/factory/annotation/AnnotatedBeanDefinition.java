package com.acoderx.beans.factory.annotation;

import com.acoderx.beans.factory.support.AbstractBeanDefinition;

import java.lang.annotation.Annotation;

/**
 * Description:注解bean的定义
 *
 * @author  xudi
 * @since  2018-11-13
 */
public class AnnotatedBeanDefinition extends AbstractBeanDefinition {
    private Annotation[] annotations;

    public AnnotatedBeanDefinition(Class aClass) {
        super(aClass);
        annotations = aClass.getAnnotations();
    }

    public Annotation[] getAnnotations() {
        return annotations;
    }
}
