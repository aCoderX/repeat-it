package com.aoderx.spring.context.stereotype;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description:标识被spring管理的类，会被AnnotationConfigApplicationContext扫描时获取
 *
 * @author  xudi
 * @since  2018-11-15
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Component {
}
