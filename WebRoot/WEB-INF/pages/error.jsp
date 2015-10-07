<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>found error</title>
    
	<meta http-equiv="pragma" content="no-cache">
<style type="text/css">
	span{
		font-size:30;
	}
	.detile{
		font-size:14;
		color:red;
	}
</style>
  </head>
  
  <body>
  	<p>----------------------------------------------------------------------------------------------------</p>
             <span>404</span>
             <span class="detile">--------所请求的资源不存在</span>
    <p>----------------------------------------------------------------------------------------------------</p>
  </body>
  <script>
  $.ajax({
   	url:"",
   	type:"",
   	success:function(data){
   	
   	},
   	error:function(){
   		alert("网路；对方答复");
   	}
  })
  </script>
</html>
