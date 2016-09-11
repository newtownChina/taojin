package com.taojin.controller;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taojin.beans.Good;
import com.taojin.beans.User;
import com.taojin.serviceImpl.GoodServiceImpl;
import com.taojin.serviceImpl.UserServiceImpl;
@Controller
public class UserController {
	@Resource
	private UserServiceImpl userServiceImpl;
	@Resource
	private GoodServiceImpl goodServiceImpl;
	private String basePath;
	@RequestMapping("regist.do")
	public void regist(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		String phone = request.getParameter("phone");
		String password = request.getParameter("password");
		phone = toURLCode(phone);
		password = toURLCode(password);
		User user = new User();
		user.setPassword(password);
		user.setPhone(phone);
		int num = userServiceImpl.add(user);
		try {
			if(num == 1){
				response.getWriter().print("regSuccess");
			}else{
				System.out.println("注册出错");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/*异步验证注册的用户名是否已经注册*/
	@RequestMapping("registed.do")
	public void registed(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		String phone = request.getParameter("phone");
		phone = toURLCode(phone);
		User user = new User();
		User foundUser;
		user.setPhone(phone);
		foundUser = userServiceImpl.findOne(user);
		try {
			if(foundUser!=null){
				response.getWriter().print("hasRegistered");
			}else{
				response.getWriter().print("canRegister");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/*登录*/
	@RequestMapping("login.do")
	public void login(HttpServletRequest request,HttpServletResponse response){
		basePath = (String)request.getSession().getAttribute("basePath");
		String phone = request.getParameter("phone");
		String password = request.getParameter("password");
		User user = new User();
		user.setPassword(password);
		user.setPhone(phone);
		User foundUser = userServiceImpl.findOne(user);
		if(foundUser == null){
			try {
				response.getWriter().print("loginFail");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			Integer loginUserUid = foundUser.getUid();
			request.getSession().setAttribute("loginUser", foundUser);
			try {
				if(loginUserUid > 0){
					response.getWriter().print("loginSuccess");
				}else {
					System.out.println("登录出错");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			List<Good> goodsList = goodServiceImpl.findSome(loginUserUid);
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
				sb.append("				<p>电话:"+foundUser.getPhone()+"</p>");
				sb.append("				<p>QQ:"+foundUser.getQQ()+"</p>");
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
			String loginUserGoodsListHTML = sb.toString();
			System.out.println(loginUserGoodsListHTML);
			request.getSession().setAttribute("loginUserGoodsListHTML", loginUserGoodsListHTML);
		}
	}
	/*修改用户密码*/
	@RequestMapping("updatePassword.do")
	public void updatePassword(HttpServletRequest request,HttpServletResponse response){
		String phoneNum = request.getParameter("phoneNum");
		String password = request.getParameter("password");
		User user = new User();
		user.setPhone(phoneNum);
		User foundUser = userServiceImpl.findOne(user);
		foundUser.setPassword(password);/*新密码*/
		int num = userServiceImpl.modify(foundUser);
		try {
			if(num == 1){
				response.getWriter().print("updatePwdSuccess");
			}else{
				response.getWriter().print("updatePwdFail");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@RequestMapping("logoff.do")
	public void logoff(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		request.getSession().removeAttribute("loginUser");
	}
	public String isImgNull(Good good){
		String imgUrl = good.getPicture();
		if(imgUrl==null||imgUrl.equalsIgnoreCase("")){
			imgUrl = "default.png";
		}
		return imgUrl;
	}
	/*用户名转码*/
	public String toURLCode(String params){
		params = params.replace("<", "&lt;");
		params = params.replace(">", "&gt;");
		params = params.replace("'", "&#39;");
		params = params.replace("\"", "&quot;");
		params = params.replace("&", "&amp;");
		return params;
	}
}	
