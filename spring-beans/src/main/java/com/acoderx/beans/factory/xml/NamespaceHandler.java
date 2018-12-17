package com.acoderx.beans.factory.xml;


import org.w3c.dom.Element;

/**
 * Description:用于xml命名空间处理，如context
 *
 * @author xudi
 * @since 2018-12-14
 */
public interface NamespaceHandler {
    void init();

    void parse(Element element, ParserContext parserContext);

}
