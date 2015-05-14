<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <li class="" id="cate${category.id }">
      <div <c:if test="${note.dirId eq category.id || param['cid'] eq category.id}">class="active"</c:if>>
        <a class="btn">
          <span class="glyphicon <c:if test="${category.leaf }">glyphicon-minus</c:if> <c:if test="${not category.leaf }">glyphicon-plus</c:if>"></span>
        </a>
        <a href="/note/list/more?cid=${category.id }">
          <span id="cName${category.id }">${category.name }</span>
          <span class="badge">${category.noteCount }</span>
        </a>
        <div class="dropdown">
          <button class="btn btn-xs btn-default dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
            <span class="caret"></span>
            <span class="sr-only">Toggle Dropdown</span>
          </button>
          <ul class="dropdown-menu dropdown-menu-right" role="menu">
            <c:if test="${category.leaf }">
              <li><a href="#" id="del-${category.id }">删除</a></li>
            </c:if>
            <li><a href="#" id="ren-${category.id }">重命名</a></li> 
            <li><a href="#" id="cre-${category.id }">新建子文件夹</a></li>
          </ul>
        </div>
      </div>
      <ul class="collapse">
        <c:if test="${not category.leaf }">
          <c:forEach items="${category.children }" var="category">
            <c:set var="category" value="${category }" scope="request"></c:set>
            <jsp:include page="/WEB-INF/view/note/dir/_cate_recur.jsp"/>
          </c:forEach>
        </c:if>
      </ul>
    </li>
