package com.acoderx.spring.web.servlet;

import com.acoderx.spring.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Description:
 *
 * @author xudi
 * @since 2019-01-15
 */
public interface HandlerMapping {
    /**
     * 获取请求的处理器
     * @param request
     * @return
     */
     HandlerMethod getHandler(HttpServletRequest request);
}
