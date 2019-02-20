# springboot+shiro+redis前后端分离实现认证 #

## 一、shiro架构图与基本知识 ##
![](https://i.imgur.com/uQeFfz0.png)

**四大功能：**

（1）认证

（2）授权

（3）加密

（4）会话管理

**1.1 Subject**

Subject 即主题，外部应用与subject进行交互，subject记录了当前操作用户，将用户当前的概念理解为当前操作的主体，可能是一个通过浏览器请求的用户，也可能是一个运行的程序。

Subject在shiro中是一个接口，接口中提供了许多认证授权的相关方法，外部程序通过subject进行认证授权，而subject通过subject通过SecurityManager进行认证授权。

**1.2 SecurityManager **

SecurityManager即安全管理器，对于所有的subject进行安全管理，它是shiro的核心，负责对所有的subject进行管理，通过SecurityManager可以完成Subject的认证授权等。SecurityManager通过三部分进行完成：

一、Authenticator(进行认证)；二、Authorizer（进行授权）；三、SessionManager进行会话管理。

**1.2.1 Authenticator**

Authenticator 即认证器，对用户身份进行认证。Authenticator是一个借口shiro提供ModularRealmAuthenticator实现类，通过ModularRealmAuthenticator基本上可以实现大多数需求，也可以自定义拦截器。

**1.2.2 Authorizer**

Authorizer 即授权器。用户通过认证器认证通过，在访问功能时需要通过授权器判断用户是否有此功能的操作权限。

**1.2.3 SessionManager**

sessionManager 即会话管理，shiro框架定义了一套会话管理，他不依赖web容器的session，所有shiro可以适用于非web应用上，也可以将分布式应用的会话集中在一点管理，该特性可以使他实现单点登录。

**1.2.4 SessionManager中的SessionDAO**

essionDAO即会话dao，是对session会话操作的一套接口，比如要将session存储到数据库，可以通过jdbc将会话存储到数据库。

**1.3 Realm**

Realm 即领域，相当于DataSource数据源，securityManager进行安全认证需要通过Realm获取用户安全数据。比如：如果用户身份数据在数据库那么realm就需要从数据库获取用户身份信息。

注意：不要把realm理解成只是从数据源取数据，在realm中还有认证授权校验的相关的代码。

**1.4 CacheManager**

CacheManager即缓存管理，将用户权限数据存储在缓存，这样可以提高性能。

**1.5 Cryptography**

Cryptography 即密码管理，shiro提供了一套加密、解密的组件，方便开发。比如提供常用的散列、加/解密等功能。

## 数据库表 ##
**1.生成数据表**

    DROP TABLE IF EXISTS `user`;
    CREATE TABLE `user` (
      `id` int(36) NOT NULL AUTO_INCREMENT,
      `username` varchar(20) DEFAULT NULL,
      `password` varchar(64) DEFAULT NULL,
      `salt` varchar(255) DEFAULT NULL,
      PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

**2.生成测试数据**
    
    public class createPassword {
    
    	public static void main(String[] args) {
	        String hashAlgorithmName = "md5";//加密方式, 散列算法:这里使用MD5算法;
	        String credentials = "123456";//密码
	        int hashIterations = 2; //加密次数
	        ByteSource credentialsSalt = ByteSource.Util.bytes("root");//盐,为了即使相同的密码不同的盐加密后的结果也不同
	        String  obj = new SimpleHash(hashAlgorithmName, credentials, credentialsSalt, hashIterations).toHex();
	        System.out.println(obj);
      	}
    }
    

## 测试结果 ##

**（1）登录测试**
A. post：  http://localhost:8080/user/login?username=张三&password=123456

![](https://i.imgur.com/fvOBIJB.png)

发送正确的登录信息，返回sessionId的Json值。

**B. 查看Redis数据库**
![](https://i.imgur.com/lAo5k6I.png)

**C. 后台打印：**
![](https://i.imgur.com/J3IEUpP.png)

**D. 退成登录测试**
post：http://localhost:8080/out/logout

![](https://i.imgur.com/41Jd7cb.png)

后台打印结果:
![](https://i.imgur.com/xPxyLky.png)

**E. 不正确密码登录**
post: http://localhost:8080/user/login?username=张三&password=123455

![](https://i.imgur.com/SuWzP8f.png)

**F.没有登录直接请求接口，也就是没有session**
post: http://localhost:8080/userlist

![](https://i.imgur.com/ud0a3no.png)

**G.有登录直接请求接口**
post: http://localhost:8080/userlist

![](https://i.imgur.com/VTdx5ZU.png)





