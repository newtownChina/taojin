package com.taojin.service;
import java.util.List;
import com.taojin.beans.User;
/**
 * �ô����й��û���ҵ��ӿ�
 * ������UserService
 * @author NXY
 * @version 1.0
 * @date ����7:35:05
 */
public interface UserService {
	User findOne(User user);
	List<User> findAll();
	int add(User user);
	int remove(Integer id);
	int modify(User user);
}
