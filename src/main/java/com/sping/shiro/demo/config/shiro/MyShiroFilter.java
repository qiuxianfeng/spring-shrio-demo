package com.sping.shiro.demo.config.shiro;

import java.util.LinkedHashMap;
import java.util.Map;
 
import javax.servlet.Filter;
 
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 
/**
 * 
* @ClassName: MyShiroFilter
* @Description: shiro过滤器配置
* @author darren
* @date 2019年2月20日
*
 */
@Configuration
public class MyShiroFilter{
	
	
	private static final Logger log = LoggerFactory.getLogger(MyShiroFilter.class);
 
	@Bean
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		
		
	    Map<String,Filter> map = new LinkedHashMap<String,Filter>();	
		map.put("authc",getFormAuthenticationFilter());
		//权限控制map
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		//注意过滤器配置顺序 不能颠倒
        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了，登出后跳转配置的loginUrl
		filterChainDefinitionMap.put("/user/**", "anon");
		// 配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/static/**", "anon");
        //配置在最后面		
		filterChainDefinitionMap.put("/**", "authc");
		
		// 登录成功后要跳转的链接
//      shiroFilterFactoryBean.setSuccessUrl("/index");
      //未授权界面;
//      shiroFilterFactoryBean.setUnauthorizedUrl("/403");

		//登录的URL接口（Shiro可以进行识别）,没有登陆的用户只能访问登陆页面
		shiroFilterFactoryBean.setLoginUrl("/user/login");
		shiroFilterFactoryBean.setSecurityManager(securityManager);
        //这个map中包含了上面自定义的信息，配置到setFilter中
		shiroFilterFactoryBean.setFilters(map);
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		
		return shiroFilterFactoryBean;
 
	}
	
	/**开启shiro aop注解支持. 
     * 使用代理方式;所以需要开启代码支持;
	 * @param securityManager
	 * @return
	 */
	@Bean  
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {  
	    AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();  
	    authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);  
	    return authorizationAttributeSourceAdvisor;  
	} 
	
	
	@Bean
	FormAuthenticationFilterOverrite getFormAuthenticationFilter(){
		
		FormAuthenticationFilterOverrite authenticating = new FormAuthenticationFilterOverrite();
	
		return authenticating;
	}
}
