<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>cartlist.jsp</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	<script src="<c:url value='/js/round.js'/>"></script>
	
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/user/cart/cart_list.css'/>">
	  <script type="text/javascript">
		  $(function () {
			  //显示总计
			  showTotal();
			  //给全选添加事件
			  $("#selectAll").click(function () {
				  //获取全选的状态
				  var bool = $("#selectAll").attr("checked");
				  //让所有复选框与全选同步
				  setItemCheckbox(bool);
				  //让结算按钮与全选同步
				  setJieSuan(bool);
				  //重新计算总计
				  showTotal();
			  });
			  //给所有复选框添加事件
			  $(":checkbox[name=checkboxBtn]").click(function () {
				  //获取所有复选框条目的个数
				  var all = $(":checkbox[name=checkboxBtn]").length;
				  //获取被勾选复选框条目的个数
				  var select  = $(":checkbox[name=checkboxBtn][checked=true]").length;
				  if(all == select ){//全选
				  	  $("#selectAll").attr("checked",true);//勾选全选按钮
				  	  setItemCheckbox(true);//勾选所有复选框
				  	  setJieSuan(true);//让结算按钮有效
				  }else if(select ==0){//取消全选
					  $("#selectAll").attr("checked",false);//不勾选全选按钮
					  setItemCheckbox(false);//不勾选所有复选框
					  setJieSuan(false);//让结算按钮无效
				  }else {
					  $("#selectAll").attr("checked",false);//不勾选全选按钮
					  setJieSuan(true);//让结算按钮无效
				  }
				  //重新计算总计
				  showTotal();
			  });
			  //给➖号添加事件
			  $(".jian").click(function () {
				  //获取cartItemId
				  var  cartItemId =$(this).attr("id").substring(0,32);
				  //获取数量
				  var quantity = $("#"+cartItemId+"Quantity").val();
				  if (quantity ==1){
					  if (confirm("您是否确定删除此条目")){
						  location = "/goods/cart/batchDelete.do?cartItemIds="+cartItemId;
					  }
				  }else {
					  sendUpateQuantity(cartItemId,Number(quantity)-1);
				  }
			  });
			  //给＋号添加事件
			  $(".jia").click(function () {
				  var cartItemId =$(this).attr("id").substring(0,32);
				  var quantity = $("#"+cartItemId+"Quantity").val();
				  sendUpateQuantity(cartItemId,Number(quantity)+1);
			  });

		  });
		  //异步请求，修改指定条目的数量
		  function sendUpateQuantity(cartItemId,quantity) {

			  $.ajax({
				  url:"/goods/cart/updateQuantity.do",
				  data:{cartItemIds:cartItemId,"quantity":quantity},
				  dataType:"json",
				  type:"post",
				  async:false,
				  caches:false,
				  success:function (data) {
					  $("#"+cartItemId+"Quantity").val(data.quantity);
					  $("#"+cartItemId+"Subtotal").text(data.subTotal);
					  showTotal();
				  }
			  });
		  }
		  //计算总计
		  function showTotal() {
			  var total = 0;
		  	  //获取所有被勾选的复选框，循环遍历
			  $(":checkbox[name=checkboxBtn][checked=true]").each(function () {

				  //获取复选框的值，即其他元素的前缀
				  var id = $(this).val()
				  //获取小计元素的文本内容
				  var text = $("#"+id+"Subtotal").text();
			  	  //累加计算
				  total += Number(text);
			  });
			  $("#total").text(round(total,2));//保留两位小数
		  }
		  //统一设置条目的复选框按钮
		  function setItemCheckbox(bool) {
			  $(":checkbox[name=checkboxBtn]").attr("checked",bool);
		  }
		  //设置结算按钮的样式
		  function setJieSuan(bool) {
			  if(bool){//让结算按钮有效
				  $("#jiesuan").removeClass("kill").addClass("jiesuan");
				  $("#jiesuan").unbind("click");//撤销当前元素所有的click事件
			  }else{//让结算按钮无效
			  	  $("#jiesuan").removeClass("jiesuan").addClass("kill");
			  	  $("#jiesuan").click(function () {
					  return false;
				  });
			  }
		  }
		  //批量删除
		  function batchDelete() {
			  var cartItemIds = new Array();
			  $(":checkbox[name=checkboxBtn][checked=true]").each(function () {
				  //获取被勾选条目的id值
				   var cartItemId= $(this).val();
				   cartItemIds.push(cartItemId);
			  });
			  location = "/goods/cart/batchDelete.do?cartItemIds="+cartItemIds;
		  }
		  //结算
          function jiesuan() {

			  var cartItemIds = new Array();
			  $(":checkbox[name=checkboxBtn][checked=true]").each(function () {
				  //获取被勾选条目的id值
			  	  var id =$(this).val();
			  	  cartItemIds.push(id);
			  })

			  $("#cartItemIds").val(cartItemIds.toString());
			  //给总计给表单的总计
			  $("#hiddenTotal").val($("#total").text())
			  $("#jiesuanForm").submit();
		  }

	  </script>


