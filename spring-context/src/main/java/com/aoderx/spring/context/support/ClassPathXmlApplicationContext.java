package com.aoderx.spring.context.support;

import com.acoderx.beans.factory.config.ConfigurableListableBeanFactory;
import com.acoderx.beans.factory.config.RuntimeBeanReference;
import com.acoderx.beans.factory.config.TypedStringValue;
import com.acoderx.beans.factory.support.BeanDefinitionRegistry;
import com.acoderx.beans.factory.support.DefaultListableBeanFactory;
import com.acoderx.beans.factory.support.RootBeanDefinition;
import com.acoderx.repeat.spring.core.StringUtils;
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
 * Description:解析xml的ApplicationContext
 *
 * @author  xudi
 * @since  2018-11-16
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext {
    private DefaultListableBeanFactory beanFactory;
    private String[] configLocations;

    public ClassPathXmlApplicationContext(String... configLocations) {
        this.configLocations = configLocations;
        refresh();
    }

    @Override
    public ConfigurableListableBeanFactory getBeanFactory() {
        return beanFactory;
    }

    @Override
    protected void refreshBeanFactory() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        loadBeanDefinitions(beanFactory);
        if (this.beanFactory == null) {
            this.beanFactory = beanFactory;
        }
    }

    /**
     * 解析configLocations，解析为beanDefinition并注册
     * @param beanFactory
     */
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) {
        DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = df.newDocumentBuilder();
            for (String configLocation : configLocations) {
                Document document = db.parse(ClassLoader.getSystemResourceAsStream(configLocation));
                Element root = document.getDocumentElement();
                doRegisterBeanDefinition(root,beanFactory);
            }
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
                parseDefaultElement(element,registry);
            }

        }
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
