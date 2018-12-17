package com.acoderx.beans.factory.xml;

import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 *
 * @author xudi
 * @since 2018-12-17
 */
public abstract class NamespaceHandlerSupport implements NamespaceHandler {
    private final Map<String, BeanDefinitionParser> parsers = new HashMap<>();
    @Override
    public void parse(Element element, ParserContext parserContext) {
        this.parsers.get(element.getLocalName()).parse(element,parserContext);
    }

    protected void registerBeanDefinitionParser(String elementName, BeanDefinitionParser parser) {
        this.parsers.put(elementName, parser);
    }

}
