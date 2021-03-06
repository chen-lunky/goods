<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>修改密码</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/css.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/user/user/pwd.css'/>">
	<script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	<script type="text/javascript" src="<c:url value="/js/user/user/pwd.js"/> "></script>
	  <script src="<c:url value='/js/common.js'/>"></script>
	  <script type="text/javascript">
		  function _change1() {
			  $("#vCode").attr("src", "/goods/user/checkCode.do?" + new Date().getTime());
		  }
	  </script>
  </head>
  
  <body>
    <div class="div0">
    	<span>修改密码</span>
    </div>

	<div class="div1">
		<form action="<c:url value='/user/updatePassword.do'/>" method="post" target="_top">
			<label class="msgClass">${msg }</label>
		<table>
			<%--<tr>--%>
				<%--<td><label class="error">${msg }</label></td>--%>
				<%--<td colspan="2">&nbsp;</td>--%>
			<%--</tr>--%>
			<tr>
				<td align="right">原密码:</td>
				<td><input class="input" type="password" name="password" id="password" value="${user.password}"/></td>
				<td><label id="passwordError" class="error"></label></td>
			</tr>
			<tr>
				<td align="right">新密码:</td>
				<td><input class="input" type="password" name="newPassword" id="newPassword" value="${newPassword}"/></td>
				<td><label id="newPasswordError" class="error"></label></td>
			</tr>
			<tr>
				<td align="right">确认密码:</td>
				<td><input class="input" type="password" name="confirmPassword" id="confirmPassword" value="${user.confirmPassword}"/></td>
				<td><label id="confirmPasswordError" class="error"></label></td>
			</tr>
			<tr>
				<td align="right"></td>
				<td>
				  <img id="vCode" src="<c:url value="/user/checkCode.do"/> " border="1"/>
		    	  <a href="javascript:_change1();">看不清，换一张</a>
				</td>
			</tr>
			<tr>
				<td align="right">验证码:</td>
				<td>
				  <input class="input" type="text" name="verifyCode" id="verifyCode" value=""/>
				</td>
				<td><label id="verifyCodeError" class="error"></label></td>
			</tr>
			<tr>
				<td align="right"></td>
				<td><input id="submit" type="submit" value="修改"/></td>
			</tr>
		</table>
		</form>
	</div>
  </body>
</html>
