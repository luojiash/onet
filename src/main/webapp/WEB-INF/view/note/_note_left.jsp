<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="col-md-3">
  <div class="panel panel-info" id="note-category-list">
    <a class="list-group-item list-group-item-warning <c:if test="${not star }">active</c:if>" href="/note/list"><span class="glyphicon glyphicon-book"></span> 所有笔记 <span class="badge" id="allnote-badge">0</span></a>
    <c:if test="${not star }">
      <div class="tree">
        <ul>
          <c:forEach items="${categories }" var="category">
            <%@ include file="/WEB-INF/view/note/dir/_cate_recur.jsp" %>
          </c:forEach>	
        </ul>
      </div>
    </c:if>
  </div> <!-- category -->

  <div class="panel panel-info" id="note-tag-list">
  	<div class="panel-heading">选择标签 
      <a href="javascript:void(0);" class="reset"><span class="glyphicon glyphicon-refresh" title="重置"></span></a>
      <input class="hidden" id="tids" value="">
      <div class="pull-right">
      	<label>
          <input type="checkbox">多选
      	</label>
      </div>
  	</div>
  	<div class="panel-body">
  	<c:forEach items="${tags }" var="tag">
  		<a href="#${tag.id }" class="label label-default">${tag.name }</a>
  	</c:forEach>
  	</div>
  </div> <!-- tags -->
  
  <div class="panel panel-info">
    <div class="list-group">
      <a class="list-group-item list-group-item-success <c:if test="${star }">active</c:if>" href="/note/list/star"><span class="glyphicon glyphicon-star"></span> 星标 <span class="badge" id="star-badge">0</span></a>
    </div>
  </div>
</div> <!-- left -->

<div class="modal fade" id="category-edit-modal">
  <div class="modal-dialog">
    <div class="modal-content">
    <form class="form">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">编辑目录</h4>
      </div>
      <div class="modal-body">
        <input class="hidden" name="op">
        <input class="hidden" name="id">
        <input class="hidden" name="pid">
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

<script type="text/javascript" src="//oimg.com/js/note/note_left.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	queryNoteCount($('#star-badge'), 'starred=1');
	queryNoteCount($('#allnote-badge'));
	initCateCollapse($('.tree li > div > a:nth-child(1)'), "${param['cid']}");
});
</script>