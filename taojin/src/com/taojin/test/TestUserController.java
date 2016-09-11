package com.taojin.test;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.taojin.beans.User;
import com.taojin.serviceImpl.UserServiceImpl;
public class TestUserController {
	@Resource
	private UserServiceImpl userServiceImpl;
	public ModelAndView test(){
		User user = new User();
		user.setQQ("111111111");
		user.setImage("<iamge/>");
		user.setPhone("11222");
		user.setPassword("mima1324564");
//		System.out.println(userServiceImpl.findOne(user));
//		System.out.println(userServiceImpl.findAll());
////		System.out.println(userServiceImpl.remove(2));
//		System.out.println(userServiceImpl.modify(user));
		System.out.println(userServiceImpl.add(user));
		return null;
	}
}
