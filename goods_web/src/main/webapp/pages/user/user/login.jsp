<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
      <title>登录</title>
    
	  <meta http-equiv="pragma" content="no-cache">
	  <meta http-equiv="cache-control" content="no-cache">
	  <meta http-equiv="expires" content="0">
	  <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	  <meta http-equiv="description" content="This is my page">
	  <meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	  <link rel="stylesheet" type="text/css" href="<c:url value='/css/user/user/login.css'/>">
	  <script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
      <script type="text/javascript" src="<c:url value='/js/user/user/user_login.js'/>"></script>
      <script src="<c:url value='/js/common.js'/>"></script>

      <script type="text/javascript">
          $(function () {//
              //获取cookie中的用户名，Map<String(cookie名称),Cookie(cookie本身)>
              var username = window.decodeURI("${cookie.username.value}");
              //回显的数据
              if ("${user.username}"){
                  username=${user.username};
              }

          })
      </script>

  </head>
  
  <body>
	<div class="main">
	  <%--<div><img src="<c:url value='/img/logo.gif'/>" /></div>--%>
	  <div>
	    <div class="imageDiv"><img class="img" src="<c:url value='/img/zj.png'/>"/></div>
        <div class="login1">
	      <div class="login2">
            <div class="loginTopDiv">
              <span class="loginTop">书城用户登录</span>
              <span>
                <a href="<c:url value='/pages/user/user/regist.jsp'/>" class="registBtn"></a>
              </span>
            </div>
            <div>
              <form target="_top" action="${pageContext.request.contextPath}/user/login.do" method="post" id="loginForm">
                <input type="hidden" name="method" value="" />
                  <table>
                    <tr>
                      <td width="50"></td>
                      <td><label class="error" id="msg">${msg}</label></td>
                    </tr>
                    <tr>
                      <td width="50">用户名</td>
                      <td><input class="input" type="text" name="username" id="username" value="${user.username}"/></td>
                    </tr>
                    <tr>
                      <td height="20">&nbsp;</td>
                      <td><label id="usernameError" class="error"></label></td>
                    </tr>
                    <tr>
                      <td>密　码</td>
                      <td><input class="input" type="password" name="password" id="password" value="${user.password}"/></td>
                    </tr>
                    <tr>
                      <td height="20">&nbsp;</td>
                      <td><label id="passwordError" class="error"></label></td>
                    </tr>
                    <tr>
                      <td>验证码</td>
                      <td>
                        <input class="input yzm" type="text" name="verifyCode" id="verifyCode" value="${user.verifyCode}"/>
                        <img id="vCode" src="${pageContext.request.contextPath}/user/checkCode.do"/>
                        <a id="VerifyCodeImg" href="javascript: _change1() ">换张图</a>

                      </td>
                    </tr>
                    <tr>
                      <td height="20px">&nbsp;</td>
                      <td><label id="verifyCodeError" class="error"></label></td>
                    </tr>
                    <tr>
                      <td>&nbsp;</td>
                      <td align="left">
                        <input type="image" id="submit" src="<c:url value='/img/login1.jpg'/>" class="loginBtn"/>
                      </td>
                    </tr>																				
                 </table>
              </form>
            </div>
          </div>
        </div>
      </div>
	</div>
  </body>
</html>
	