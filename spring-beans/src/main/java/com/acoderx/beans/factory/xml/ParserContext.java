package com.acoderx.beans.factory.xml;

import com.acoderx.beans.factory.support.BeanDefinitionRegistry;

/**
 * Description:解析自定义xml的上下文
 *
 * @author xudi
 * @since 2018-12-17
 */
public class ParserContext {
    private final BeanDefinitionRegistry registry;

    public ParserContext(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public BeanDefinitionRegistry getRegistry() {
        return registry;
    }

}
