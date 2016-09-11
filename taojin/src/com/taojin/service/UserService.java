package com.taojin.service;
import java.util.List;
import com.taojin.beans.User;
/**
 * 用处：有关用户的业务接口
 * 类名：UserService
 * @author NXY
 * @version 1.0
 * @date 下午7:35:05
 */
public interface UserService {
	User findOne(User user);
	List<User> findAll();
	int add(User user);
	int remove(Integer id);
	int modify(User user);
}
