package com.acoderx.beans.factory.xml;

import com.acoderx.beans.factory.config.BeanDefinition;
import org.w3c.dom.Element;


/**
 * Description:xml自定义bean的解析器
 *
 * @author xudi
 * @since 2018-12-17
 */
public interface BeanDefinitionParser {
    BeanDefinition parse(Element element, ParserContext parserContext);
}
