<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <%@ include file="../include/include.jsp"%>
  <meta charset="UTF-8">
  <title>系统设置</title>
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
          <li class="active"><a href="#panel1" role="tab" data-toggle="tab">编辑器设置</a></li>
        </ul>
        <div class="tab-content">
          <div class="tab-pane fade in active" id="panel1">
            <form class="form-horizontal" method="POST" action="/user/setting/edit">
              <div class="col-sm-offset-1 col-sm-10">
                <div class="checkbox">
                  <label>
                    <input id="markdown" name="mdEnabled" type="checkbox" <c:if test="${setting.mdEnabled}">checked</c:if>> 启用实时markdown的功能
                  </label>
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
	$('#markdown').change(function(e,data){
	  var md = $(this).is(':checked');
  	  $.ajax({
  		type: 'POST',
        url: '/user/setting/submit',
        data: 'mdEnabled='+md,
        error: function(data) {
             alert("error");
        },
        success: function(data){
          if (data.result) {
        	  localStorage.mdEnabled = md;
        	  toastr.info('设置已保存');
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
