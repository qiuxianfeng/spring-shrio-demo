package com.sping.shiro.demo.config.shiro;
 
import java.io.PrintWriter;
 
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
 
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
import com.alibaba.fastjson.JSONObject;
 
/**
 * 
* @ClassName: FormAuthenticationFilterOverrite
* @Description: 登录界面进行登录操作时触发
* @author darren
* @date 2019年2月20日
*
 */
public class FormAuthenticationFilterOverrite extends FormAuthenticationFilter{
	
	
	private static final Logger log = LoggerFactory.getLogger(FormAuthenticationFilterOverrite.class);
	
 
	/*
	 *  重写时注意事项：
	 *      1，没有session。调用FormAuthenticationFilter.onAccessDeny()方法。
	 *      2，没有session，但是是LoginURL。调用AuthenticatingFilter.executeLogin()
	 *                   认证成功，调用 AuthenticatingFilter中 onLoginSuccess(token, subject, request, response);
	 *                   认证失败，调用 AuthenticatingFilter中 onLoginFailure(token, e, request, response);
	 *     						  在认证之前又开始进行了Token认证，所以要重写 createToken方法。
	 *     
	 * 
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
	        if (this.isLoginRequest(request, response)) {
	        	log.info("--------------isLoginRequest--------------");
	            if (this.isLoginSubmission(request, response)) {
	            	log.info("--------------isLoginSubmission--------------");
	                if (log.isTraceEnabled()) {
	                    log.trace("Login submission detected.  Attempting to execute login.");
	                }
	                AuthenticatingFilterOverride ao = new AuthenticatingFilterOverride();                
	                return ao.executeLogin(request, response);
	            } else {
	                if (log.isTraceEnabled()) {
	                    log.trace("Login page view.");
	                }
	                return true;
	            }
	        } else {
	            if (log.isTraceEnabled()) {
	                log.trace("Attempting to access a path which requires authentication.  Forwarding to the Authentication url [" + this.getLoginUrl() + "]");
	            }
	            
	            response.setContentType("application/json");
	            response.setCharacterEncoding("UTF-8");
	            PrintWriter out = response.getWriter();    
	            JSONObject json = new JSONObject();
	            json.put("no-session", "未登录，无法访问该地址");
	            out.println(json);
	            out.flush();
	            out.close();
	            return false;
	        }
	    }
	
	
	@Override
	public AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
	        String username = getUsername(request);
	        String password = getPassword(request);
	        return createToken(username, password, request, response);
	    }
 
}
