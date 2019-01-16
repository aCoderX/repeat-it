package com.acoderx.spring.web.servlet.config;

import com.acoderx.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Description:处理xml中mvc的命名空间
 *
 * @author xudi
 * @since 2018-12-18
 */
public class MvcNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        //自动注册mvc需要的组件
        registerBeanDefinitionParser("annotation-driven", new AnnotationDrivenBeanDefinitionParser());
    }
}
