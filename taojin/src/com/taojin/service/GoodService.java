package com.taojin.service;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.taojin.beans.Good;
/**
 * �ô����й���Ʒ��ҵ��ӿ�
 * ����GoodService
 * @author NXY
 * @version 1.0
 * @date ����7:34:01
 */
public interface GoodService {
	Good findOne(Good good);
	List<Good> findAll();
	List<Good> findSome(@Param("uid") Integer uid);
	int add(Good good);
	int remove(@Param("gid") Integer id);
	int modify(Good good);
	List<Good> findeKeyWordGood(@Param("keyword") String keyword);
}
