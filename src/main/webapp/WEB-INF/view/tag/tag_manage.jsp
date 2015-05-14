<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.onet.user.constant.Role" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<%@ include file="/WEB-INF/view/include/include.jsp"%>
	<meta charset="UTF-8">
	<title>管理我的标签</title>
	<link rel="stylesheet" type="text/css" href="//oimg.com/css/lib/bootstrap.min.css" />
	<style type="text/css">
	.label {background-color: rgb(125, 192, 247);margin: 0px 3px;padding: 5px 8px;font-size: 16px}
	.label-container{line-height: 32px;}
	</style>
</head>
<body>
<%@ include file="/WEB-INF/view/include/header.jsp"%>
<div class="container">
	<div class="row">
      <div class="col-md-12">
    	<h3>文章标签管理</h3>
        <hr>
    	<div class="label-container">
        	<c:forEach items="${atags }" var="tag">
        	<div class="label">
        		<span>${tag.name }</span>（${tag.referCount }）
        		<a href="javascript:void(0);" class="rename">
        			<span class="glyphicon glyphicon-pencil" data-toggle="tooltip" title="重命名"> </span>
        		</a>
        		<a href="javascript:void(0);" class="del">
        			<span class="glyphicon glyphicon-trash" data-toggle="tooltip" title="删除"> </span>
        		</a>
        		<input class="hidden" value="${tag.id }">
        	</div>
        	</c:forEach>
    	</div>
      </div>
      <div class="col-md-12">
        <h3>笔记标签管理</h3>
        <hr>
        <div class="label-container">
            <c:forEach items="${ntags }" var="tag">
            <div class="label">
              <span>${tag.name }</span>（${tag.referCount }）
              <a href="javascript:void(0);" class="rename">
                <span class="glyphicon glyphicon-pencil" data-toggle="tooltip" title="重命名"> </span>
              </a>
              <a href="javascript:void(0);" class="del">
                <span class="glyphicon glyphicon-trash" data-toggle="tooltip" title="删除"> </span>
              </a>
              <input class="hidden" value="${tag.id }">
            </div>
            </c:forEach>
        </div>
      </div>
	</div>
</div>	

<div class="modal fade" id="tag-edit-modal">
  <div class="modal-dialog">
    <div class="modal-content">
    <form class="form">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">重命名标签</h4>
      </div>
      <div class="modal-body">
        <input class="hidden" name="id">
        <input class="form-control" name="name" placeholder="输入标签" required>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="submit" class="btn btn-primary">保存</button>
      </div>
    </form>
    </div>
  </div>
</div>

	<script type="text/javascript" src="//oimg.com/js/lib/jquery-1.11.2.min.js"></script>
	<script type="text/javascript" src="//oimg.com/js/lib/bootstrap.min.js"></script>
	
	<script type="text/javascript">
		$('[data-toggle=tooltip]').tooltip();
		// 初始化重命名按钮
		$('a.rename').click(function(){
			_this = $(this);
			var id = _this.siblings('input').val();
			$('#tag-edit-modal input:first').val(id);
			$('#tag-edit-modal input:last').val(_this.siblings('span').text());
			$('#tag-edit-modal').modal();
		});
		$('#tag-edit-modal').on('shown.bs.modal', function (e) {
			$(this).find('input:last').focus();
		});
		
		$('#tag-edit-modal form').submit(function(e){
			e.preventDefault();
			$.ajax({
				type: "POST",
				url: '/tag/rename',
				data: $(this).serialize(),
				error: function(data) {
					alert("tag edit error");
				},
				success: function(data){
					if(data.result){
						$('#tag-edit-modal').modal('hide');
						var name = $('input[name=name]', '#tag-edit-modal form').val();
						_this.siblings('span').text(name);
					} else {
						alert(data.returnMessage);
					}
				}
			});
		});
		// 初始化删除按钮
		$('a.del').click(function(){
			_this = $(this);
			var id = _this.siblings('input').val();
			var msg = '确定删除标签"'+_this.siblings('span').text()+'"？该操作不可撤销！'
			if(confirm(msg)){
				$.ajax({
					type: "POST",
					url: "/tag/delete",
					data: 'tid='+id,
					error: function(data) {
						alert("tag delete error");
					},
					success: function(data){
						if(data.result){
							_this.parent().fadeOut().remove();
						} else {
							alert(data.returnMessage);
						}
					}
				});
			}
		});
	</script>
</body>
</html>
