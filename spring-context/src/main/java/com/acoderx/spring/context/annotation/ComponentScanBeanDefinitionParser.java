package com.acoderx.spring.context.annotation;

import com.acoderx.beans.factory.config.BeanDefinition;
import com.acoderx.beans.factory.xml.BeanDefinitionParser;
import com.acoderx.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;


/**
 * Description:解析xml中component-scan配置，自动扫描bean，并注册
 *
 * @author xudi
 * @since 2018-12-17
 */
public class ComponentScanBeanDefinitionParser implements BeanDefinitionParser {

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(parserContext.getRegistry());
        scanner.scan("");
        return null;
    }
}
