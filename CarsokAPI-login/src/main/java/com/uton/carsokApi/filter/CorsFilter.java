package com.uton.carsokApi.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by WANGYJ on 2017/7/6.
 * 针对vue可能出现的跨域进行处理
 */
//@WebFilter(urlPatterns = "/**", filterName = "CorsFilter")
public class CorsFilter implements Filter {

    private String corsdomain;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        corsdomain = filterConfig.getInitParameter("crossdomain");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
            HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

            // 跨域

            String origin = httpRequest.getHeader("Origin");
            if (origin == null) {
                httpResponse.addHeader("Access-Control-Allow-Origin", corsdomain);
            } else {
                httpResponse.addHeader("Access-Control-Allow-Origin", corsdomain);

            }
            httpResponse.addHeader("Access-Control-Allow-Headers", "Origin, x-requested-with, Content-Type, Accept,X-Cookie,token,subToken");
            httpResponse.addHeader("Access-Control-Allow-Credentials", "false");
            httpResponse.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,OPTIONS,DELETE");
            if ( httpRequest.getMethod().equals("OPTIONS") ) {
                httpResponse.setStatus(HttpServletResponse.SC_OK);
                return;
            }
            filterChain.doFilter(servletRequest, servletResponse);

        } catch (Exception e) {
            throw e;
        }
    }


    @Override
    public void destroy() {

    }
}
