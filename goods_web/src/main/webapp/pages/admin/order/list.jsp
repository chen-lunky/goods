<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>订单列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/user/pager/pager-pager.css'/>" />
	  <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-1.5.1.js"></script>
    <%--<script type="text/javascript" src="<c:url value='/jsps/pager/pager.js'/>"></script>--%>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/admin/order/list.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/css.css'/>" />
	  <script type="text/javascript" src="<c:url value='/jquery/jquery.datepick.js'/>"></script>
	  <script type="text/javascript" src="<c:url value='/jquery/jquery.datepick-zh-CN.js'/>"></script>
    <script type="text/javascript">
		$(function () {
			$("#ordertime").datepick({dateFormat:"yy-mm-dd"});
		})
		function findByOrdertime(){
			var ordertime = $("#ordertime").val();
			location = "http://localhost:8080/goods/adminOrder/findByOrdertime.do?ordertime="+ordertime;
		}

	</script>
  </head>
  
  <body>
<p class="pLink">
  <a href="<c:url value='/adminOrder/findByStatus.do?status=1'/>">未付款</a>  |
  <a href="<c:url value='/adminOrder/findByStatus.do?status=2'/>">已付款</a>  |
  <a href="<c:url value='/adminOrder/findByStatus.do?status=3'/>">已发货</a>  |
  <a href="<c:url value='/adminOrder/findByStatus.do?status=4'/>">交易成功</a>  |
  <a href="<c:url value='/adminOrder/findByStatus.do?status=5'/>">已取消</a> |
  <a href="javascript:findByOrdertime()">按时间查<input type="text" id="ordertime" ></a>


</p>
<div class="divMain">
	<div class="title">
		<div style="margin-top:7px;">
			<span style="margin-left: 150px;margin-right: 280px;">商品信息</span>
			<span style="margin-left: 40px;margin-right: 100px;">金额</span>
			<span style="margin-left: 50px;margin-right: 53px;">订单状态</span>
			<span style="margin-left: 100px;">操作</span>
		</div>
	</div>
	<br/>
	<table align="center" border="0" width="100%" cellpadding="0" cellspacing="0">
	
	     <c:forEach items="${pageInfo.list}" var="order">
			 <tr class="tt">
				 <td width="320px">订单号：<a  href="<c:url value='/adminOrder/showOrder.do?oid=${order.oid}&btn='/>">${order.oid}</a></td>
				 <td width="200px">下单时间：${order.ordertime}</td>
				 <td width="178px">&nbsp;</td>
				 <td width="205px">&nbsp;</td>
				 <td>&nbsp;</td>
			 </tr>
			 <tr style="padding-top: 10px; padding-bottom: 10px;">
				 <td colspan="2">
					 <c:forEach items="${order.orderItemList}" var="orderItem">
						 <img border="0" width="70" src="<c:url value='/${orderItem.book.image_b}'/>"/>
					 </c:forEach>

				 </td>
				 <td style="padding-left: 0">
					 <span class="price_t">&yen;${order.total}</span>
				 </td>
				 <td>
					 <c:choose>
						 <c:when test="${order.status eq 1}">等待付款</c:when>
						 <c:when test="${order.status eq 2}">准备发货</c:when>
						 <c:when test="${order.status eq 3}">等待确认</c:when>
						 <c:when test="${order.status eq 4}">交易成功</c:when>
						 <c:when test="${order.status eq 5}"> 取消</c:when>
					 </c:choose>
				 </td>
				 <td>
					 <a href="<c:url value='/adminOrder/showOrder.do?oid=${order.oid}&btn=watch'/>">查看</a><br/>
					 <c:if test="${order.status eq 1}">
						 <a href="<c:url value='/adminOrder/showOrder.do?oid=${order.oid}&btn=cancel'/>">取消</a><br/>
					 </c:if>
					 <c:if test="${order.status eq 2}">
						 <a href="<c:url value='/adminOrder/showOrder.do?oid=${order.oid}&btn=deliver'/>">发货</a>
					 </c:if>

				 </td>
			 </tr>
		 </c:forEach>

	</table>
	<br/>
	<%@include file="/pages/user/pager/pager.jsp" %>
</div>
  </body>
</html>
