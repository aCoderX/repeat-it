package com.acoderx.spring.web.servlet;

import com.acoderx.spring.context.ApplicationContext;
import com.acoderx.spring.web.context.WebApplicationContext;
import com.acoderx.spring.web.context.support.XmlWebApplicationContext;
import com.acoderx.spring.web.method.HandlerMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
    private List<HandlerAdapter> handlerAdapters = new ArrayList<>();

    @Override
    public void init() throws ServletException {
        initWebApplicationContext();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doDispatch(req,resp);
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) {
        //获取handlerMethod
        Object handler = getHandler(req);
        if (handler == null) {
            return;
        }
        //获取handle
        HandlerAdapter ha = getHandlerAdapter(handler);
        if (ha == null) {
            return;
        }
        //执行方法
        ha.handler(req,resp,handler);
    }

    private HandlerAdapter getHandlerAdapter(Object handler) {
        for (HandlerAdapter handlerAdapter : handlerAdapters) {
            if (handlerAdapter.supports(handler)) {
                return handlerAdapter;
            }
        }
        return null;
    }

    private Object getHandler(HttpServletRequest req) {
        for (HandlerMapping handlerMapping : handlerMappings) {
            HandlerMethod handler = handlerMapping.getHandler(req);
            if (handler != null) {
                return handler;
            }
        }
        return null;
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
        //初始化handlerMapping
        initHandlerMapping(context);
        //初始化handlerAdapter
        initHandlerAdapter(context);
    }

    private void initHandlerAdapter(ApplicationContext context) {
        String[] beanNamesForType = context.getBeanNamesForType(HandlerAdapter.class);
        for (String s : beanNamesForType) {
            HandlerAdapter bean = (HandlerAdapter) context.getBean(s);
            if (bean != null) {
                handlerAdapters.add(bean);
            }
        }
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
