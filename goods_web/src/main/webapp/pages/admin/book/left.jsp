<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'left.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	  <script type="text/javascript" src="<c:url value="/jquery/jquery-1.5.1.js"/> "></script>
	  <link rel="stylesheet" type="text/css" href="<c:url value='/css/admin/book/left.css'/>">
	  <script type="text/javascript" src="<c:url value='/js/menu/mymenu.js'/>"></script>
	  <link rel="stylesheet" href="<c:url value='/css/menu/mymenu.css'/>" type="text/css" media="all">

	  <script language="javascript">
		  //对象名要与第一个参数一致
		  var bar = new Q6MenuBar("bar", "网上书城");
		  $(function() {
			  bar.colorStyle = 2;//指定配色的样式。有四种，1，2，3，4
			  bar.config.imgDir = "<c:url value='/img/menu/'/>";//小工具需要的图片路径
			  bar.config.radioButton=true;//是否排斥，多个一级分类是否排斥
			  <c:forEach items="${parents}" var="parent">
			  <c:forEach items="${parent.children}" var="child">
			  /**
			   * bar.add（）
			   * 第一个参数是以及分类
			   * 第二个参数是二级分类
			   * 第三个参数是点击二级分类链接后的url
			   * 第四个参数是链接的内容在哪个框架页显示
			   */
			  bar.add("${parent.cname}", "${child.cname}", "/goods/adminBook/findByCategory.do?cid=${child.cid}", "body");
			  </c:forEach>
			  </c:forEach>

			  $("#menu").html(bar.toString());
		  });
      </script>

  </head>
  
  <body onload="load()">
<div id="menu"></div>
  </body>
</html>
