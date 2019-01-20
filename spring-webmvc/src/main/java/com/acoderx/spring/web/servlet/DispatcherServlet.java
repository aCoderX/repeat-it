package com.acoderx.spring.web.servlet;

import com.acoderx.spring.context.ApplicationContext;
import com.acoderx.spring.web.context.WebApplicationContext;
import com.acoderx.spring.web.context.support.XmlWebApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @author xudi
 * @since 2018-12-12
 */
public class DispatcherServlet extends HttpServlet {
    //默认的配置文件后缀
    public static final String DEFAULT_CONFIG_SUFFIX = "-servlet";

    private List<HandlerMapping> handlerMappings = new ArrayList<>();

    @Override
    public void init() throws ServletException {
        initWebApplicationContext();
    }

    private void initWebApplicationContext() {
        XmlWebApplicationContext webApplicationContext = new XmlWebApplicationContext();

        //设置配置文件（spring在这使用了namespace设置，在XmlWebApplicationContext中回调）
        webApplicationContext.setConfigLocations(new String[]{
                "/WEB-INF" + getServletConfig().getServletName() + DEFAULT_CONFIG_SUFFIX
        });

        Object attribute = getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        if (attribute != null) {
            webApplicationContext.setParent(((ApplicationContext) attribute));
        }
        webApplicationContext.refresh();
        //初始化handlerMappings
        onRefresh(webApplicationContext);
    }

    private void onRefresh(ApplicationContext context) {
        initStrategies(context);
    }

    private void initStrategies(ApplicationContext context) {
        initHandlerMapping(context);
    }


    private void initHandlerMapping(ApplicationContext context) {
        String[] beanNamesForType = context.getBeanNamesForType(HandlerMapping.class);
        for (String s : beanNamesForType) {
            HandlerMapping bean = (HandlerMapping) context.getBean(s);
            if (bean != null) {
                handlerMappings.add(bean);
            }
        }
    }
}
