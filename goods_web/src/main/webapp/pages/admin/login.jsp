<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>管理员登录页面</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	<script type="text/javascript">
		function checkForm() {
			if(!$("#a_name").val()) {
				alert("管理员名称不能为空！");
				return false;
			}
			if(!$("#a_password").val()) {
				alert("管理员密码不能为空！");
				return false;
			}
			return true;
		}

		$(function () {//
			//获取cookie中的用户名，Map<String(cookie名称),Cookie(cookie本身)>
			var a_name = window.decodeURI("${cookie.a_name.value}");
			//回显的数据
			if ("${admin.a_name}"){
				a_name=${admin.a_name};
			}

		})
	</script>
  </head>
  
  <body>
<h1>管理员登录页面</h1>
<hr/>
  <p style="font-weight: 900; color: red">${msg }</p>
<form action="<c:url value='/admin/login.do'/>" method="post" onsubmit="return checkForm()" target="_top">
	<%--<input type="hidden" name="method" value="login"/>--%>
	管理员账户：<input type="text" name="a_name" value="${admin.a_name}" id="a_name"/><br/>
	密　　　码：<input type="password" name="a_password" value="${admin.a_password}" id="a_password"/><br/>
	<input type="submit" value="进入后台"/>
</form>
  </body>
</html>
