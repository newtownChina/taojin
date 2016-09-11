package com.taojin.serviceImpl;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taojin.beans.Good;
import com.taojin.dao.GoodDao;
import com.taojin.service.GoodService;
/**
 * �ô��������й���Ʒ��ҵ��ʵ����
 * ����GoodServiceImpl
 * @author NXY
 * @version 1.0
 * @date ����8:11:44
 */
@Service
public class GoodServiceImpl implements GoodService{
	@Resource
	private GoodDao goodDao;
	@Override
	public Good findOne(Good good) {
		return goodDao.selectOne(good);
	}

	@Override
	public List<Good> findAll() {
		return goodDao.selectAll();
	}
	
	@Override
	public List<Good> findSome(Integer uid) {
		return goodDao.selectSome(uid);
	}
	@Override
	
	public int add(Good good) {
		return goodDao.insert(good);
	}

	@Override
	public int remove(Integer id) {
		return goodDao.delete(id);
	}

	@Override
	public int modify(Good good) {
		return goodDao.update(good);
	}

	@Override
	public List<Good> findeKeyWordGood(String keyword) {
		return goodDao.selectKeyWordGood(keyword);
	}
}
