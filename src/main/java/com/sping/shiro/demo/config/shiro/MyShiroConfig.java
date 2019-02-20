package com.sping.shiro.demo.config.shiro;
 
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sping.shiro.demo.shiro.MyShiroRealmService;
 
/**
 * 
* @ClassName: MyShiroConfig
* @Description: Shiro配置
* @author darren
* @date 2019年2月20日
*
 */
@Configuration
public class MyShiroConfig {
	
	
	/**
	 * 一、在认证中：
	 *    1.1，将加密算法定义好后扔到 MyShiroRealm中 也就是自己定义的realm中
	 *    1.2，将MyShiroRealm定义后扔到SecurityManager中。
	 *    1.3，后期用到session什么的，都被SecurityManager管理
	 * 
	 * @return
	 */
	
	
	/**
	 * 二、配置session（用Redis存储）
	 * 	  2.1 需要配置session，就需要将sessionManager配置在SecurityManager中。
	 *    2.2 sessionManager需要交给Redis来管理，所以定义了RedisSessionDAO
	 *    2.3 RedisSessionDAO中需要配置Redis的信息，所以定义RedisManager
	 * 
	 * @return
	 */
	
	
	@Value("${spring.datasource.redis.host}")
	private String host;
	@Value("${spring.datasource.redis.port}")
	private int port;
	@Value("${spring.datasource.redis.timeout}")
	private int timeout;
	@Value("${spring.datasource.redis.password}")
	private String password;
	
	
	
	
//-------------------------认证---------------------------
	@Bean
	public SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(myShiroRealm());
		// 自定义session管理 使用redis
		securityManager.setSessionManager(sessionManager());
		 // 自定义缓存实现 使用redis
        securityManager.setCacheManager(cacheManager());	
		return securityManager;
	}
	
	/**
     * cacheManager 缓存 redis实现
     * <p>
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    @Bean
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }

 
	@Bean
	public MyShiroRealmService myShiroRealm() {
		MyShiroRealmService myShiroRealm = new MyShiroRealmService();
		myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
		return myShiroRealm;
	}	
	
	/**
	 * 
	* @Title: hashedCredentialsMatcher
	* @Description: 凭证匹配器,配置密码比较器,由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
	* @param @return    参数
	* @return HashedCredentialsMatcher    返回类型
	* @throws
	 */
	@Bean("hashedCredentialsMatcher")
	public HashedCredentialsMatcher hashedCredentialsMatcher() {
		HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
		// 散列算法:这里使用MD5算法;
		credentialsMatcher.setHashAlgorithmName("MD5");
		// 散列的次数，比如散列两次，相当于 md5(md5(""));
		credentialsMatcher.setHashIterations(2);
		credentialsMatcher.setStoredCredentialsHexEncoded(true);//storedCredentialsHexEncoded默认是true，此时用的是密码加密用的是Hex编码；false时用Base64编码
		return credentialsMatcher;
	}
	
	
//-------------------------redis-session----------------------
	
	//自定义sessionManager
	 @Bean
	 public SessionManager sessionManager() {
	 MySessionManager mySessionManager = new MySessionManager();
	 mySessionManager.setSessionDAO(redisSessionDAO());
	 return mySessionManager;
	 }
	
	
	
	/**
	 * RedisSessionDAO shiro sessionDao层的实现 通过redis
	 * <p>
	 * 使用的是shiro-redis开源插件
	 */
	@Bean
	public RedisSessionDAO redisSessionDAO() {
		RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
		redisSessionDAO.setRedisManager(redisManager());
		redisSessionDAO.setExpire(1800);//过期时间 1800 秒
		return redisSessionDAO;
	}
	
	/**
	 * 配置shiro redisManager
	 * <p>
	 * 使用的是shiro-redis开源插件
	 *
	 * @return
	 */
	public RedisManager redisManager() {
		RedisManager redisManager = new RedisManager();
		redisManager.setHost(host);
		redisManager.setPort(port);
		redisManager.setTimeout(timeout);
//		redisManager.setPassword(password);
		return redisManager;
	}
	
 
	
}
