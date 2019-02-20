package com.sping.shiro.demo.controller;
 
import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;
 
/**
 * 
* @ClassName: LogOutController
* @Description: 退出登陆
* @author darren
* @date 2019年2月20日
*
 */
@RestController
@RequestMapping("/out")
public class LogOutController {
	
	
	Logger logger = LoggerFactory.getLogger(getClass());
 
	@PostMapping("/logout")
	public void logout(){
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		
		String sessionId = (String)session.getId();
		logger.info("sessionId{}",sessionId);
		JedisShardInfo  shardInfo = new JedisShardInfo("redis://192.168.187.102:6379");	
//		shardInfo.setPassword("123456");
		Jedis jedis = new Jedis(shardInfo);
		long jedis_key = jedis.del("shiro:session:"+sessionId);	
		logger.info("jedis_key{}",jedis_key);	
		logger.info("--------数据已经删除--------"); 
 
	}
	
	/**
     * 未登录，shiro应重定向到登录界面，此处返回未登录状态信息由前端控制跳转页面
     * @return
     */
    @RequestMapping(value = "/unauth")
    @ResponseBody
    public Object unauth() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", "1000000");
        map.put("msg", "未登录");
        return map;
    }

 
}
