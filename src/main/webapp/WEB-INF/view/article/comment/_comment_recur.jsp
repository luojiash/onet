<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
    <li class="" id="comment${comment.id }">
      <div>
        <div><b>@${comment.username }</b></div>
        <div>
          ${comment.content }
        </div>
        <div class="action">
          <span title='<fmt:formatDate value="${comment.commentDate }" pattern="yyyy-MM-dd HH:mm:ss"/>'><fmt:formatDate value="${comment.commentDate }"/></span>
          <a href="javascript:void(0);" class="reply-btn"><span class="glyphicon glyphicon-share-alt"></span> 回复</a>
          <input class="hidden" value="${comment.id }">
        </div>
      </div>
      <hr>
      <ul>
        <c:if test="${comment.replyCount > 0 }">
          <c:forEach items="${comment.replies }" var="comment">
            <c:set var="comment" value="${comment }" scope="request"></c:set>
            <jsp:include page="/WEB-INF/view/article/comment/_comment_recur.jsp"/>
          </c:forEach>
        </c:if>
      </ul>
    </li>
