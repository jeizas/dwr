<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="<%=path%>/js/jquery.js"></script>
	<script type='text/javascript' src='<%=path%>/dwr/engine.js'> </script>  
    <script type='text/javascript' src='<%=path%>/dwr/util.js'> </script>  
    <script type='text/javascript' src='<%=path%>/dwr/interface/chat.js'> </script>  
	<script type="text/javascript">
		window.onload = function(){
			initData();
			dwr.engine.setActiveReverseAjax(true);
			dwr.engine.setNotifyServerOnPageUnload(true);
			var tag = "${user.username}";    //自定义一个标签
	       	chat.onPageLoad(tag);
		}
		
		function initData(){
			chat.initData();
		}
		
		function testDate(s){
			document.getElementById("userList").innerHTML="真的不知道对不对！";
		}
		
		function addLoginUser(user){
		document.getElementById("loginInfo").innerHTML = user.username+"&nbsp;进入聊天室！";
			window.setTimeout(function(){
				document.getElementById("loginInfo").innerHTML = "";
			},2000);
		//	document.getElementById("userList").innerHTML="<li id="+user.id+">"+user.username+"</li>";
		//	var li = document.createElement("li");
		//	li.id = user.id;
		//	li.innerText= user.username;
		//	document.getElementById("userList").appendChild(li);
		}
		
		/*
		function loginUser(username){
			document.getElementById("loginInfo").innerHTML = username+"&nbsp;进入聊天室！";
			window.setTimeout(function(){
				document.getElementById("loginInfo").innerHTML = "";
			},2000);
		}
		*/
		function sendMsg(){
			var userSelect = document.getElementById("userSelect").value;
			var chatInfo = document.getElementById("chatInfo").value;
			chat.sendMsg(userSelect,chatInfo);
			document.getElementById("chatInfo").innerHTML="";
		}
		function privateMsg(){
			var userSelect = document.getElementById("userSelect").value;
			var chatInfo = document.getElementById("chatInfo").value;
			chat.privateMsg(userSelect,chatInfo);
			document.getElementById("chatInfo").innerHTML="";
		}
		function chatInfos(from,to,msg,data){
			console.log(from+to+msg+data);
		}
		function tuichu(){
			chat.logout();
			window.location.href = "/dwr/login/logout.action";
		}
		
		function clearWin(){
			chat.clearWindow();
		}
		
		function allChatInfo(){
			window.open("/dwr/chat/getAllChatInfo.action","newwindow","height=400, width=600,scrollbars=yes");
		}
	</script>
  </head>
<body>
	<table align="center" border="1">
		<tr>
			<td colspan="1" width="530px;" align="left">&nbsp;&nbsp;欢迎：${user.username}</td>
			<td colspan="1" width="170px;" align="left"><div id="loginInfo"></div></td>
		</tr>
		<tr>
			<td width="530px" height="400px;" valign="top">
			<div style="overflow:auto;height:400px;">
				<ul id="chatInfos" style="list-style:none;margin:0;padding:0;">
						
				</ul>
			</div>
			</td>
			<td width="170px" height="400px;" rowspan="2" valign="top">
			<div style="overflow:auto;height:500px;">
					在线用户列表：
					<ul id="userList">
						
					</ul>
				</div>
			</td>
		</tr>
		<tr>
			<td width="430px" height="100px;" valign="top" align="right">
				<form action="">
					<textarea rows="4" cols="63" name="chatInfo" id="chatInfo"></textarea>
					我对<select name="userSelect" id="userSelect">
					       <option value="大家">大家</option>
					       <option value="jeizas">jeizas</option>
					       <option value="li">li</option>
				        </select>说&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" value="退出" onclick="javascript:tuichu();"></input>&nbsp;&nbsp;
					<input type="button" value="发送" onclick="javascript:privateMsg();"></input>&nbsp;&nbsp;
					<input type="button" value="清屏" onclick="javascript:clearWin();"></input>&nbsp;&nbsp;
					<input type="button" value="聊天记录" onclick="javascript:allChatInfo();"></input>&nbsp;&nbsp;
				</form>
			</td>
		</tr>
	</table>
</body>
</html>