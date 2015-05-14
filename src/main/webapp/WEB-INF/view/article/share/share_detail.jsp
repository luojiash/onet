<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <%@ include file="/WEB-INF/view/include/include.jsp"%>
  <meta charset="UTF-8">
  <title>分享文章-${share.article.title }</title>
  <link rel="stylesheet" type="text/css" href="//oimg.com/css/lib/bootstrap.min.css" />
  <link rel="stylesheet" type="text/css" href="//oimg.com/css/lib/toastr.min.css" />
  <link rel="stylesheet" type="text/css" href="//oimg.com/css/lib/simditor/simditor.css" />
  <link rel="stylesheet" type="text/css" href="//oimg.com/css/article/article_detail.css" />
</head>
<body>
<%@ include file="/WEB-INF/view/include/header.jsp"%>
<div class="container">
	<div class="row">
      <c:set var="article" value="${share.article }"></c:set>
      <div class="alert">
        <span class="label">@${article.username }</span>
        <span class="label"><fmt:formatDate value="${share.shareDate }" pattern="yyyy-MM-dd HH:mm"/></span>
        <span class="label">阅读 ${article.readCount }</span>
      </div>
      <%@ include file="/WEB-INF/view/article/_article_item.jsp"%>
      <div class="col-md-12" id="comment-container"></div>
	</div>
</div>

  <script type="text/javascript" src="//oimg.com/js/lib/jquery-1.11.2.min.js"></script>
  <script type="text/javascript" src="//oimg.com/js/lib/bootstrap.min.js"></script>
  <script type="text/javascript" src="//oimg.com/js/lib/toastr.min.js"></script>
  <script type="text/javascript" src="//oimg.com/js/lib/simditor/module.js"></script>
  <script type="text/javascript" src="//oimg.com/js/lib/simditor/hotkeys.js"></script>
  <script type="text/javascript" src="//oimg.com/js/lib/simditor/uploader.js"></script>
  <script type="text/javascript" src="//oimg.com/js/lib/simditor/simditor.js"></script>
  
  <script type="text/javascript" src="//oimg.com/js/article/article_item.js"></script>
  
  <script type="text/javascript">
  	initArticleItem($('.row'), Boolean('${share.article.trashed}'));
  	loadComment('${article.id}', $('#comment-container'));
  </script>
</body>
</html>
