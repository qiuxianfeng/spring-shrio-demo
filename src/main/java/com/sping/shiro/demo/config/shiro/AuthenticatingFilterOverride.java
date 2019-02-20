package com.sping.shiro.demo.config.shiro;
 
import java.io.IOException;
import java.io.PrintWriter;
 
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
 
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
import com.alibaba.fastjson.JSONObject;

/**
 * 
* @ClassName: AuthenticatingFilterOverride
* @Description: 验证用户登陆
* @author darren
* @date 2019年2月20日
*
 */
public class AuthenticatingFilterOverride extends AuthenticatingFilter{
 
 
    private static final Logger log = LoggerFactory.getLogger(AuthenticatingFilterOverride.class);
 
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
    	log.info("--------executeLogin---------");
    	FormAuthenticationFilterOverrite formAuthen = new FormAuthenticationFilterOverrite();
        AuthenticationToken token = formAuthen.createToken(request, response);
        if (token == null) {
            String msg = "createToken method implementation returned null. A valid non-null AuthenticationToken " +
                    "must be created in order to execute a login attempt.";
            System.out.println("*******"+msg);
            throw new IllegalStateException(msg);
        }
        try {
        	log.info("----------我进来进行核对了信息----------------");
            
        	Subject subject = getSubject(request, response);
            
        	subject.login(token);
            
            return this.onLoginSuccess(token, subject, request, response);
        } catch (AuthenticationException e) {
        	System.out.println("-----onLoginFailure;---------");
        	e.printStackTrace();
            return this.onLoginFailure(token, e, request, response);
        }
    }
 
	
	@Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
		log.info("AuthenticatingFilterOverride--------onLoginSuccess------");
		response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        return true;
    }
 
	
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
    	log.info("AuthenticatingFilterOverride--------onLoginFailure------");
    	response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        JSONObject json = new JSONObject();       
        String exc = e.getClass().getName();          
        if(exc.equals(UnknownAccountException.class.getName())){
        	json.put("fail", "账户不存在");
        }
        if(exc.equals(IncorrectCredentialsException.class.getName())){
        	System.out.println("=========");
        	json.put("fail", "密码不正确");
        }
        out.println(json);
        out.flush();
        out.close();
        return false;
    }
 
	/* （非 Javadoc）
	 * @see org.apache.shiro.web.filter.authc.AuthenticatingFilter#createToken(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
	 */
	@Override
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
		// TODO 自动生成的方法存根
		
		return null;
	}
 
	/* （非 Javadoc）
	 * @see org.apache.shiro.web.filter.AccessControlFilter#onAccessDenied(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		// TODO 自动生成的方法存根
		
		return false;
	}
	
 
}
