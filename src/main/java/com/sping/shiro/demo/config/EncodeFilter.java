package com.sping.shiro.demo.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
 
/**
 * 
* @ClassName: EncodeFilter
* @Description: 编码 过滤器
* @author darren
* @date 2019年2月20日
*
 */
@Component
public class EncodeFilter implements Filter{
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}
 
    /**
     * 设置编码为UTF-8
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
 
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        System.out.println("EncodeFilter");
        
        //过滤结束，继续执行    没有这一行，程序不会继续向下执行
        chain.doFilter(req, res); 
        
    }
     
    @Override
    public void destroy() {}
 
}
