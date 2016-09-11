$(function(){
	$(".dialog-1 .btnClose").click(function(){
		$(".dialog-1").hide();
	});
	$(".dialog-2 .btnClose").click(function(){
		$(".dialog-2").hide();
	});
	$(".nav a").click(function(){
		$(this).tab("show");
	});
	$("#registBtn").click(function(){
		var params = $("#regist").serialize();
		$.ajax({
			url:"regist.do",
			type:"post",
			data:params,
			success:function(data){
				if(data=="regSuccess"){
					$(".dialog-1").removeClass("hide");
				}
			}
		});
	});
	/*登录按钮*/
	$("#loginBtn").click(function(){
		var params = $("#login").serialize();
		$.ajax({
			url:"login.do",
			type:"post",
			data:params,
			success:function(data){
				if(data=="loginSuccess"){
					$(".dialog-2").removeClass("h ide");
					window.location.reload();
				}else{
					$("#fb").text("用户名或密码错误了哦...").addClass("glyphicon glyphicon-remove");
				}
			}
		});
	});
	/*注销*/
	$("#logoff").click(function(){
		$.ajax({
			url:"logoff.do",
			type:"post",
			success:function(data){
				window.location.reload();
			}
		});
	});
	/*编辑商品*/
	$("#editSure").click(function(){
		var params = $("#editForm").serialize();
		$.ajax({
			url:"edit.do",
			type:"post",
			data:params,
			success:function(data){
				if(data == "priceOutOfBound"){
					$("#priceInput").text("价格过高");
				}else{
					$("#loginUserGoodsList").empty().html(data);
				}
			}
		});
	});
	/*删除商品*/
	$("#delSure").click(function(){
		var delGid = $("#delGid").val();
		$.ajax({
			url:"delete.do",
			type:"post",
			data:{gid:delGid},
			success:function(data){
				$("#loginUserGoodsList").empty().html(data);
			}
		});
	});
	/*新增商品*/
	$("#addNewGoodSure").click(function(){
		var params = $("#newGoodForm").serialize();
		//防止js注入
		$.ajax({
			url:"addNewGood.do",
			type:"post",
			data:params,
			success:function(data){
				if(data == "priceOutOfBound"){
					$("#priceInput").text("价格过高");
				}else{
					$("#loginUserGoodsList").empty().html(data);
				}
			}
		});
	});
	/*新增商品时添加图片*/
	$("#addPic").fileinput({
	    uploadUrl: "uploadPic.do", // server upload action
	    uploadAsync: true,
	    maxFileCount: 1
	});
	/*修改商品时添加图片*/
	$("#modifyPic").fileinput({
	    uploadUrl: "uploadPic.do", // server upload action
	    uploadAsync: true,
	    maxFileCount: 1
	});
});
/*向编辑商品你的弹出框表单域中加入编辑商品的gid值，以在表单中获取*/
function editGood(gid){
	$("#hideGid").val(gid);
}
/*向删除商品你的弹出框表单域中加入编辑商品的gid值，以在表单中获取*/
function delGood(gid){
	$("#delGid").val(gid);
}
/*给商品添加图片*/
function addPicture(){
	$("#addPic")[0].click();//js的click，用jquery不管用
}
function previewPic(file){
	var objUrl = getObjectURL(file);
	$("#prePic").attr("src",objUrl);
}
function getObjectURL(file) {
	var url = null ;
	if (window.createObjectURL!=undefined) { // basic
		url = window.createObjectURL(file) ;
	} else if (window.URL!=undefined) { // mozilla(firefox)
		url = window.URL.createObjectURL(file) ;
	} else if (window.webkitURL!=undefined) { // webkit or chrome
		url = window.webkitURL.createObjectURL(file) ;
	}
	return url ;
}
/*图片上传用异步实现*/
function keywordSearch(){
	var keyword = $("#keywordForm").serialize();
	$.ajax({
		url:"keyWordSearch.do",
		type:"post",
		data:keyword,
		success:function(data){
			if(data.trim() != ""){
				$("#ershou ul").html(data);
			}else{
				/*灭有搜索到*/
				var noResult = 
				"<div class=\"alert alert-info pull-left\">没有搜索到相关商品哦</div>"
				$("#ershou ul").html(noResult);
			}
		}
	});
}

