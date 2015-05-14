<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <%@ include file="../include/include.jsp"%>
  <meta charset="UTF-8">
  <title>编辑笔记</title>
  <link rel="stylesheet" type="text/css" href="//oimg.com/css/lib/bootstrap.min.css" />
  <link rel="stylesheet" type="text/css" href="//oimg.com/css/lib/jquery-ui.min.css" />
  <link rel="stylesheet" type="text/css" href="//oimg.com/css/lib/jquery.tagit.css" />
  <link rel="stylesheet" type="text/css" href="//oimg.com/css/lib/tagit.ui-zendesk.css" />
  <link rel="stylesheet" type="text/css" href="//oimg.com/css/lib/simditor/simditor.css" />
  <link rel="stylesheet" type="text/css" href="//oimg.com/css/lib/toastr.min.css" />
  <style type="text/css">
  .tree .dropdown{display: none;}
  .tree ul{list-style: none;padding-left: 10px;}
  .tree a{text-decoration: initial;color: #333;}
  .tree li > div.active {background-color: #D9D9D9;}
  </style>
</head>
<body>
<%@ include file="/WEB-INF/view/include/header.jsp"%>
<div class="container">
	<div class="row">
      <div class="col-md-3">
        <div class="panel panel-info">
          <input class="hidden" id="cid" value="${note.category.id }">
          <div class="panel-heading">选择目录 </div>
          <div class="tree">
            <ul>
              <c:forEach items="${categories }" var="category">
                <%@ include file="/WEB-INF/view/note/dir/_cate_recur.jsp" %>
              </c:forEach>	
            </ul>
          </div>
        </div>
      </div>
      <div class="col-md-9">
		<form class="form">
            <input class="hidden" id="note-id" value="${note.id }">
			<div class="form-group">
				<textarea id="content" name="content" placeholder="这里输入内容" autofocus>${note.content}</textarea>
			</div>			
			<div class="form-group">
				<label class="control-label">Tags</label>
				<ul id="tags">
				<c:forEach items="${note.tags}" var="tag">
					<li>${tag.name}</li>
				</c:forEach>
				</ul>
			</div>			
			<div class="checkbox">
				<label>
					<input name=starred type="checkbox" <c:if test="${note.starred}">checked</c:if>> 添加星标
				</label>
			</div>
			<button type="submit" class="btn btn-success">保存</button>
		</form>
      </div>
	</div>
</div>	
	
  <script type="text/javascript" src="//oimg.com/js/lib/jquery-1.11.2.min.js"></script>
  <script type="text/javascript" src="//oimg.com/js/lib/bootstrap.min.js"></script>
  <script type="text/javascript" src="//oimg.com/js/lib/jquery-ui.min.js"></script>
  <script type="text/javascript" src="//oimg.com/js/lib/tag-it.min.js"></script>
  <script type="text/javascript" src="//oimg.com/js/lib/toastr.min.js"></script>
	
  <script type="text/javascript" src="//oimg.com/js/lib/simditor/module.js"></script>
  <script type="text/javascript" src="//oimg.com/js/lib/simditor/hotkeys.js"></script>
  <script type="text/javascript" src="//oimg.com/js/lib/simditor/uploader.js"></script>
  <script type="text/javascript" src="//oimg.com/js/lib/simditor/simditor.js"></script>
  <script type="text/javascript" src="//oimg.com/js/lib/simditor/simditor-markdown.js"></script>
  
  <script type="text/javascript" src="//oimg.com/js/note/note_left.js"></script>
  <script type="text/javascript" src="//oimg.com/js/common/simditor.js"></script>
	
	<script type="text/javascript">
	$(document).ready(function(){
	initCateCollapse($('.tree li > div > a:nth-child(1)'), '${note.category.id}');
	$('.tree li > div > a:nth-child(2)').click(function(e){
		e.preventDefault();
		$('.tree li > div.active').removeClass('active');
		$(this).parent().addClass('active');
		var href = $(this).attr('href');
		$('#cid').val(href.substring(href.indexOf('cid=')+4));
	});
	});
	
	var sampleTags = [];
	<c:forEach items="${tags}" var="tag">
	sampleTags.push('${tag.name}');
	</c:forEach>
	$("#tags").tagit({
		removeConfirmation: true,
		availableTags: sampleTags,
	});
	
	// 保存笔记
	$('form').submit(function(e){
		e.preventDefault();
		var data = {};
		data.content = editor.getValue();
		data.tagList = $("#tags").tagit("assignedTags");
		data.dirId = $('#cid').val();
		data.starred = $('input[name=starred]', this).is(':checked');
		var id = $('#note-id').val();
		if(id != '') data.id = id;
		$.ajax({
		   type: "POST",
		   url: id=='' ? "/note/add/submit" : "/note/edit/submit",
		   data: JSON.stringify(data),
		   dataType: 'json',
		   contentType: 'application/json;charset=UTF-8',
		   error: function(data) {
	           alert("edit note error");
	       },
		   success: function(data){
			   if (data.result) {
				   toastr.success('保存成功');
				   setTimeout('window.location.href="/note/list?cid='+$('#cid').val()+'"',1000);
			   } else {
				   alert(data.returnMessage);
			   }
		   }
		});
	});
	</script>
</body>
</html>
