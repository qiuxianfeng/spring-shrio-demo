package com.sping.shiro.demo.dao;

 
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.sping.shiro.demo.model.User;
 

@Mapper
public interface UserDAO {
	
	@Select("select password from user2 where username=#{username}")
	String findPass(String username);
	
	@Select("select * from user2 where username=#{username}")
	User getUserByUsername(String username);
	
 
}