/*新增商品的表单验证*/
function checkValue(_this,value){
	var inputName = $(_this).attr("name");
	if(value.trim() != ""){
		/*如果更正了，则先移除叉号再添加对号*/
		$(_this).siblings("span").not(".help-block").removeClass("glyphicon-remove").addClass("glyphicon-ok");
		$(_this).parent("div").removeClass("has-error");
		$(_this).siblings("span.help-block").text("");
		/*判断价格越界*/
		if(inputName == "price"){
			if(value.length > 9){
				$(_this).siblings("span").not(".help-block").removeClass("glyphicon-ok").addClass("glyphicon-remove");
				$(_this).siblings("span.help-block").text("价格超过100万了哦！土豪降点价吧！");
				$(_this).parent("div").addClass("has-error");
			}else{
				/*长度小于9，判断是否是数字*/
				var v = new Number(value);
				if (isNaN(v)){
					$(_this).siblings("span").not(".help-block").removeClass("glyphicon-ok").addClass("glyphicon-remove");
					$(_this).siblings("span.help-block").text("价格请填写数字");
					$(_this).parent("div").addClass("has-error");
				}
			}
		}
		/*判断js注入*/
	}else{
		/*如果改错了，则先移除对号在添加叉号*/
		$(_this).siblings("span").not(".help-block").removeClass("glyphicon-ok").addClass("glyphicon-remove");
		$(_this).parent("div").addClass("has-error");
		$(_this).siblings("span.help-block").text("内容不能为空哦");
	}
	/*检查是否全部都正确，此时提交按钮可用了*/
	var formName = $(_this).parents("form").attr("id");
	checkAllInput(formName);
}
function checkAllInput(formName){
	var id = "#"+formName;
	/*填写正确的*/
	var okNum = $(id).find(".glyphicon-ok").length;
	/*总的需要填写的*/
	var inputNum = $(id).find("input:not('#hideGid')").length;
	if(okNum == inputNum){
		if(formName == "newGoodForm"){
			$("#addNewGoodSure").removeAttr("disabled");
		}else{
			$("#editSure").removeAttr("disabled");
		}
	}else{
		/*如果用户填写都对了，然后又改错，重新禁用*/
		if(formName == "newGoodForm"){
			$("#addNewGoodSure").attr("disabled","");
		}else{
			$("#editSure").attr("disabled","");
		}
	}
}
/*注册验证*/
var r,r2;
function checkPhone(_this,value){
	/*手机号长度*/
	r = value.match(/^[0-9]{11}$/);//不满足正则则返回null
	if(r == null){
		$(_this).siblings("span").text("请输入11位手机号哦");
	}else{
		$(_this).siblings("span").text("");
		/*满足js验证后，进一步检测数据库是否已经注册该账户*/
		$.ajax({
			url:"registed.do",
			type:"post",
			data:{phone:value},
			success:function(data){
				if(data=="hasRegistered"){
					$(_this).siblings("span").text("该用户已经注册，请直接登录哦");
					r = false;//禁用注册按钮
				}else{//data=="canRegister"
					$(_this).siblings("span").text("该用户可以注册");
				}
			}
		});
		
	}
	enableRegBtn(r,r2);
}
function checkPassword(_this,value){
	r2 = value.match(/^[a-zA-Z0-9]{6,18}$/);
	if(r2 == null){
		$(_this).siblings("span").text("请输入6~18位的字母数字密码");
	}else{
		$(_this).siblings("span").text("");
	}
	enableRegBtn(r,r2);
}
function enableRegBtn(r,r2){
	if(r&&r2){
		$("#registBtn").removeAttr("disabled");
	}else{
		$("#registBtn").attr("disabled","");
	}
}
/*登录验证*/
var s,s2;
function checkLoginPhone(_this,value){
	/*手机号长度*/
	s = value.match(/^[0-9]{11}$/);
	if(s == null){
		$(_this).siblings("span").text("请输入11位手机号哦");
	}else{
		$(_this).siblings("span").text("");
	}
	enableLoginBtn(s,s2);
}
function checkLoginPassword(_this,value){
	s2 = value.match(/^[a-zA-Z0-9]{6,18}$/);
	if(s2 == null){
		$(_this).siblings("span").text("请输入6~18位的字母数字密码");
	}else{
		$(_this).siblings("span").text("");
	}
	enableLoginBtn(s,s2);
}
function enableLoginBtn(s,s2){
	if(s&&s2){
		$("#loginBtn").removeAttr("disabled");
	}else{
		$("#loginBtn").attr("disabled","");
	}
}
/*运营商发送的验证码*/
function sendVeriCode(){
	var phoneNum = $("#phoneNum").val();
	/*判断手机号是否符合要求*/
	var result = phoneNum.match(/^[0-9]{11}$/);//不满足条件则result为null
	if(!result){//如果错误
		$("#phoneNum").siblings("span.help-block").text("手机号不正确");
	}else{
		$("#phoneNum").siblings("span.help-block").text("");
		$.ajax({
			url:"sendVerifyCode.do",
			type:"post",
			data:{phoneNum:phoneNum},
			dataType:"json",
			success:function(data){
				if(data.obj != undefined){
					$("#alertSuccess").show().text("短信发送成功");
				}
			}
		});
	}
}
var ifVeriCodeRigtht = false;
function checkPwd1(){
	var newPwd1 = $("#newPwd1").val();
	r1 = newPwd1.match(/^[a-zA-Z0-9]{6,18}$/);/*第一个密码*/
	if(!r1){/*r1不符合*/
		$("#newPwd1").siblings("span.help-block").text("密码仅限6~18位的字母和数字哦");
	}else{
		$("#newPwd1").siblings("span.help-block").text("");
	}
	return r1;
}
function checkPwd2(){
	var newPwd1 = $("#newPwd1").val();
	var newPwd2 = $("#newPwd2").val();
	r2 = newPwd2.match(/^[a-zA-Z0-9]{6,18}$/);/*第二个密码*/
	if(!r2){/*r2不符合*/
		$("#newPwd2").siblings("span.help-block").text("密码仅限6~18位的字母和数字哦");
	}else{
		$("#newPwd2").siblings("span.help-block").text("");
	}
	if(newPwd1 != newPwd2){
		$("#newPwd2").siblings("span.help-block").text("两次输入密码不一致，请重新输入哦");
	}else{
		$("#newPwd2").siblings("span.help-block").text("");
	}
	return r2&&(newPwd1 == newPwd2);
}
function setNewPwd(_this){
	/*用户输入获取到的验证码*/
	var newPwd2 = $("#newPwd2").val();
	var r1 = checkPwd1();
	var r2 = checkPwd2();
	if(r1 && r2 && ifVeriCodeRigtht && (newPwd2!="")){/*两次输入密码和验证码都正确*/
		var phoneNum = $("#phoneNum").val();
		$.ajax({
			url:"updatePassword.do",
			type:"post",
			data:{phoneNum:phoneNum,password:newPwd2},
			dataType:"text",
			success:function(data){
				if(data == "updatePwdSuccess"){
					$("#alertSuccess").show().text("密码修改成功!请登录...");
				}
			}
		});
	}
}
function checkCode(_this,value){
	/*验证码是否正确,需要发送到https://api.netease.im/sms/verifycode.action HTTP/1.1*/
	var phoneNum = $("#phoneNum").val();
	$.ajax({
		url:"checkVerifyCode.do",
		type:"post",
		data:{mobile:phoneNum,code:value},
		dataType:"json",
		success:function(data){
			if(data.code == "200"){
				$("span.form-control-feedback").addClass("glyphicon-ok");
				$(_this).siblings("span.help-block").text("");
				ifVeriCodeRigtht = true;
			}else{
				$(_this).siblings("span.help-block").text("验证码错误,请重新输入...");
				$("span.form-control-feedback").removeClass("glyphicon-ok");
			}
		}
	});
}
/*html防脚本注入转义*/
//function html_encode(str){   
//  var s = "";   
//  if (str.length == 0) return "";   /*转换后因为&gt的存在导致，后台无法获取参数了，因为&*/
//  s = str.replace(/(<)|(%3C)/g, "&lt;");   
//  s = s.replace(/(>)|(%3E)/g, "&gt;");   
//  s = s.replace(/ /g, "&nbsp;");   
//  s = s.replace(/(\')|(%27)/g, "&#39;");   
//  s = s.replace(/(\")|(%22)/g, "&quot;");   
//  s = s.repl   ace(/\n/g, "<br>");   
//  return s;   
//}   
//function toJson(data) {  
//    data=data.replace(/&/g,",");   
//    data=data.replace(/=/g,":");  
//    data="{\""+data+"\"}";  
//    return data;  
//}  