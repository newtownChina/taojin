package com.taojin.controller;
import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.taojin.beans.Good;
import com.taojin.beans.User;
import com.taojin.serviceImpl.GoodServiceImpl;
import com.taojin.serviceImpl.UserServiceImpl;
/**
 * @author NXY
 * @version 1.0
 * @date 下午7:40:08
 */
@Controller
public class GoodController {
	@Resource
	private GoodServiceImpl goodServiceImpl;
	@Resource
	private UserServiceImpl userServiceImpl;
	private String basePath;
	/**
	 * @return
	 */
	@RequestMapping("index.do")
	public ModelAndView allGoods(HttpServletRequest request,ModelMap modelMap,HttpSession httpSession){
		String path = request.getContextPath();
		int port = request.getServerPort();
		if(port == 80){
			basePath = request.getScheme()+"://"+request.getServerName()+path+"/";	
		}else{		
			basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";	
		}
		httpSession.setAttribute("basePath", basePath);
		List<Good> goodsList = goodServiceImpl.findAll();
		StringBuilder sb = new StringBuilder();
		Integer uid;
		User user;
		for(Good good:goodsList){
			uid = good.getUid();
			user = new User();
			user.setUid(uid);//查询和当前商品有关的客户
			user = userServiceImpl.findOne(user);
			sb.append("<li class=\"list-group-item\">");
			sb.append("		<div class=\"row\">");
			sb.append("			<div class=\"col-md-1\"><img class=\"thumbnail\" src='"+basePath+"goodImages/"+isImgNull(good)+"' width=\"60\" height=\"auto\" /></div>");
			sb.append("			<div class=\"col-md-9\">");	
			sb.append("				<p>标题:"+good.getTitle()+"</p>");	
			sb.append("				<p>卖价:"+good.getPrice()+"元</p>");	
			sb.append("				<p>描述:"+good.getDescription()+"</p>");	
			sb.append("			</div>");	
			sb.append("			<div class=\"col-md-2\">");	
			sb.append("				<p>卖家电话:"+user.getPhone()+"</p>");	
			sb.append("				<p>QQ:"+user.getQQ()+"</p>");	
			sb.append("			</div>");	
			sb.append("		</div>");	
			sb.append("</li>");	
		}
		modelMap.put("allGoodsListHTML", sb.toString());
		return new ModelAndView("index", modelMap);
	}
	/*编辑*/
	@RequestMapping("edit.do")
	public void editGood(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response,HttpSession httpSession){
		String fileName = (String) request.getSession().getAttribute("fileName");
		String gid = request.getParameter("gid");
		String title = request.getParameter("title");
		String price = request.getParameter("price");
		String description = request.getParameter("description");
		title = toURLCode(title);
		price = toURLCode(price);
		description = toURLCode(description);
		User loginUser = (User) httpSession.getAttribute("loginUser");
		Integer uid = loginUser.getUid();
		Good g = new Good();
		g.setGid(Integer.valueOf(gid));
		g.setDescription(description);
		g.setPicture(fileName);
		if(price.length()>10){
			try {
				response.setCharacterEncoding("utf-8");
				response.getWriter().print("priceOutOfBound");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			g.setPrice(Float.valueOf(price));
		}
		g.setTitle(title);
		g.setUid(uid);
		goodServiceImpl.modify(g);
		List<Good> goodsList = goodServiceImpl.findSome(uid);
		String loginUserGoodsListHTML= listToHTML(loginUser,goodsList);
		
		httpSession.setAttribute("loginUserGoodsListHTML", loginUserGoodsListHTML);
		try {
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(loginUserGoodsListHTML);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/*删除*/
	@RequestMapping("delete.do")
	public void delGood(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response,HttpSession httpSession){
		String gid = request.getParameter("gid");
		User loginUser = (User) httpSession.getAttribute("loginUser");
		Good g = new Good();
		g.setGid(Integer.valueOf(gid));
		goodServiceImpl.remove(Integer.valueOf(gid));
		int uid = loginUser.getUid();
		List<Good> goodsList = goodServiceImpl.findSome(uid);
		String loginUserGoodsListHTML = listToHTML(loginUser,goodsList);
		httpSession.setAttribute("loginUserGoodsListHTML", loginUserGoodsListHTML);
		try {
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(loginUserGoodsListHTML);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*添加新商品*/
	@RequestMapping("addNewGood.do")
	public void addNewGood(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response,HttpSession httpSession){
		/*先上传图片，然后从session从获取*/
		String fileName = (String) httpSession.getAttribute("fileName");
		String title = request.getParameter("title");
		String price = request.getParameter("price");
		String description = request.getParameter("description");
		User loginUser = (User) httpSession.getAttribute("loginUser");
		title = toURLCode(title);
		price = toURLCode(price);
		description = toURLCode(description);
		Integer uid = loginUser.getUid();
		Good g = new Good();
		g.setUid(uid);
		g.setDescription(description);
		g.setPicture(fileName);
		if(price.length()>10){
			try {
				response.setCharacterEncoding("utf-8");
				response.getWriter().print("priceOutOfBound");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			g.setPrice(Float.valueOf(price));
		}
		g.setTitle(title);
		goodServiceImpl.add(g);
		List<Good> goodsList = goodServiceImpl.findSome(uid);
		String loginUserGoodsListHTML = listToHTML(loginUser, goodsList);
		httpSession.setAttribute("loginUserGoodsListHTML", loginUserGoodsListHTML);
		try {
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(loginUserGoodsListHTML);
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*从session中清楚上传的图片，否则下次如果用户没有上传图片还会用这个图*/
		httpSession.setAttribute("fileName", "default.png");
	}
	/*提取方法*/
	private String listToHTML(User loginUser,List<Good> goodsList) {
		StringBuilder sb = new StringBuilder();
		for(Good good:goodsList){
			sb.append("<li class=\"list-group-item\">");
			sb.append("		<input type=\"hidden\" name=\"gid\" value='"+good.getGid()+"'>");
			sb.append("		<div class=\"row\">");
			sb.append("			<div class=\"col-md-1\"><img class=\"thumbnail\" src='"+basePath+"goodImages/"+isImgNull(good)+"' width=\"60\" height=\"auto\"></div>");
			sb.append("			<div class=\"col-md-7\">");
			sb.append("				<p>标题:"+good.getTitle()+"</p>");
			sb.append("				<p>卖价:"+good.getPrice()+"元</p>");
			sb.append("				<p>描述:"+good.getDescription()+"</p>");
			sb.append("			</div>");
			sb.append("			<div class=\"col-md-2\">");
			sb.append("				<p>我的电话:"+loginUser.getPhone()+"</p>");
			sb.append("				<p>QQ:"+loginUser.getQQ()+"</p>");
			sb.append("			</div>");
			sb.append("			<div class=\"col-md-2 text-right\" style=\"padding-right:30px;\">");
			sb.append("				<div class=\"row\">");
			sb.append("					<a onclick=\"editGood("+good.getGid()+")\" data-toggle=\"modal\" data-target=\"#myModal\" href=\"javascript:void(0)\" class=\"btn btn-default\">编辑</a>");
			sb.append("					<a onclick=\"delGood("+good.getGid()+")\" data-toggle=\"modal\" data-target=\"#myDeleteModal\" href=\"javascript:void(0)\" class=\"btn btn-default\">删除</a>");
			sb.append("				</div>");
			sb.append("			</div>");
			sb.append("		</div>");
			sb.append("</li>");	
		}
		return sb.toString();
	}
	public String isImgNull(Good good){
		String imgUrl = good.getPicture();
		if(imgUrl==null||imgUrl.equalsIgnoreCase("")){
			imgUrl = "default.png";
		}
		return imgUrl;
	}
	/*防js注入：转码*/
	public String toURLCode(String params){
		params = params.replace("<", "&lt;");
		params = params.replace(">", "&gt;");
		params = params.replace("'", "&#39;");
		params = params.replace("\"", "&quot;");
		params = params.replace("&", "&amp;");
		return params;
	}
}
