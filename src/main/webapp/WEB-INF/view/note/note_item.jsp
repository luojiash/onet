<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<%@ include file="../include/include.jsp"%>
	<meta charset="UTF-8">
	<title>笔记详情</title>
	<link rel="stylesheet" type="text/css" href="//oimg.com/css/lib/bootstrap.min.css" />
	<link rel="stylesheet" type="text/css" href="//oimg.com/css/note/note_list.css" />
	
	<script type="text/javascript" src="//oimg.com/js/lib/jquery-1.11.2.min.js"></script>
	<script type="text/javascript" src="//oimg.com/js/lib/bootstrap.min.js"></script>
	
	<script type="text/javascript" src="//oimg.com/js/note/note_list.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/view/include/header.jsp"%>
<div class="container">
	<div class="row">
	<c:if test="${noteRes.rs eq 1 }">
		<c:set var="note" value="${noteRes.data }"></c:set>
		<%@ include file="/WEB-INF/view/note/_note_item.jsp"%>
	</c:if>
	<c:if test="${noteRes.rs eq 0 }"><p>${noteRes.msg}</p></c:if>
	</div>
</div>
<script type="text/javascript">
initNoteItem($('.note-item'),'${note.category.id}');
</script>
</body>
</html>
