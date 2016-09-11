package com.taojin.test;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.taojin.beans.Good;
import com.taojin.serviceImpl.GoodServiceImpl;
/**
 * 用处：处理有关商品的业务控制器
 * 类名：GoodController
 * @author NXY
 * @version 1.0
 * @date 下午7:40:08
 */
public class TestGoodController {
	@Resource
	private GoodServiceImpl goodServiceImpl;
	public ModelAndView test(){
		Good good = new Good();
		good.setGid(3);
//		goodServiceImpl.add(good);
//		goodServiceImpl.modify(good);
//		goodServiceImpl.remove(2);
		System.out.println(goodServiceImpl.findOne(good));
		return null;
	}
}