<%--<script type="text/javascript">--%>
<%--$(function() {--%>
	<%--showTotal();//显示合计--%>
	<%--// 给全选按钮添加点击事件--%>
	<%--$("#selectAll").click(function() {--%>
		<%--var flag = $(this).attr("checked");//获取全选的状态--%>
		<%--setAll(flag);//让所有条目复选框与全选同步--%>
		<%--setJieSuanStyle(flag);//让结算按钮与全选同步--%>
	<%--});--%>

	<%--// 给条目复选框添加事件--%>
	<%--$(":checkbox[name=checkboxBtn]").click(function() {--%>
		<%--var selectedCount = $(":checkbox[name=checkboxBtn][checked=true]").length;//被勾选复选框个数--%>
		<%--var allCount = $(":checkbox[name=checkboxBtn]").length;//所有条目复选框个数--%>
		<%--if(selectedCount == allCount) {//全选了--%>
			<%--$("#selectAll").attr("checked", true);//勾选全选复选框--%>
			<%--setJieSuanStyle(true);//使结算按钮可用--%>
		<%--} else if(selectedCount == 0) {//全撤消了--%>
			<%--$("#selectAll").attr("checked", false);//撤消全选复选框--%>
			<%--setJieSuanStyle(false);//使结算按钮不可用--%>
		<%--} else {//未全选--%>
			<%--$("#selectAll").attr("checked", false);//撤消全选复选框--%>
			<%--setJieSuanStyle(true);//使结算按钮可用--%>
		<%--}--%>
		<%--showTotal();//重新计算合计--%>
	<%--});--%>

	<%--// 给jia、jian添加事件--%>
	<%--$(".jian").click(function() {--%>
		<%--var cartItemId = $(this).attr("id").substring(0, 5);--%>
		<%--var quantity = Number($("#" + cartItemId + "Quantity").val());--%>
		<%--if(quantity == 1) {--%>
			<%--if(confirm("您是否真要删除该条目？")) {--%>
				<%--alert("删除成功！");--%>
			<%--}--%>
		<%--} else {--%>
			<%--sendUpdate(cartItemId, quantity-1);--%>
		<%--}--%>
	<%--});--%>
	<%--$(".jia").click(function() {--%>
		<%--var cartItemId = $(this).attr("id").substring(0, 5);--%>
		<%--var quantity = Number($("#" + cartItemId + "Quantity").val());--%>
		<%--sendUpdate(cartItemId, quantity+1);--%>
	<%--});--%>
<%--});--%>

<%--// 异步请求，修改数量--%>
<%--function sendUpdate(cartItemId, quantity) {--%>
	<%--/*--%>
	 <%--1. 通过cartItemId找到输入框元素--%>
	 <%--2. 通过cartItemId找到小计元素--%>
	<%--*/--%>
	<%--var input = $("#" + cartItemId + "Quantity");--%>
	<%--var subtotal = $("#" + cartItemId + "Subtotal");--%>
	<%--var currPrice = $("#" + cartItemId + "CurrPrice");--%>

	<%--input.val(quantity);--%>
	<%--subtotal.text(round(currPrice.text() * quantity, 2));--%>
	<%--showTotal();--%>
<%--}--%>

<%--// 设置所有条目复选框--%>
<%--function setAll(flag) {--%>
	<%--$(":checkbox[name=checkboxBtn]").attr("checked", flag);//让所有条目的复选框与参数flag同步--%>
	<%--showTotal();//重新设置合计--%>
<%--}--%>

<%--// 设置结算按钮的样式--%>
<%--function setJieSuanStyle(flag) {--%>
	<%--if(flag) {// 有效状态--%>
		<%--$("#jiesuan").removeClass("kill").addClass("jiesuan");//切换样式--%>
		<%--$("#jiesuan").unbind("click");//撤消“点击无效”--%>
	<%--} else {// 无效状态--%>
		<%--$("#jiesuan").removeClass("jiesuan").addClass("kill");//切换样式--%>
		<%--$("#jiesuan").click(function() {//使其“点击无效”--%>
			<%--return false;--%>
		<%--});--%>
	<%--}--%>
<%--}--%>

