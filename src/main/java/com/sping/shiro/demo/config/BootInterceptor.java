package com.sping.shiro.demo.config;

import java.util.Map;
 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
 
 
/**
 * 
* @ClassName: BootInterceptor
* @Description: 拦截器类
* @author darren
* @date 2019年2月20日
*
 */
public class BootInterceptor implements HandlerInterceptor {
   
	private static final Logger logger = LoggerFactory.getLogger(BootInterceptor.class);
	
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         * 对来自后台的请求统一进行日志处理
         */
        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        String uri = request.getRequestURI();
//        String queryString = request.getQueryString();
        Map<String, String[]>map=request.getParameterMap();   
        System.out.println("---------------------------------------------------------------------------------------------------");
        map.forEach((k,v) ->{
        	logger.info("请求参数-- "+k+": "+v[0]);
        });
        logger.info("url--"+url);
        logger.info("method--"+method);
        logger.info("uri--"+uri);
//        logger.info("请求参数-- "+queryString);
        System.out.println("---------------------------------------------------------------------------------------------------");
        return true;
    }
 
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    	
    }
 
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    
    }
}
