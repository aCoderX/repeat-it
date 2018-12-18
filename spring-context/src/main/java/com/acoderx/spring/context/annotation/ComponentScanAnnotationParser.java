package com.acoderx.spring.context.annotation;

import com.acoderx.beans.factory.support.BeanDefinitionRegistry;

/**
 * Description:解析@ComponentScan注解
 *
 * @author xudi
 * @since 2018-12-18
 */
public class ComponentScanAnnotationParser {

    public void parse(BeanDefinitionRegistry registry) {
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry);
        scanner.scan("");
    }
}
