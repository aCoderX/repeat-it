package com.acoderx.spring.web.servlet.config;

import com.acoderx.beans.factory.config.BeanDefinition;
import com.acoderx.beans.factory.support.RootBeanDefinition;
import com.acoderx.beans.factory.xml.BeanDefinitionParser;
import com.acoderx.beans.factory.xml.ParserContext;
import com.acoderx.spring.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import com.acoderx.spring.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.w3c.dom.Element;

/**
 * Description:解析xml配置<mvc:annotation-driven/>,自动注册mvc所需组件
 *
 * @author xudi
 * @since 2018-12-18
 */
public class AnnotationDrivenBeanDefinitionParser implements BeanDefinitionParser {
    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        parserContext.getRegistry().registerBeanDefinition(RequestMappingHandlerMapping.class.getName(), new RootBeanDefinition(RequestMappingHandlerMapping.class));
        parserContext.getRegistry().registerBeanDefinition(RequestMappingHandlerAdapter.class.getName(), new RootBeanDefinition(RequestMappingHandlerAdapter.class));
        return null;
    }
}
