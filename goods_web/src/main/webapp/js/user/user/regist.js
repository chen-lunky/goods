$(function () {
    $(".labelError").each(function () {
        showError($(this))
    });
    // 注册按钮图片切换
    $("#submit").hover(
        function () {
            $("#submit").attr("src","/goods/img/regist2.jpg")
        },
        function f() {
            $("#submit").attr("src","/goods/img/regist1.jpg")
        }
    )
    /**
     * 3.输入框得到焦点隐藏错误信息
     */
    $(".input").focus(function () {
        //通过输入框找到对应的label的id
        var labelId = $(this).attr("id")+"Error";
        //把label的内容情况
        $("#"+labelId).text("");
        //隐藏没有信息的label
        showError($("#"+labelId));
    });
    /**
     * 4.输入框失去焦点进行校验
     */
    $(".input").blur(function () {
        var id = $(this).attr("id");
        var funName = "validate"+id.substring(0,1).toUpperCase()+id.substring(1)+"()";
        eval(funName);//执行函数调用


    });

    $("#registForm").submit(function () {
        if (!validateUsername() || !validatePassword() || !validateConfirmPassword() || !validateEmail() || !validateVerifyCode()){
            return false;
        }
        return true;
    });
});

/**
 * 登录名校验方法
 * @param ele
 */
function validateUsername() {

    var id = "username";
    var value = $("#"+id).val();

    //1非空校验
    if (!value){
        /**
         * 1.获取对应的label
         * 添加错误信息
         * 显示信息
         */
        $("#"+id+"Error").text("用户名不能为空");
        showError($("#"+id+"Error"));
        return false;
    }
    // //2.长度校验
    // if (value.length<3 || value.length>20){
    //     /**
    //      * 1.获取对应的label
    //      * 添加错误信息
    //      * 显示信息
    //      */
    //     $("#"+id+"Error").text("用户名的长度要在3-20之间");
    //     showError($("#"+id+"Error"));
    //     return false;
    // }

    //3.是否注册校验
    $.ajax({
        url:"/goods/user/validateUsername",//请求路径
        data:{username:value},//传给服务端的数据
        dataType:"json",
        type:"POST",
        async:false,//是否是异步请求，如果是异步，那么就不会等服务器返回，我们这个函数就会向下执行了
        caches:false,
        success:function(data){
            if (!data){
                $("#"+id+"Error").text("该用户名已被注册");
                showError($("#"+id+"Error"));
                return false;
            }
        }

    });
    return true;
}
/**
 * 登录密码校验方法
 * @param ele
 */
function validatePassword() {

    var id = "password";
    var value = $("#"+id).val();
    //1非空校验
    if (!value){
        /**
         * 1.获取对应的label
         * 添加错误信息
         * 显示信息
         */
        $("#"+id+"Error").text("密码不能为空");
        showError($("#"+id+"Error"));
        return false;

    }
    //2.长度校验
    if (value.length<3 || value.length>20){
        /**
         * 1.获取对应的label
         * 添加错误信息
         * 显示信息
         */
        $("#"+id+"Error").text("密码的长度要在3-20之间");
        showError($("#"+id+"Error"));
        return false;
    }
    return true

}
/**
 * 确认密码校验方法
 * @param ele
 */
function validateConfirmPassword() {

    var id = "confirmPassword";
    var value = $("#"+id).val();
    //1非空校验
    if (!value){
        /**
         * 1.获取对应的label
         * 添加错误信息
         * 显示信息
         */
        $("#"+id+"Error").text("确认密码不能为空");
        showError($("#"+id+"Error"));
        return false;
    }
    //2.判断两次输入的密码是否一次
    if (value != $("#password").val()){
        /**
         * 1.获取对应的label
         * 添加错误信息
         * 显示信息
         */
        $("#"+id+"Error").text("两次输入不一致");
        showError($("#"+id+"Error"));
        return false;
    }
    return true;
}
/**
 * 邮箱校验方法
 * @param ele
 */
function validateEmail() {

    var id = "email";
    var value = $("#"+id).val();
    //1非空校验
    if (!value){
        /**
         * 1.获取对应的label
         * 添加错误信息
         * 显示信息
         */
        $("#"+id+"Error").text("邮箱不能为空");
        showError($("#"+id+"Error"));
        return false;

    }
    //2.格式校验
    if (!/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/.test(value)){
        /**
         * 1.获取对应的label
         * 添加错误信息
         * 显示信息
         */
        $("#"+id+"Error").text("邮箱的格式错误");
        showError($("#"+id+"Error"));
        return false;
    }
    //3.是否注册校验
    $.ajax({
        url:"/goods/user/validateEmail",//请求路径
        data:{email:value},//传给服务端的数据
        dataType:"json",
        type:"POST",
        async:false,//是否是异步请求，如果是异步，那么就不会等服务器返回，我们这个函数就会向下执行了
        caches:false,
        success:function (data) {
            if (!data){
                $("#"+id+"Error").text("该邮箱已被注册");
                showError($("#"+id+"Error"));
                return false;
            }
        }

    });
    return true;

}
/**
 *验证码校验方法
 * @param ele
 */
function validateVerifyCode() {

    var id = "verifyCode";
    var value = $("#"+id).val();
    //1非空校验
    if (!value){
        /**
         * 1.获取对应的label
         * 添加错误信息
         * 显示信息
         */
        $("#"+id+"Error").text("验证码不能为空");
        showError($("#"+id+"Error"));
        return false;

    }
    //2.长度校验
    if (value.length !=4 ){
        /**
         * 1.获取对应的label
         * 添加错误信息
         * 显示信息
         */
        $("#"+id+"Error").text("验证码的长度不对");
        showError($("#"+id+"Error"));
        return false;
    }
    $.ajax({
        url:"/goods/user/validateVerifyCode",//请求路径
        data:{verifyCode:value},//传给服务端的数据
        dataType:"json",
        type:"POST",
        async:false,//是否是异步请求，如果是异步，那么就不会等服务器返回，我们这个函数就会向下执行了
        caches:false,
        success:function (data) {
            if (!data){
                $("#"+id+"Error").text("验证码错误");
                showError($("#"+id+"Error"));
                return false;
            }
        }

    });
    return true;

}

function showError(ele) {
    var text = ele.text();
    if (!text){
        ele.css("display","none");
    } else {
        ele.css("display","");
    }
};

function _change() {
    $("#imgVerifyCode").attr("src","/goods/user/checkCode.do?"+new Date().getTime())

}
