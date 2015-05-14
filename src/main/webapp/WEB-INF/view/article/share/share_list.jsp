<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <%@ include file="/WEB-INF/view/include/include.jsp"%>
  <meta charset="UTF-8">
  <title>文章列表</title>
  <link rel="stylesheet" type="text/css" href="//oimg.com/css/lib/bootstrap.min.css"/>
  <link rel="stylesheet" type="text/css" href="//oimg.com/css/lib/simplePagination.css" />
  <link rel="stylesheet" type="text/css" href="//oimg.com/css/lib/toastr.min.css" />
  <style type="text/css">
  .meta {font-size: 13px;margin-top: 15px;}
  </style>
</head>
<body>
<%@ include file="/WEB-INF/view/include/header.jsp"%>
<div class="container">
	<div class="row">
		<%@ include file="/WEB-INF/view/article/_article_left.jsp"%>
		<div class="col-md-9">
            <div class="row" id="article-list">
              <c:forEach items="${shares }" var="share">
              <c:set var="article" value="${share.article }" scope="request"></c:set>
              <%@ include file="/WEB-INF/view/article/_article_item.jsp"%>
              </c:forEach>
            </div>
            <div id="pagination"></div>
		</div>
	</div>
</div>
  <%@ include file="/WEB-INF/view/article/template/modal.jsp"%>

  <script type="text/javascript" src="//oimg.com/js/lib/jquery-1.11.2.min.js"></script>
  <script type="text/javascript" src="//oimg.com/js/lib/bootstrap.min.js"></script>
  <script type="text/javascript" src="//oimg.com/js/lib/jquery.simplePagination.js"></script>
  <script type="text/javascript" src="//oimg.com/js/lib/toastr.min.js"></script>
  <script type="text/javascript" src="//oimg.com/js/article/article_item.js"></script>
  
<script type="text/javascript">
$(document).ready(function(){
	$('.article-item > span').hide();
	$('.article-item').hover(function(){
		$('>span', this).show();
	}, function(){
		$('>span', this).hide();
	});
	
	initArticleItem($('#article-list'), '${trash}');
	
	$('#pagination').pagination({
        items: "${userRes.total}",
        itemsOnPage: "${articleRes.pageSize}",
        currentPage: "${articleRes.pageNo}",
        hrefTextPrefix: "?page=",
    });
	
	$('#clear-trash').click(function(){
		if(confirm('确定清空回收站？')){
			$.ajax({
				type: "POST",
				url: "/article/trash/clear",
				error: function(data) {
					alert("clear trash error");
				},
				success: function(data){
					if(data.result){
						$('#article-list').fadeOut().remove();
					} else {
						alert(data.returnMessage);
					}
				}
			});
		}
	});
});
</script>
</body>
</html>
