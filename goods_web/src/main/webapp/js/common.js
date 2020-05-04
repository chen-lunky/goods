function _change() {
	$("#vCode").attr("src", "/goods/user/checkCode.do?" + new Date().getTime());
}