<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <%@ include file="../include/include.jsp"%>
  <meta charset="UTF-8">
  <title>笔记列表</title>
  <link rel="stylesheet" type="text/css" href="//oimg.com/css/lib/bootstrap.min.css"/>
  <link rel="stylesheet" type="text/css" href="//oimg.com/css/note/note_list.css"/>
  <link rel="stylesheet" type="text/css" href="//oimg.com/css/note/note_left.css"/>
  
  <script type="text/javascript" src="//oimg.com/js/lib/jquery-1.11.2.min.js"></script>
  <script type="text/javascript" src="//oimg.com/js/lib/bootstrap.min.js"></script>
  <script type="text/javascript" src="//oimg.com/js/lib/jquery-ias.min.js"></script>
  
  <script type="text/javascript" src="//oimg.com/js/note/note_list.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/view/include/header.jsp"%>
<div class="container">
	<div class="row">
		<%@ include file="/WEB-INF/view/note/_note_left.jsp"%>
		<div class="col-md-9">
            <%@ include file="/WEB-INF/view/note/_note_list.jsp"%>
		</div>
	</div>
</div>
<script type="text/javascript">
$(document).ready(function(){
	initias();
});
</script>
</body>
</html>
