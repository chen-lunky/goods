$(function() {
	// 注册按钮图片切换
	$("#submit").hover(
		function() {
			$("#submit").attr("src", "/goods/img/regist2.jpg");
		}, 
		function() {
			$("#submit").attr("src", "/goods/img/regist1.jpg");
		}
	);
	// 默认隐藏所有错误信息
	$(".error").css("display", "none");
	
	// 当提交表单时
	$("#registForm").submit(function() {
		var bool = true;
		if(!validateUsername()) {
			bool = false;
		}
		if(!validatePassword()) {
			bool = false;
		}
		if(!validateConfirmPassword()) {
			bool = false;
		}
		if(!validateEmail()) {
			bool = false;
		}
		if(!validateVerifyCode()) {
			bool = false;
		}
		return bool;
	});
});

// 校验用户名
function validateUsername() {
	$("#usernameError").css("display", "none");
	var bool = true;
	var val = $("#loginname").val();
	if(!val) {
		$("#usernameError").text("用户名不能为空！");
		$("#usernameError").css("display", "");
		bool = false;
	} else if(val.length < 2 || val.length > 10) {
		$("#usernameError").text("用户名长度必须在2~10之间！");
		$("#usernameError").css("display", "");
		bool = false;
	}
	return bool;
}

// 校验密码
function validatePassword() {
	$("#passwordError").css("display", "none");
	var bool = true;
	var val = $("#password").val();
	if(!val) {
		$("#passwordError").text("密码不能为空！");
		$("#passwordError").css("display", "");
		bool = false;
	} else if(val.length < 2 || val.length > 10) {
		$("#passwordError").text("密码长度必须在2~10之间！");
		$("#passwordError").css("display", "");
		bool = false;
	}
	return bool;	
}

// 验证确认密码
function validateConfirmPassword() {
	$("#confirmPasswordError").css("display", "none");
	var bool = true;
	var val = $("#confirmPassword").val();
	if(!val) {
		$("#confirmPasswordError").text("密码不能为空！");
		$("#confirmPasswordError").css("display", "");
		bool = false;
	} else if(val != $("#password").val()) {
		$("#confirmPasswordError").text("两次密码输入不一致！");
		$("#confirmPasswordError").css("display", "");
		bool = false;
	}
	return bool;	
}

// 校验email
function validateEmail() {
	$("#emailError").css("display", "none");
	var bool = true;
	var val = $("#email").val();
	if(!val) {
		$("#emailError").text("Email不能为空！");
		$("#emailError").css("display", "");
		bool = false;
	} else if(!/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/.test(val)) {
		$("#emailError").text("错误的Email格式！");
		$("#emailError").css("display", "");		
		bool = false;
	}
	return bool;
}

// 校验验证码
function validateVerifyCode() {
	$("#verifyCodeError").css("display", "none");
	var bool = true;
	var val = $("#verifyCode").val();
	if(!val) {
		$("#verifyCodeError").text("验证码不能为空！");
		$("#verifyCodeError").css("display", "");
		bool = false;
	}
	return bool;
}