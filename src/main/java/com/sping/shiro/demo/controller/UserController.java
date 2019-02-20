package com.sping.shiro.demo.controller;
 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
 
/**
 * 
* @ClassName: UserController
* @Description: 测试接口
* @author darren
* @date 2019年2月20日
*
 */
@RestController
public class UserController {
 
	@GetMapping("/userlist")
	@ResponseBody
	public String getUser(){
		
		
		return "user";
		
	}
	
}
