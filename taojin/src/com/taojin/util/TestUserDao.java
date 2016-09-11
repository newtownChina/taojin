package com.taojin.util;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.taojin.beans.User;
import com.taojin.serviceImpl.UserServiceImpl;
public class TestUserDao {
	public void testSelect(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		UserServiceImpl  userServiceImpl = ctx.getBean("userServiceImpl",UserServiceImpl.class);
		User user = new User();
		user.setUid(1);
		User user2 = userServiceImpl.findOne(user);
		System.out.println(user2);
	}
}
