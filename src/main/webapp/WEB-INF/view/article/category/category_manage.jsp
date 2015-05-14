<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<%@ include file="/WEB-INF/view/include/include.jsp"%>
	<meta charset="UTF-8">
	<title>文章目录管理</title>
	<link rel="stylesheet" type="text/css" href="//oimg.com/css/lib/bootstrap.min.css" />
</head>
<body>
<%@ include file="/WEB-INF/view/include/header.jsp"%>
<div class="container">
	<div class="row">
		<div class="">
			<h3>文章目录 </h3>
		</div>
        <hr>
		<a class="btn btn-default" id="new-category"><span class="glyphicon glyphicon-plus"></span> 新建目录</a>
		<table class="table table-striped">
			<thead>
				<tr>
					<th>目录名</th>
					<th>笔记数目</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${categories}" var="category">
			<tr>
				<td id="cname${category.id }">${category.name}</td>
				<td>${category.articleCount}</td>
				<td>
					<a href="javascript:void(0);" class="rename"><span class="glyphicon glyphicon-pencil" data-toggle="tooltip" title="重命名"> </span></a>
					<a href="javascript:void(0);" class="del"><span class="glyphicon glyphicon-trash" data-toggle="tooltip" title="删除"> </span></a>
					<input class="hidden" value="${category.id }">
				</td>
			</tr>
			</c:forEach>
			</tbody>
		</table>
	</div> <!-- /.category list -->
</div>	

<div class="modal fade" id="category-edit-modal">
  <div class="modal-dialog">
    <div class="modal-content">
    <form class="form">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">编辑目录</h4>
      </div>
      <div class="modal-body">
        <input class="hidden">
        <input class="form-control" placeholder="输入目录名" required>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="submit" class="btn btn-primary">保存</button>
      </div>
    </form>
    </div>
  </div>
</div><!-- /.category edit modal -->

	<script type="text/javascript" src="//oimg.com/js/lib/jquery-1.11.2.min.js"></script>
	<script type="text/javascript" src="//oimg.com/js/lib/bootstrap.min.js"></script>
	
	<script type="text/javascript">
		// 初始化重命名按钮
		$('a.rename').click(function(){
			_this = $(this);
			var cid = _this.siblings('input').val();
			$('#category-edit-modal input.hidden').val(cid);
			$('#category-edit-modal input:last').val($('#cname'+cid).text());
			$('#category-edit-modal').modal();
		});
		// 初始化新建目录按钮
		$('#new-category').click(function(){
			$('#category-edit-modal input').val('');
			$('#category-edit-modal').modal();
		});
		$('#category-edit-modal').on('shown.bs.modal', function (e) {
			$(this).find('input:last').focus();
		});
		$('form').submit(function(e){
			e.preventDefault();
			var cid = $('#category-edit-modal input.hidden').val();
			var name = $('#category-edit-modal input:last').val();
			if(cid==''){
				data = {'oper': 'cre', 'cname': name};
			} else {
				data = {'oper': 'ren', 'cid': cid, 'cname': name};
			}
			$.ajax({
				type: "POST",
				url: '/article/category/oper',
				data: data,
				error: function(data) {
					alert("category edit error");
				},
				success: function(data){
					if(data.result){
						$('#category-edit-modal').modal('hide');
						if(cid==''){
							location.reload();
						} else {
							$('#cname'+cid).text(name);
						}
					} else {
						alert(data.returnMessage);
					}
				}
			});
		});
		// 初始化删除按钮
		$('a.del').click(function(){
			_this = $(this);
			var cid = _this.siblings('input').val();
			var msg = '确认删除"'+$('#cname'+cid).text()+'"？';
			if(confirm(msg)){
				$.ajax({
					type: "POST",
					url: '/article/category/oper',
					data: {'oper': 'del', 'cid': cid},
					error: function(data) {
						alert("category delete error");
					},
					success: function(data){
						if(data.result){
							_this.parents('tr').fadeOut().remove();
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
