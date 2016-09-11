package com.taojin.dao;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.taojin.beans.Good;
@Repository
public interface GoodDao {
	Good selectOne(Good good);
	List<Good> selectAll();
	List<Good> selectSome(@Param("uid") Integer uid);
	int insert(Good good);
	int delete(@Param("gid") Integer id);
	int update(Good good);
	List<Good> selectKeyWordGood(@Param("keyword") String keyword);
}
