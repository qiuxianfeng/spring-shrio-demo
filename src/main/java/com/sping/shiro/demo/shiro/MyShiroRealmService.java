package com.sping.shiro.demo.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.sping.shiro.demo.model.User;
import com.sping.shiro.demo.service.UserService;

/**
 * 
* @ClassName: MyShiroRealmService
* @Description: 自定义权限匹配和账号密码匹配
* @author darren
* @date 2019年2月20日
*
 */
public class MyShiroRealmService extends AuthorizingRealm{
 
	//日志
	
	private static final Logger log = LoggerFactory.getLogger(MyShiroRealmService.class);
 
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	/**
	 * 
	 * <p>Title: doGetAuthenticationInfo</p>   
	 * <p>Description: 主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确</p>   
	 * @param token
	 * @return
	 * @throws AuthenticationException   
	 * @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		
		//获取用户的输入的账号
		String username = (String)token.getPrincipal();
		log.info("token带来的数据：  "+username);
 
		String passwordDataSource = userService.findPass(username);
		log.info("从数据库中查询到的数据密码：{}",passwordDataSource);
		//通过username从数据库中查找 User对象，如果找到，没找到
		//实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制
		User user = userService.getUserByUsername(username);
		log.info("user：{}",user);
		
		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
				user, //用户对象--数据库
	            user.getPassword(), //密码--数据库
	            ByteSource.Util.bytes(user.getSalt()),//盐
	            getName()  //realm name
				);
		return simpleAuthenticationInfo;	
	}
	
	
	//授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		// TODO 自动生成的方法存根
		//改掉null
		//查询数据库获取角色和权限信息
		//SimpleAuthorizationInfo a = new SimpleAuthorizationInfo();
//		a.setRoles(roles);
		return null;
	
	}
}
