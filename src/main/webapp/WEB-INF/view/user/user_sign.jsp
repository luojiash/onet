<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<%@ include file="../include/include.jsp"%>
	<meta charset="UTF-8">
	<title>用户登录/注册</title>
	<link rel="stylesheet" type="text/css" href="//oimg.com/css/lib/bootstrap.min.css" />
	<link rel="stylesheet" type="text/css" href="//oimg.com/css/lib/toastr.min.css" />
	<link rel="stylesheet" type="text/css" href="//oimg.com/css/user/user_register.css" />
</head>
<body>
<div class="container">
  	<div class="loginBox row" id="loginBox">
  		<h2>
  			<span>用户登录</span>
  			<a class="pull-right" id="toRegister" href="#">注册 <span class="glyphicon glyphicon-circle-arrow-right" aria-hidden="true"></span></a>
  		</h2>
		<form id="loginForm" class="form-horizontal">
			<div class="form-group has-success">
			  <div class="col-sm-12 col-md-12">
			    <input type="text" class="form-control" name="userName" placeholder="用户名" required>
			  </div>
			</div>
			<div class="form-group has-success">
			  <div class="col-sm-12 col-md-12">
			    <input type="password" class="form-control" name="password" placeholder="密码" required>
			  </div>
			</div>
			<div class="checkbox">
			    <label>
			      <input type="checkbox" name="rememberMe" value="y"> 记住我
			    </label>
			</div>
			<div class="form-group">
			  	<div class="col-sm-offset-4 col-sm-10" style="color: #990033;"></div>
			</div>
			<div class="alert alert-danger" style="display: none;">
			  <button type="button" class="close"><span>×</span></button>
		      <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
	 		  <span class="sr-only">Error:</span>
		      <span class="err-msg"></span>
		    </div><!-- error msg  -->
			<div class="form-group">
			  <div class="col-sm-12 col-md-12">
			    <button type="submit" class="btn btn-info btn-block" data-loading-text="正在登录...">登 录</button>
			  </div>
			</div>
  		</form>
	</div>
	
	<div class="loginBox row" id="registerBox" style="display: none;">
  		<h2>
  			<span>注册账号</span>
  			<a class="pull-right" id="toLogin" href="#">登录 <span class="glyphicon glyphicon-circle-arrow-right" aria-hidden="true"></span></a>
  		</h2>
		<form id="registerForm" class="form-horizontal">
			<div class="form-group has-success">
			  <div class="col-sm-12 col-md-12">
			    <input type="text" class="form-control" name="userName" placeholder="用户名" required>
			  </div>
			</div>
			<div class="form-group has-success">
			  <div class="col-sm-12 col-md-12">
			    <input type="password" class="form-control" name="password" placeholder="密码" required>
			  </div>
			</div>
			<div class="form-group has-success">
			  <div class="col-sm-12 col-md-12">
			    <input type="password" class="form-control" name="confirmPassword" placeholder="重复密码" required>
			  </div>
			</div>
			<div class="form-group">
			  	<div class="col-sm-offset-4 col-sm-10" style="color: #990033;"></div>
			</div>
			<div class="alert alert-danger" style="display: none;">
			  <button type="button" class="close"><span>×</span></button>
			  <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
	 		  <span class="sr-only">Error:</span>
		      <span class="err-msg"></span>
		    </div><!-- error msg  -->
			<div class="form-group">
			  <div class="col-sm-12 col-md-12">
			    <button type="submit" class="btn btn-info btn-block" data-loading-text="正在注册...">注册</button>
			  </div>
			</div>
  		</form>
	</div>
</div>

	<script type="text/javascript" src="//oimg.com/js/lib/jquery-1.11.2.min.js"></script>
	<script type="text/javascript" src="//oimg.com/js/lib/bootstrap.min.js"></script>
	<script type="text/javascript" src="//oimg.com/js/lib/toastr.min.js"></script>
	<script type="text/javascript" src="//oimg.com/js/user/user_sign.js"></script>
</body>
</html>
