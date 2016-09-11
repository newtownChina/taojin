<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title>抱歉——页面未找到</title>
  <style type="text/css">
	*{margin:0;padding:0;font-family:"微软雅黑"}
	#title{width:800px;height:120px;font-size:25px;margin:50px auto;}
	#title div{float:left;}
	#title div h1{font-size:50px;font-weight:bold;color:#5b4f5b;margin:17px 0 0 10px;}
	#title div p{color:#5b4f5b;padding-left:17px;float:left;width:70px;height:20px;line-height:20px;font-size:15px;}
	#title div a{display:block;float:left;text-decoration:none;font-size:15px;height:20px;line-height:20px;margin-left:20px;}
	#title div span{font-size:100px;font-weight:bold;color:#5b4f5b;}
	#f_lost{border:1px dotted #666;width:800px;height:400px;margin:-20px auto;} 
	#f_lost
  </style>
 </head>
 <body>
	<div id="title">
		<div><span>服务器去打酱油了,等会再来访问吧，(∩_∩)！</span></div>
		<div>
			<h1>您访问的页面找不回来了！</h1>
			<p id="sec">6秒后</p><a href="index.do">返回首页</a>
		</div>
	</div>
	<div id="f_lost">
		<a href="http://bbs.baobeihuijia.com/thread-254062-1-1.html"></a>
	</div>
	<script type="text/javascript">
		window.onload = function(){
			var pDom =document.getElementById("sec");
			var sec = 6;
			var timer = setInterval(function(){
				sec--;
				pDom.innerHTML = "";
				pDom.innerHTML = sec+"秒后";
			},1000);
			var timer = setTimeout(function(){
				location="index.do";
			},6000);
		}
	</script>
 </body>
</html>

