<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <%@ include file="../include/include.jsp"%>
  <meta charset="UTF-8">
  <title>个人信息</title>
  <link rel="stylesheet" type="text/css" href="//oimg.com/css/lib/bootstrap.min.css" />
  <link rel="stylesheet" type="text/css" href="//oimg.com/css/lib/toastr.min.css" />
  <style type="text/css">
  .tab-content {margin-top: 12px;}
  </style>
</head>
<body>
<%@ include file="/WEB-INF/view/include/header.jsp"%>
<div class="container">
  <div class="row">
    <div class="col-md-12">
      <div class="tabbable">
        <ul class="nav nav-tabs" role="tablist" id="myTab">
          <li class="active"><a href="#panel1" role="tab" data-toggle="tab">我的信息</a></li>
          <li><a href="#panel2" role="tab" data-toggle="tab">修改密码</a></li>
        </ul>
        <div class="tab-content">
          <div class="tab-pane fade in active" id="panel1">
            <form class="form-horizontal" id="profileform" method="POST" action="/user/profile/edit">
              <div class="form-group">
                <label class="col-sm-2 control-label">用户名</label>
                <div class="col-sm-6" style="margin-top: 6px;">
                  ${user.userName}
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">注册日期</label>
                <div class="col-sm-6" style="margin-top: 6px;">
                  ${user.registerDate}
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">用户角色</label>
                <div class="col-sm-6" style="margin-top: 6px;">
                  ${user.role.mean}
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">邮箱地址</label>
                <div class="col-sm-6">
                  <input type="email" class="form-control" name="email" value="${user.email}" required>
                </div>
              </div>
              <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                  <button type="submit" class="btn btn-default">保存</button>
                </div>
              </div>
            </form>
          </div>
          
          <div class="tab-pane fade" id="panel2">
            <form class="form-horizontal" method="POST" action="/user/chpwd/submit" id="chpwdform">
              <div class="form-group">
                <label class="col-sm-2 control-label">原密码</label>
                <div class="col-sm-6">
                  <input type="password" class="form-control" name="password" required>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">新密码</label>
                <div class="col-sm-6">
                  <input type="password" class="form-control" name="newPassword" required>
                </div>
              </div>
               <div class="form-group">
                <label class="col-sm-2 control-label">确认新密码</label>
                <div class="col-sm-6">
                  <input type="password" class="form-control" name="newPassword1" required>
                </div>
              </div>
              <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                  <button type="submit" class="btn btn-default">Submit</button>
                </div>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
  
  <script type="text/javascript" src="//oimg.com/js/lib/jquery-1.11.2.min.js"></script>
  <script type="text/javascript" src="//oimg.com/js/lib/bootstrap.min.js"></script>
  <script type="text/javascript" src="//oimg.com/js/lib/toastr.min.js"></script>
  <script type="text/javascript">
  $(document).ready(function(){
    $('form').submit(function(e){
  	  e.preventDefault();
  	  $.ajax({
        type: e.target.method,
        url: e.target.action,
        data: $(this).serialize(),
        error: function(data) {
             alert("error");
        },
        success: function(data){
          if (data.result) {
          	toastr.success('编辑成功');
          } else {
          	toastr.error(data.returnMessage, '编辑失败！');
          }
        }
      });
    });
  });
  </script>
</body>
</html>
