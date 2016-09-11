package com.taojin.controller;
import java.io.File;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ServletConfigAware;
import org.springframework.web.context.ServletContextAware;
import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;

/**
 * 用处：处理图片上传
 * 类名：UploadPic
 * @author NXY
 * @version 1.0
 * @date 下午12:30:36
 */
@Controller
public class UploadPicController implements ServletConfigAware,ServletContextAware{
	private ServletConfig servletConfig;
	private ServletContext servletContext;
	@Override
	public void setServletConfig(ServletConfig config) {
		this.servletConfig = config; 
	}
	@Override
	public void setServletContext(ServletContext context) {
		this.servletContext = context;
	}
	@RequestMapping("uploadPic.do")
	public void uploadPic(HttpServletRequest request,HttpServletResponse response){
		//保存商品图片的位置
		String  filePath = servletContext.getRealPath("/")+"goodImages";
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdir();
		}
		SmartUpload su = new SmartUpload();
		String result = "";
		try {
			su.initialize(servletConfig,request,response);
			su.setAllowedFilesList("jpg,gif,png");
			su.setMaxFileSize(1024*600);//600K
			su.setTotalMaxFileSize(1024*700);
			su.upload();
			int success_num  = su.save(filePath);
			if(success_num==1){result += "图片上传成功,请完成你的用户注册";}else{result = "图片上传失败";}
			//需要文件名，用于保存到服务器数据库中
			String fileName = su.getFiles().getFile(0).getFileName();
			request.getSession().setAttribute("fileName", fileName);
		} catch (SmartUploadException e) {
			if(e.getMessage().indexOf("1010")!=-1){result = "失败，文件类型错误";}
			if(e.getMessage().indexOf("1015")!=-1){result = "失败，文件类型错误";}
			if(e.getMessage().indexOf("1105")!=-1){result = "失败，头像最大100KB";}
			if(e.getMessage().indexOf("1110")!=-1){result = "失败，头像最大100KB";}
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("su.upload()出错");
		}finally{
			try {
				response.setCharacterEncoding("utf-8");
				response.getWriter().print(result);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}	
