package com.taojin.controller;
import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taojin.beans.Good;
import com.taojin.beans.User;
import com.taojin.serviceImpl.GoodServiceImpl;
import com.taojin.serviceImpl.UserServiceImpl;
/**
 * 用处：处理关键词搜索，当关键词为空的时候,sql为like '%%'，会查出所有记录,不报错
 * 类名：GoodSearchController
 * @author NXY
 * @version 1.0
 * @date 上午11:25:21
 */
@Controller
public class GoodSearchController {
	@Resource
	private GoodServiceImpl goodServiceImpl;
	@Resource
	private UserServiceImpl userServiceImpl;
	private String basePath;
	@RequestMapping("keyWordSearch.do")
	public void searchKeyWordGood(HttpServletRequest request,HttpServletResponse response){
		basePath = (String)request.getSession().getAttribute("basePath");
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String keyword = request.getParameter("keyword");
		List<Good> goodsList = goodServiceImpl.findeKeyWordGood(keyword);
		StringBuilder sb = new StringBuilder();
		Integer uid;
		User user;
		for(Good good:goodsList){
			uid = good.getUid();
			user = new User();
			user.setUid(uid);
			user = userServiceImpl.findOne(user);//查询发布了该商品的   客户的信息
			sb.append("<li class=\"list-group-item\">");
			sb.append("		<div class=\"row\">");
			sb.append("			<div class=\"col-md-1\"><img class=\"thumbnail\" src='"+basePath+"goodImages/"+isImgNull(good)+"' width=\"60\" height=\"auto\" /></div>");
			sb.append("			<div class=\"col-md-9\">");	
			sb.append("				<p>标题:"+good.getTitle()+"</p>");	
			sb.append("				<p>卖价:"+good.getPrice()+"元</p>");	
			sb.append("				<p>描述:"+good.getDescription()+"</p>");	
			sb.append("			</div>");	
			sb.append("			<div class=\"col-md-2\">");	
			sb.append("				<p>电话:"+user.getPhone()+"</p>");	
			sb.append("				<p>QQ:"+user.getQQ()+"</p>");	
			sb.append("			</div>");	
			sb.append("		</div>");	
			sb.append("</li>");	
		}
		String allGoodsListHTML = sb.toString();
		try {
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(allGoodsListHTML);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String isImgNull(Good good){
		String imgUrl = good.getPicture();
		if(imgUrl==null||imgUrl.equalsIgnoreCase("")){
			imgUrl = "default.png";
		}
		return imgUrl;
	}
}
