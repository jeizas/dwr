<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户登录</title>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">

	document.onkeydown=toLogin;
	function toLogin(){
		if (event.keyCode == 13){
			checkForm();
    	}
	}

	function checkForm(){
		if($("#username").val().length <1){
			alert("用户名不能为空！");
			return false;
		}
		if($("#password").val().length <1){
			alert("密码不能为空！");
			return false;
		}
	}
</script>
</head>
<body>
	<form name="form" action="/dwr/login/login.action" method="post" onsubmit="return checkForm();">
		<table align="center">
			<tr>
				<td colspan="2"><font color="red">${msg}</font></td>
			</tr>
			<tr>
				<td>用户名：</td>
				<td><input type="text" name="user.username" id="username"></input></td>
			</tr>
			<tr>
				<td>密&nbsp;&nbsp;&nbsp;&nbsp;码：</td>
				<td><input type="text" name="user.password" id="password"></input></td>
			</tr>
			
			<tr>
				<td align="center" colspan="2"><input type="submit" value="登录"></input></td>
			</tr>
		</table>
	</form>
</body>
</html>