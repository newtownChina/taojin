package com.taojin.serviceImpl;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.taojin.beans.User;
import com.taojin.dao.UserDao;
import com.taojin.service.UserService;
@Service
public class UserServiceImpl implements UserService{
	@Resource
	private UserDao userDao;
	@Override
	public User findOne(User user) {
		return userDao.selectOne(user);
	}

	@Override
	public List<User> findAll() {
		return userDao.selectAll();
	}

	@Override
	public int add(User user) {
		return userDao.insert(user);
	}

	@Override
	public int remove(Integer id) {
		return userDao.delete(id);
	}

	@Override
	public int modify(User user) {
		return userDao.update(user);
	}
}
