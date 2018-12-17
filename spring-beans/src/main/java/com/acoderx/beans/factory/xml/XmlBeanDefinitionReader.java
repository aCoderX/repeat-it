package com.acoderx.beans.factory.xml;

import com.acoderx.beans.factory.config.RuntimeBeanReference;
import com.acoderx.beans.factory.config.TypedStringValue;
import com.acoderx.beans.factory.support.BeanDefinitionReader;
import com.acoderx.beans.factory.support.BeanDefinitionRegistry;
import com.acoderx.beans.factory.support.RootBeanDefinition;
import com.acoderx.repeat.spring.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Description:将xml的config locations 解析成BeanDefinition并注册
 *
 * @author xudi
 * @since 2018-12-11
 */
public class XmlBeanDefinitionReader implements BeanDefinitionReader {
    private final BeanDefinitionRegistry registry;
    private NamespaceHandlerResolver namespaceHandlerResolver = new DefaultNamespaceHandlerResolver();

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void loadBeanDefinition(String location) {
        try {
            DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
            //需要开启对xml命名空间的支持
            df.setNamespaceAware(true);
            DocumentBuilder db = df.newDocumentBuilder();
            Document document = db.parse(ClassLoader.getSystemResourceAsStream(location));
            Element root = document.getDocumentElement();
            doRegisterBeanDefinition(root,registry);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    private void doRegisterBeanDefinition(Element root, BeanDefinitionRegistry registry) {
        NodeList nodeList = root.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node instanceof Element) {
                Element element = (Element) node;
                parseBeanDefinition(element,registry);
            }
        }
    }


    private void parseBeanDefinition(Element element, BeanDefinitionRegistry registry) {
        if (element.getNamespaceURI() != null) {
            parseCustomElement(element,registry);
        } else {
            parseDefaultElement(element,registry);
        }
    }

    private void parseCustomElement(Element element,BeanDefinitionRegistry registry) {
        //如果是自定义的标签，这里只做简单判断
        NamespaceHandler handler = namespaceHandlerResolver.resolve(element.getNamespaceURI());
        handler.parse(element, new ParserContext(registry));
    }

    /**
     * 解析默认的元素（beans、bean）
     * @param element
     * @param registry
     */
    private void parseDefaultElement(Element element,BeanDefinitionRegistry registry) {
        if ("bean".equals(element.getTagName())) {
            String id = element.getAttribute("id");
            String clazz = element.getAttribute("class");
            NodeList list = element.getChildNodes();
            Map<String, Object> props = new HashMap<>();
            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                if (node instanceof Element) {
                    Element e = (Element) node;
                    String nodeName = e.getNodeName();
                    //解析属性，依赖
                    if ("property".equals(nodeName)) {
                        String name = e.getAttribute("name");
                        String value = e.getAttribute("value");
                        String ref = e.getAttribute("ref");
                        if (StringUtils.isEmpty(name)) {
                            continue;
                        }
                        if (StringUtils.isNotEmpty(value)) {
                            props.put(name, new TypedStringValue(value));
                        } else if (StringUtils.isNotEmpty(ref)) {
                            props.put(name, new RuntimeBeanReference(ref));
                        }
                    }
                }
            }
            try {
                Class c = Class.forName(clazz);
                registry.registerBeanDefinition(id,new RootBeanDefinition(c,props));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else if("beans".equals(element.getTagName())){
            doRegisterBeanDefinition(element,registry);
        }

    }
}
