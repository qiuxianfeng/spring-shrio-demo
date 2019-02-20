package com.sping.shiro.demo.service;

 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sping.shiro.demo.dao.UserDAO;
import com.sping.shiro.demo.model.User;
 

@Service("userService")
public class UserService {
 
	@Autowired
	private UserDAO dao;
 
	public String findPass(String username) {
 
		// 之后写业务逻辑
		return dao.findPass(username);
	}
 
	public User getUserByUsername(String username) {
		// 之后写业务逻辑
		return dao.getUserByUsername(username);
 
	}
 
}
