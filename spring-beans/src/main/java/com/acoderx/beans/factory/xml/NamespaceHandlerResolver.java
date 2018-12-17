package com.acoderx.beans.factory.xml;

/**
 * Description:
 *
 * @author xudi
 * @since 2018-12-14
 */
public interface NamespaceHandlerResolver {
    NamespaceHandler resolve(String namespaceUri);
}
