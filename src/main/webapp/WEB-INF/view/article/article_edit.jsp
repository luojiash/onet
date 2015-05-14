<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <%@ include file="../include/include.jsp"%>
  <meta charset="UTF-8">
  <title>编辑文章</title>
  <link rel="stylesheet" type="text/css" href="//oimg.com/css/lib/bootstrap.min.css" />
  <link rel="stylesheet" type="text/css" href="//oimg.com/css/lib/jquery-ui.min.css" />
  <link rel="stylesheet" type="text/css" href="//oimg.com/css/lib/jquery.tagit.css" />
  <link rel="stylesheet" type="text/css" href="//oimg.com/css/lib/tagit.ui-zendesk.css" />
  <link rel="stylesheet" type="text/css" href="//oimg.com/css/lib/simditor/simditor.css" />
  <link rel="stylesheet" type="text/css" href="//oimg.com/css/lib/toastr.min.css" />
  <style type="text/css">
  
  </style>
</head>
<body>
<%@ include file="/WEB-INF/view/include/header.jsp"%>
<div class="container">
  <div class="row">
  	<div class="col-md-12">
      <form class="form form-horizontal">
        <div class="form-group">
          <div class="col-sm-3">
            <select name="categoryId" class="form-control">
            <option value="">-----选择目录-----</option>
            <c:forEach items="${categories }" var="category">
              <option value="${category.id }" <c:if test="${article.category.id eq category.id}">selected</c:if>>${category.name }</option>
            </c:forEach>
            </select>
          </div>
          <div class="col-sm-9">
            <input type="text" class="form-control" name="title" value="${article.title}" placeholder="输入标题" required>
            <input class="hidden" id="aid" name="id" value="${article.id }">
          </div>
        </div>  
      	<div class="form-group">
          <div class="col-sm-12">
      		<textarea id="content" name="content" placeholder="这里输入内容">${article.content}</textarea>
          </div>
      	</div>
      	<div class="form-group">
          <div class="col-sm-12">
      		<ul id="tags">
      		<c:forEach items="${article.tags}" var="tag">
      			<li>${tag.name}</li>
      		</c:forEach>
      		</ul>
          </div>
      	</div>
        <div class="form-group">
          <div class="col-sm-12">
            <button type="submit" class="btn btn-default">保存</button>
          </div>
        </div>
      </form>
  	</div>
  </div>
</div>
	
  <script type="text/javascript" src="//oimg.com/js/lib/jquery-1.11.2.min.js"></script>
  <script type="text/javascript" src="//oimg.com/js/lib/bootstrap.min.js"></script>
  
  <script type="text/javascript" src="//oimg.com/js/lib/simditor/module.js"></script>
  <script type="text/javascript" src="//oimg.com/js/lib/simditor/hotkeys.js"></script>
  <script type="text/javascript" src="//oimg.com/js/lib/simditor/uploader.js"></script>
  <script type="text/javascript" src="//oimg.com/js/lib/simditor/simditor.js"></script>
  <script type="text/javascript" src="//oimg.com/js/lib/simditor/simditor-markdown.js"></script>
  
  <script type="text/javascript" src="//oimg.com/js/lib/jquery-ui.min.js"></script>
  <script type="text/javascript" src="//oimg.com/js/lib/tag-it.min.js"></script>
  <script type="text/javascript" src="//oimg.com/js/lib/toastr.min.js"></script>
  
  <script type="text/javascript" src="//oimg.com/js/common/simditor.js"></script>
	
	<script type="text/javascript">
	$(document).ready(function(){
	
	// init tag-it
	var sampleTags = [];
	<c:forEach items="${tags}" var="tag">
	sampleTags.push('${tag.name}');
	</c:forEach>
	$("#tags").tagit({
		removeConfirmation: true,
		availableTags: sampleTags,
		placeholderText: '添加标签',
	});
		
	// 保存笔记
	$('form').submit(function(e){
		e.preventDefault();
		var data = {};
		data.title = $('input[name=title]', this).val();
		data.content = editor.getValue();
		data.tagList = $("#tags").tagit("assignedTags");
		var cid = $('select[name=categoryId]', this).val();
		if(cid != '') {
			var category = {};
			category.id = cid;
			data.category = category;
		}
		var id = $('#aid').val();
		if(id != '') data.id = id;
		$.ajax({
		   type: "POST",
		   url: id=='' ? "/article/add" : "/article/update",
		   data: JSON.stringify(data),
		   contentType: 'application/json;charset=UTF-8',
		   error: function(data) {
	           alert("edit article error");
	       },
		   success: function(data){
			   if (data.result) {
				   toastr.success('保存成功');
				   setTimeout('window.location.href="/article/detail/'+data.returnCode+'"',1000);
			   } else {
				   alert(data.returnMessage);
			   }
		   }
		});
	});
	});
	</script>
</body>
</html>
