package com.acoderx.spring.web.servlet.mvc.method.annotation;

import com.acoderx.spring.web.method.HandlerMethod;
import com.acoderx.spring.web.servlet.HandlerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Description:
 *
 * @author xudi
 * @since 2019-01-22
 */
public class RequestMappingHandlerAdapter implements HandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        return handler instanceof HandlerMethod;
    }

    @Override
    public void handler(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Method method = ((HandlerMethod) handler).getMethod();
        Object bean = ((HandlerMethod) handler).getBean();

        //TODO 参数解析、返回值解析
        //参数处理
        //使用resolver解析，并传入dataBinderFactory
        // resolver中包含 httpMessageConverter,而DataBinderFactory中包含converter和formatter

        Object result;
        try {
            //调用方法
            result = method.invoke(bean);
            //返回值处理
            PrintWriter writer = response.getWriter();
            writer.write(result.toString());
            writer.flush();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
