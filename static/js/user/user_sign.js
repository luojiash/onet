$(document).ready(function() {
toastr.options.positionClass = "toast-top-center";

/**
 * 用户登录提交
 */
$('#loginForm').submit(function(e){
	e.preventDefault();
	var _this = $(this);
	var btn = _this.find('button[type=submit]').button('loading');
	$.ajax({
	   type: "POST",
	   url: "/user/login/submit",
	   data: _this.serialize(),
	   error: function(data) {
           alert("login error");
       },
	   success: function(data){
		   if (data.result) {
			   toastr.success('登录成功！两秒后跳转。');
			   setTimeout("window.location.href='/note/list'",2000);
		   } else {
			   _this.find('.err-msg').text(data.returnMessage);
			   _this.children('.alert-danger').show();
		   }
	   }
	}).done(function(){
		btn.button('reset');
	});
});

/**
 * 用户注册提交
 */
$('#registerForm').submit(function(e){
	e.preventDefault();
	var _this = $(this);
	var btn = _this.find('button[type=submit]').button('loading');
	$.ajax({
	   type: "POST",
	   url: "/user/register/submit",
	   data: _this.serialize(),
	   error: function(data) {
           alert("register error");
       },
	   success: function(data){
		   if (data.result) {
			   toastr.success('恭喜你，注册成功！请重新登录。');
			   setTimeout("window.location.reload()",2000);
		   } else {
			   _this.find('.err-msg').text(data.returnMessage);
			   _this.children('.alert-danger').show();
		   }
	   }
	}).done(function(){
		btn.button('reset');
	});
});

/**
 * 关闭提示框
 */
$('.close').click(function(){
	$(this).parent().hide();
});

/**
 * 登录、注册界面切换
 */
$('#toRegister').click(function(e){
	e.preventDefault();
	$('#loginBox').hide();
	$('#registerBox').show();
});
$('#toLogin').click(function(e){
	e.preventDefault();
	$('#registerBox').hide();
	$('#loginBox').show();
});

});