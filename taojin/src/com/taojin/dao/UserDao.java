package com.taojin.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.taojin.beans.User;
/**
 * �ô������ݷ��ʽӿ�
 * ������UserDao
 * @author NXY
 * @version 1.0
 * @date ����7:34:35
 */
@Repository

public interface UserDao {
	User selectOne(User user);
	List<User> selectAll();
	int insert(User user);
	int delete(@Param("uid") Integer id);
	int update(User user);
}
