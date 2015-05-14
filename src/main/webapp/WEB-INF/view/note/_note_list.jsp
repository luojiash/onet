<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="row" id="note-list">
  <c:forEach items="${noteRes.list }" var="note">
  <%@ include file="/WEB-INF/view/note/_note_item.jsp"%>
  </c:forEach>
  
  <c:if test="${noteRes.pageNo eq 1}">
    <div id="pagination">
    <c:forEach begin="2" end="${noteRes.totalPage }" var="i">
    	<a href="/note/list/more?page=${i }&cid=${param['cid']}&tids=${param['tids']}" class="next"></a>
    </c:forEach>
    </div>
    <input class="hidden" id="cid" value="${param['cid']}">
    <script type="text/javascript">
    	initNoteItem($('.note-item'));
    </script>
  </c:if>
</div>


