<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<title></title>
	<meta charset="utf-8">
	<link rel="stylesheet" type="text/css" href="css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="css/fileinput.min.css" />
	<style type="text/css">
		img.thumbnail:hover{zoom:500%;position:absolute;z-index:100;}
	</style>
	<script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
	<script type="text/javascript" src="js/fileinput.min.js"></script>
</head>
<body>
	<div class="container">
		<div class="jumbotron" style="background:rgb(229,222,16);">
			<h2>新医淘金网</h2>
			<p>做最省时、省事，省钱的二手商品信息免费共享</p>
		</div>
	</div>
	<div class="container" style="position:relative;">
		<ul class="nav nav-tabs">  
			<li class="active"><a href="#ershou" data-toggle="tab">二手货</a></li>
			<li><a href="#pub" data-toggle="tab">我的发布</a></li>
			<li><a href="#reglogin" data-toggle="tab">注册/登录</a></li>
		</ul>
		<div class="input-group col-md-4" style="position:absolute;right:0;top:0;margin-right:20px;">
			<form id="keywordForm">
				<input type="text" name="keyword" class="form-control" placeholder="标题关键词搜索,如:自行车" />
			</form>
			<span class="input-group-btn">
				<input id="keywordSearchBtn" type="submit" value="搜索" class="btn btn-primary" onclick="keywordSearch()"/>
			</span>
		</div>
	
		<!-- 直接显示的内容，第一个选项卡 -->
		<div class="tab-content">
			<div class="tab-pane active" id="ershou">
				<ul class="list-group">
					${allGoodsListHTML}
				</ul>
			</div>
			<!-- 我的商品发布 -->
			<div class="tab-pane" id="pub" style="position:relative;">
				<br />
				<div class="container-fluid">
					<div class="row">
						<div class="col-md-3 pull-left">
							<div class="alert alert-info pull-left" style="margin-left:-15px;">
								当前用户：
								<c:if test="${loginUser.phone == null}">
									尚未登录
									<button id="logoff" class="hide btn btn-default">注销</button>
								</c:if>
								<c:if test="${loginUser.phone != null}">${loginUser.phone}
									<button id="logoff" class="btn btn-success">注销</button>
								</c:if>
							</div>
						</div>
						<c:if test="${loginUser != null}">
							<a class="btn btn-default col-md-1 pull-right" data-toggle="modal" data-target="#myModalNew">发布新商品</a>
						</c:if>
					</div>
				</div>
				<ul id="loginUserGoodsList" class="list-group">
					<c:if test="${loginUser.phone!=null}">
						${loginUserGoodsListHTML}
					</c:if>
				</ul>
			</div>
			<div class="tab-pane" id="reglogin" style="position:relative;">
				<div class="row">
					<div class="col-md-3">
						<div class="alert alert-info">登录前请先完成注册哦！</div>
					</div>
				</div>
				<div class="container">
					<div id="d1" class="dialog-1 modal-dialog modal-sm hide" style="position:absolute;left:40%;top:40px;z-index:999;">
						<div class="modal-content">
							<div class="modal-header">
								<h4 class="modal-title">提示</h4>
							</div>
							<div class="modal-body">
								注册成功！请登录...
							</div>
							<div class="modal-footer">
								<button class="btnClose btn btn-success" data-dismiss="modal">确定</button>
							</div>
						</div>
					</div>
					<div class="dialog-2 modal-dialog modal-sm hide" style="position:absolute;left:40%;top:40px;z-index:998;">
						<div class="modal-content">
							<div class="modal-header">
								<h4 class="modal-title">提示</h4>
							</div>
							<div class="modal-body">
								登录成功！
							</div>
							<div class="modal-footer">
								<button class="btnClose btn btn-success" data-dismiss="modal">确定</button>
							</div>
						</div>
					</div>
					<div class="row">
						<div  class="panel panel-default col-md-5">
							<div class="panel-heading">注册</div>
							<div class="panel-body">
								<form id="regist">
									<div class="form-group">
										<input onblur="checkPhone(this,this.value)" type="text" name="phone" placeholder="请输入手机号" class="form-control">
										<span class="help-block"></span>
									</div>
									<div class="form-group">
										<input onblur="checkPassword(this,this.value)" type="password" name="password" placeholder="请输入密码" class="form-control">
										<span class="help-block"></span>
									</div>
								</form>
							</div>
							<div class="panel-footer">
								<input disabled id="registBtn" type="button" value="注册" class="btn btn-default" />
							</div>
						</div>
						<div class="panel panel-default col-md-5 col-md-offset-1">
							<div class="panel-heading">登录</div>
							<div class="panel-body">
								<form id="login">
									<div class="form-group">
										<input onblur="checkLoginPhone(this,this.value)" type="text" name="phone" placeholder="请输入手机号" class="form-control">
										<span class="help-block"></span>
									</div>
									<div class="form-group">
										<input onblur="checkLoginPassword(this,this.value)" type="password" name="password" placeholder="请输入密码" class="form-control">
										<span class="help-block"></span>
									</div>
								</form>
							</div>
							<div class="panel-footer">
								<input disabled id="loginBtn" type="button" value="登录" class="btn btn-default" />
								<span id="fb" class=""></span>
								<a class="pull-right" href="" data-target="#findPassword" data-toggle="modal">找回密码</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 新增商品的模态弹出框 -->
	<div class="modal" id="myModalNew">
		<div class="modal-dialog modal-md">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">新增商品</h4>
				</div>
				<div class="modal-body">
					<div class="row" style="padding:15px;">
						<input id="addPic" type="file" name="picture" multiple onchange="previewPic(this.files[0])">
					</div>
					<div class="alert alert-info">图片文件名请不要出现中文，请先点击upload完成图片上传，再填写以下内容</div>
					<form id="newGoodForm">
						<div class="form-group has-feedback">
							<input type="text" name="title" placeholder="商品标题" class="form-control" onblur="checkValue(this,this.value)">
							<span class="glyphicon form-control-feedback"></span>
							<span class="help-block"></span><br />
						</div>
						<div class="form-group has-feedback">
							<input type="text" name="price" placeholder="商品卖价" class="form-control" onblur="checkValue(this,this.value)">
							<span class="glyphicon form-control-feedback"></span>
							<span class="help-block"></span><br />
						</div>
						<div class="form-group has-feedback">
							<input type="text" name="description" placeholder="商品描述" class="form-control" onblur="checkValue(this,this.value)">
							<span class="glyphicon form-control-feedback"></span>
							<span class="help-block"></span><br />
						</div>
						<div class="form-group has-feedback">
							<input type="text" name="qq" placeholder="我的QQ" class="form-control" onblur="checkValue(this,this.value)">
							<span class="glyphicon form-control-feedback"></span>
							<span class="help-block"></span><br />
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button id="addNewGoodSure" class="btnClose btn btn-primary" data-dismiss="modal" disabled>确定</button>
					<button class="btnClose btn btn-primary" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div>
	<!-- 修改商品的模态弹出框 -->
	<div class="modal" id="myModal">
		<div class="modal-dialog modal-md">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">编辑商品</h4>
				</div>
				<div class="modal-body">
					<div class="row" style="padding:15px;">
						<input id="modifyPic" type="file" name="picture" multiple>
					</div>
					<form id="editForm">
						<input id="hideGid" type="hidden" name="gid" placeholder="修改标题" class="form-control"><br />
						<div class="alert alert-info">图片文件名请不要出现中文，请先点击upload完成图片上传，再填写以下内容</div>
						<div class="form-group has-feedback">
							<input type="text" name="title" placeholder="商品标题" class="form-control" onblur="checkValue(this,this.value)">
							<span class="glyphicon form-control-feedback"></span>
							<span class="help-block"></span><br />
						</div>
						<div id= "priceInput" class="form-group has-feedback">
							<input type="text" name="price" placeholder="商品卖价" class="form-control" onblur="checkValue(this,this.value)">
							<span class="glyphicon form-control-feedback"></span>
							<span class="help-block"></span><br />
						</div>
						<div class="form-group has-feedback">
							<input type="text" name="description" placeholder="商品描述" class="form-control" onblur="checkValue(this,this.value)">
							<span class="glyphicon form-control-feedback"></span>
							<span class="help-block"></span><br />
						</div>
						<div class="form-group has-feedback">
							<input type="text" name="qq" placeholder="我的QQ" class="form-control" onblur="checkValue(this,this.value)">
							<span class="glyphicon form-control-feedback"></span>
							<span class="help-block"></span><br />
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button  id="editSure" class="btnClose btn btn-primary" data-dismiss="modal" disabled>确定</button>
					<button class="btnClose btn btn-primary" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div>
	<!-- 删除商品的弹出框 -->
	<div class="modal" id="myDeleteModal">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">提示</h4>
				</div>
				<div class="modal-body">
					<input type="hidden" id="delGid" value="" />
					确定删除吗？
				</div>
				<div class="modal-footer">
					<button id="delSure" class="btnClose btn btn-primary" data-dismiss="modal">确定</button>
					<button class="btnClose btn btn-primary" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div>
	<!-- 找回密码的弹出框 -->
	<div class="modal" id="findPassword">
		<div class="modal-dialog modal-md">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">找回密码</h4>
				</div>
				<div class="modal-body">
					<!--要求验证码对了，才更新密码  -->
					<div class="form-group" style="position:relative;height:35px;">
						<input id="phoneNum" type="text" name="phone" placeholder="请输入手机号" class="form-control col-md-2">
						<button onclick="sendVeriCode()" class="btn btn-default" style="position:absolute;right:0;top:0">发送验证码</button>
						<span class="help-block"></span>
					</div><br />
					<div class="form-group has-feedback">
						<input id="codeInput" onblur="checkCode(this,this.value)" type="text" name="phoneVeriCode" placeholder="请输入收到的验证码" class="form-control">
						<span class="glyphicon form-control-feedback"></span>
						<span class="help-block"></span>
					</div><br />
					<div class="form-group">
						<input id="newPwd1" onblur="checkPwd1()" type="text" name="phone" placeholder="请输入新密码" class="form-control">
						<span class="help-block"></span>
					</div><br />
					<div class="form-group">
						<input id="newPwd2" onblur="checkPwd2()" type="text" placeholder="请确认新密码" class="form-control">
						<span class="help-block"></span>
					</div>
					<div id="alertSuccess" class="alert alert-info" style="display:none;"></div>
				</div>
				<div class="modal-footer">
					<button id="setNewPwdBtn" onclick="setNewPwd()" class="btn btn-default">确定</button>
					<button class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
	<div class="container text-center">
		<hr />
		<p>Powered by news <a href="http://www.miitbeian.gov.cn/" target="_blank">豫ICP备15033796号-1</a> ©All Rights Reserved</p>
		<br />
	</div>
	<script type="text/javascript" src="js/taojin.js"></script>
</body>
</html>