<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<%@ include file="../include/include.jsp"%>
	<meta charset="UTF-8">
	<title>后台-用户列表</title>
	<link rel="stylesheet" type="text/css" href="//oimg.com/css/lib/bootstrap.min.css" />
	<link rel="stylesheet" type="text/css" href="//oimg.com/css/lib/simplePagination.css" />
	
	<script type="text/javascript" src="//oimg.com/js/lib/jquery-1.11.2.min.js"></script>
	<script type="text/javascript" src="//oimg.com/js/lib/bootstrap.min.js"></script>
	<script type="text/javascript" src="//oimg.com/js/lib/jquery.simplePagination.js"></script>
	
	<script type="text/javascript" src="//oimg.com/js/admin/user_list.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/view/include/header.jsp"%>
<div class="container">
	<div class="row">
		<div class="page-header">
			<h2>Users <small>All users</small></h2>
		</div>
		<form class="form-inline" method="get">
		  <div class="form-group">
		      <input type="text" class="form-control" name="u" value="${u }" placeholder="搜索 …">
		      <input type="text" class="hidden" id="keyWord" value="${u }">
		  </div>
		  <button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-search"></span></button>
		</form>
		<table class="table table-striped">
			<thead>
				<tr>
					<th>ID</th>
					<th>E-mail</th>
					<th>用户名</th>
					<th>昵称</th>
					<th>Role</th>
					<th>注册日期</th>
					<th>Status</th>
				</tr>
			</thead>
			<tbody>
			<%@ include file="/WEB-INF/view/admin/_user_list_body.jsp"%>
			</tbody>
		</table>
		<div id="pagination">
		</div>
	</div> <!-- user list end -->
</div>	
	
	<script type="text/javascript">
	$(function() {
		simplePage("${userRes.total}","${userRes.pageNo}","${userRes.pageSize}");
	});
	</script>
</body>
</html>
