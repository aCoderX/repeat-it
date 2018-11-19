package com.aoderx.spring.context.support;

import com.acoderx.beans.factory.BeanFactory;
import com.acoderx.beans.factory.support.BeanDefinitionRegistry;
import com.acoderx.beans.factory.support.DefaultListableBeanFactory;
import com.acoderx.beans.factory.support.RootBeanDefinition;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Description:
 *
 * @author: xudi
 * @since: 2018-11-16
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext {
    private DefaultListableBeanFactory beanFactory;
    private String[] configLocations;

    public ClassPathXmlApplicationContext(String... configLocations) {
        this.configLocations = configLocations;
        refresh();
    }

    @Override
    public BeanFactory getBeanFactory() {
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

    private void parseDefaultElement(Element element,BeanDefinitionRegistry registry) {
        if ("bean".equals(element.getTagName())) {
            String id = element.getAttribute("id");
            String clazz = element.getAttribute("class");
            try {
                Class c = Class.forName(clazz);
                registry.registerBeanDefinition(id,new RootBeanDefinition(c));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else if("beans".equals(element.getTagName())){
            doRegisterBeanDefinition(element,registry);
        }

    }
}