<%--// 显示合计--%>
<%--function showTotal() {--%>
	<%--var total = 0;//创建total，准备累加--%>
	<%--/*--%>
	<%--1. 获取所有被勾选的复选框，遍历之--%>
	<%--*/--%>
	<%--$(":checkbox[name=checkboxBtn][checked=true]").each(function () {--%>
		<%--/*--%>
		<%--2. 通过复选框找到小计--%>
		<%--*/--%>
		<%--var subtotal = Number($("#" + $(this).val() + "Subtotal").text());--%>
		<%--total += subtotal;--%>
	<%--});--%>
	<%--/*--%>
	<%--3. 设置合计--%>
	<%--*/--%>
	<%--$("#total").text(round(total, 2));--%>
<%--}--%>
<%--</script>--%>

  </head>
  <body>

      <c:choose>
		  <c:when test="${empty cartitemList}">
			  <table width="95%" align="center" cellpadding="0" cellspacing="0">
				  <tr>
					  <td align="right">
						  <img align="top" src="<c:url value='/img/icon_empty.png'/>"/>
					  </td>
					  <td>
						  <span class="spanEmpty">您的购物车中暂时没有商品</span>
					  </td>
				  </tr>
			  </table>
		  </c:when>
		  <c:otherwise>
			  <table width="95%" align="center" cellpadding="0" cellspacing="0">
				  <tr align="center" bgcolor="#efeae5">
					  <td align="left" width="50px">
						  <input type="checkbox" id="selectAll" checked="checked" /><label for="selectAll">全选</label>
					  </td>
					  <td colspan="2">商品名称</td>
					  <td>单价</td>
					  <td>数量</td>
					  <td>小计</td>
					  <td>操作</td>
				  </tr>


				  <c:forEach items="${cartitemList}" var="cartitem">
					  <tr align="center">
						  <td align="left">
							  <input value="${cartitem.cartItemId}" type="checkbox" name="checkboxBtn" checked="checked"/>
						  </td>
						  <td align="left" width="70px">
							  <a class="linkImage" href="<c:url value='/book/load.do?bid=${cartitem.book.bid}'/>"><img border="0" width="54" align="top" src="<c:url value='/${cartitem.book.image_b}'/>"/></a>
						  </td>
						  <td align="left" width="400px">
							  <a href="<c:url value='/book/load.do?bid=${cartitem.book.bid}'/>"><span>${cartitem.book.bname}</span></a>
						  </td>
						  <td><span>&yen;<span class="currPrice" id="${cartitem.cartItemId}CurrPrice">${cartitem.book.currPrice}</span></span></td>
						  <td>
							  <a class="jian" id="${cartitem.cartItemId}Jian"></a><input class="quantity" readonly="readonly" id="${cartitem.cartItemId}Quantity" type="text" value="${cartitem.quantity}"/><a class="jia" id="${cartitem.cartItemId}Jia"></a>
						  </td>
						  <td width="100px">
							  <span class="price_n">&yen;<span class="subTotal" id="${cartitem.cartItemId}Subtotal">${cartitem.subTotal}</span></span>
						  </td>
						  <td>
							  <a href="<c:url value='/cart/batchDelete.do?cartItemIds=${cartitem.cartItemId}'/>">删除</a>
						  </td>
					  </tr>

				  </c:forEach>



				  <tr>
					  <td colspan="4" class="tdBatchDelete">
						  <a href="javascript:batchDelete();">批量删除</a>
					  </td>
					  <td colspan="3" align="right" class="tdTotal">
						  <span>总计：</span><span class="price_t">&yen;<span id="total"></span></span>
					  </td>
				  </tr>
				  <tr>
					  <td colspan="7" align="right">
						  <a href="javascript:jiesuan()" id="jiesuan" class="jiesuan"></a>
					  </td>
				  </tr>
			  </table>
			  <form id="jiesuanForm" action="<c:url value='/cart/showCartItems.do'/>" method="post">
				  <input type="hidden" name="cartItemIds" id="cartItemIds"/>
				  <input type="hidden" name="total" id="hiddenTotal"/>
				  <%--<input type="hidden" name="method" value="loadCartItems"/>--%>
			  </form>

		  </c:otherwise>
	  </c:choose>

  </body>
</html>
