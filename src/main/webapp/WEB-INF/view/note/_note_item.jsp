<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="col-md-12 note-item well" id="note${note.id }">
	<span class="pull-right action">
		<c:if test="${cid ne 'trash' }">
			<a href="/note/view/${note.id }" target="_blank"><span class="glyphicon glyphicon-new-window" title="新标签页查看"> </span></a>
			<c:if test="${note.starred }">
			<a href="javascript:void(0);" class="star"><span class="glyphicon glyphicon-star" title="取消星标"> </span></a>
			</c:if>
			<c:if test="${not note.starred }">
			<a href="javascript:void(0);" class="star"><span class="glyphicon glyphicon-star-empty" title="添加星标"> </span></a>
			</c:if>
			<a href="/note/edit/${note.id }" <c:if test="${not empty cid }">target="_blank"</c:if>><span class="glyphicon glyphicon-pencil" title="编辑"> </span></a>
		</c:if>
		<c:if test="${cid eq 'trash' }">
			<a href="javascript:void(0);" class="restore"><span class="glyphicon glyphicon-share-alt" title="还原"> </span></a>
		</c:if>
		<a href="javascript:void(0);" class="del"><span class="glyphicon glyphicon-trash" title="删除"> </span></a>
		<input class="hidden" value="${note.id }">
	</span>
	<div class="meta">
		<span class="glyphicon glyphicon-folder-open"> ${note.category.name } </span>
		<span>
		<span class="glyphicon glyphicon-tags" style="margin-right: 6px;margin-left: 12px"></span> 
		<c:forEach items="${note.tags }" var="tag">
		<span class="label label-info">${tag.name }</span>
		</c:forEach>
		</span>
		<span class="glyphicon glyphicon-calendar" style="margin-left: 8px">修改时间：${note.modifyTime} </span>
	</div>
	<div>${note.content }</div>
</div>