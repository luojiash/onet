<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style type="text/css">
a.reply-btn {text-decoration: none;padding-left: 6px;} 
#comment-list ul {list-style: none;}
#comment-list li > div > div:nth-child(1) {padding-bottom: 8px;}
</style>
<div class="row">
  <div class="col-md-12">
    <div id="comment-list">
      <ul>
        <c:forEach items="${comments }" var="comment">
          <%@ include file="/WEB-INF/view/article/comment/_comment_recur.jsp" %>
        </c:forEach>	
      </ul>
    </div>
    <form class="form reply-form">
      <div class="form-group">
        <input class="hidden" name="pid" value="-1">
        <input class="hidden" name="articleId" value="${articleId }">
        <textarea id="reply-content" name="content" rows="1" placeholder="输入回复内容"></textarea>
      </div>
      <button type="submit" class="btn btn-default">回复</button>
    </form>
  </div>
</div>

<div class="reply-box" style="display: none;">
  <form class="common">
    <div class="form-group">
      <input class="hidden" name="pid" value="">
      <input class="hidden" name="articleId" value="${articleId }">
      <textarea id="inline-reply-content" class="form-control" name="content" placeholder="输入回复内容"></textarea>
    </div>
    <button type="submit" class="btn btn-default">回复</button>
  </form>
</div>

<script type="text/javascript">

$(document).ready(function(){
	var editor = new Simditor({
	    textarea: $('#reply-content'),
	    toolbar: ['bold','italic','blockquote','code','link','hr'],
	});
	
	initReplyBtn($('#comment-list .reply-btn'));
	initForm($('form'));
});
</script>