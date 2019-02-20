package com.sping.shiro.demo.controller;
 
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
 
/**
 * 
* @ClassName: LoginController
* @Description: 登陆
* @author darren
* @date 2019年2月20日
*
 */
@RestController
@RequestMapping("/user")
public class LoginController {
 
	Logger logger = LoggerFactory.getLogger(getClass());
 
	@PostMapping("/login")
	public JSONObject login(@RequestParam String username, @RequestParam String password) {
        System.out.println("login start");
		Subject subject = SecurityUtils.getSubject();
		JSONObject json = new JSONObject();
		Session session = subject.getSession();
		String sessionId = (String) session.getId();
		json.put("sessionId", sessionId);
		 System.out.println("login end");
		return json;
	}
 
}
