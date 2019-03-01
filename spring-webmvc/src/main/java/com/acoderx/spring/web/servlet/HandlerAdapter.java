package com.acoderx.spring.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description:
 *
 * @author xudi
 * @since 2019-01-21
 */
public interface HandlerAdapter {
    /**
     * 判断改adapter是否可以处理某种handler
     * @param handler
     * @return
     */
    boolean supports(Object handler);

    /**
     * 用handler处理请求
     * @param request
     * @param response
     * @param handler
     */
    void handler(HttpServletRequest request, HttpServletResponse response, Object handler);
}
