<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>注册</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/css.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/user/user/regist.css'/>">
	<script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/common.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/user/user/regist.js'/>"></script>

  </head>
  
  <body>
<div class="divBody">
  <div class="divTitle">
    <span class="spanTitle">新用户注册</span>
  </div>
  <div class="divCenter">
    <form action="${pageContext.request.contextPath}/user/regist.do" method="post" id="registForm">
    <input type="hidden" name="method" value=""/>
    <table>
      <tr>
        <td class="tdLabel">用户名：</td>
        <td class="tdInput">
          <input type="text" name="username" id="username" class="input" value="${user.username}"/>
        </td>
        <td class="tdError">
          <label class="labelError" id="usernameError">${errors.username}</label>
        </td>
      </tr>
      <tr>
        <td class="tdLabel">登录密码：</td>
        <td class="tdInput">
          <input type="password" name="password" id="password" class="input" value="${user.password}"/>
        </td>
        <td class="tdError">
          <label class="labelError" id="passwordError">${errors.password}</label>
        </td>
      </tr>
      <tr>
        <td class="tdLabel">确认密码：</td>
        <td class="tdInput">
          <input type="password" name="confirmPassword" id="confirmPassword" class="input" value="${user.confirmPassword}"/>
        </td>
        <td class="tdError">
          <label class="labelError" id="confirmPasswordError">${errors.confirmPassword}</label>
        </td>
      </tr>
      <tr>
        <td class="tdLabel">Email：</td>
        <td class="tdInput">
          <input type="text" name="email" id="email" class="input" value="${user.email}"/>
        </td>
        <td class="tdError">
          <label class="labelError" id="emailError" >${errors.email}</label>
        </td>
      </tr>
      <tr>
        <td class="tdLabel">图形验证码：</td>
        <td class="tdInput">
          <input type="text" name="verifyCode" id="verifyCode" class="input" value="${user.verifyCode}"/>
        </td>
        <td class="tdError">
          <label class="labelError" id="verifyCodeError">${errors.verifyCode}</label>
        </td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>
          <span class="verifyCodeImg"><img id="imgVerifyCode" width="100" src="${pageContext.request.contextPath}/user/checkCode.do" /></span>
        </td>
        <td><a href="javascript: _change()">换一张</a></td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>
          <input type="image" src="http://localhost:8080/goods/img/regist1.jpg" id="submit"/>
        </td>
        <td>&nbsp;</td>
      </tr>
    </table>
    </form>
  </div>
</div>
  </body>
</html>
	