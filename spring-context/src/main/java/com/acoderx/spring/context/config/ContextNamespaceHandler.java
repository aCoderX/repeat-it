package com.acoderx.spring.context.config;

import com.acoderx.beans.factory.xml.NamespaceHandlerSupport;
import com.acoderx.spring.context.annotation.ComponentScanBeanDefinitionParser;

/**
 * Description:用来处理配置文件（application.xml）中自定义的namespace context
 *
 * @author xudi
 * @since 2018-12-17
 */
public class ContextNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("component-scan", new ComponentScanBeanDefinitionParser());
    }
}
