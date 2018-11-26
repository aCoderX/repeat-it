package com.aoderx.spring.context.annotation;

import com.aoderx.spring.context.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description:配置Bean，会被ConfigurationClassPostProcessor扫描并解析
 *
 * @author  xudi
 * @since  2018-11-13
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface Configuration {
}
