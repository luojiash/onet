<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <%@ include file="../include/include.jsp"%>
  <meta charset="UTF-8">
  <title>文章-${article.title }</title>
  <link rel="stylesheet" type="text/css" href="//oimg.com/css/lib/bootstrap.min.css" />
  <link rel="stylesheet" type="text/css" href="//oimg.com/css/lib/toastr.min.css" />
  <link rel="stylesheet" type="text/css" href="//oimg.com/css/lib/simditor/simditor.css" />
  <link rel="stylesheet" type="text/css" href="//oimg.com/css/article/article_detail.css" />
</head>
<body>
<%@ include file="/WEB-INF/view/include/header.jsp"%>
<div class="container">
	<div class="row">
		<%@ include file="/WEB-INF/view/article/_article_item.jsp"%>
      <div class="col-md-12" id="comment-container"></div>
	</div>
</div>
  <%@ include file="/WEB-INF/view/article/template/modal.jsp"%>

  <script type="text/javascript" src="//oimg.com/js/lib/jquery-1.11.2.min.js"></script>
  <script type="text/javascript" src="//oimg.com/js/lib/bootstrap.min.js"></script>
  <script type="text/javascript" src="//oimg.com/js/lib/toastr.min.js"></script>
  <script type="text/javascript" src="//oimg.com/js/lib/simditor/module.js"></script>
  <script type="text/javascript" src="//oimg.com/js/lib/simditor/hotkeys.js"></script>
  <script type="text/javascript" src="//oimg.com/js/lib/simditor/uploader.js"></script>
  <script type="text/javascript" src="//oimg.com/js/lib/simditor/simditor.js"></script>
  
  <script type="text/javascript" src="//oimg.com/js/article/article_item.js"></script>
  
  <script type="text/javascript">
$(document).ready(function(){
	initArticleItem($('.row'), Boolean('${article.trashed}'));
	loadComment('${article.id}', $('#comment-container'));
});
  	
  </script>
</body>
</html>
