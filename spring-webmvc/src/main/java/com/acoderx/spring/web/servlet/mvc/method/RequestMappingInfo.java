package com.acoderx.spring.web.servlet.mvc.method;

import com.acoderx.spring.http.HttpMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * Description:请求的信息
 *
 * @author xudi
 * @since 2019-02-28
 */
public class RequestMappingInfo {
    private HttpMethod httpMethod;
    private String url;

    public boolean match(HttpServletRequest request) {
        String servletPath = request.getServletPath();
        if (servletPath.equals(url) && httpMethod.matches(request.getMethod())) {
            return true;
        }
        return false;
    }
    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public RequestMappingInfo() {
    }

    public RequestMappingInfo(HttpMethod httpMethod, String url) {
        this.httpMethod = httpMethod;
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RequestMappingInfo that = (RequestMappingInfo) o;
        return httpMethod == that.httpMethod &&
                Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(httpMethod, url);
    }
}
