<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<div class="col-md-12 well article-item" id="article${article.id }">
  <c:if test="${not sharePage }">
    <h2 style="display: inline">
      <a href="/article/detail/${article.id }">${article.title }</a>
      <span style="color:red"><c:if test="${article.trashed }">（已删除）</c:if></span>
    </h2>
  </c:if>
  <c:if test="${sharePage }">
    <h2 style="display: inline">
      <a href="/article/share/detail/${share.id }">${article.title }</a>
      <span style="color:red"></span>
    </h2>
  </c:if>
  <c:if test="${article.userid eq userid }">
    <span class="pull-right action">
      <c:if test="${not article.trashed }">
        <c:if test="${not article.shared }">
          <a href="javascript:void(0);" class="share"><span class="glyphicon glyphicon-share" title="创建分享链接"></span></a>
        </c:if>
        <c:if test="${article.shared }">
          <a href="javascript:void(0);" class="cancelshare"><span class="glyphicon glyphicon-remove" title="取消分享"></span></a>
        </c:if>
        <a href="/article/edit/${article.id }"><span class="glyphicon glyphicon-pencil" title="编辑"></span></a>
      </c:if>
      <c:if test="${article.trashed }">
        <a href="javascript:void(0);" class="restore"><span class="glyphicon glyphicon-share-alt" title="还原"></span></a>
      </c:if>
      <a href="javascript:void(0);" class="del">
        <span class="glyphicon glyphicon-trash" title="删除"></span>
      </a>
      <input class="hidden" value="${article.id }">
    </span>
  </c:if>
  <div class="meta">
    <span class="glyphicon glyphicon-calendar"><fmt:formatDate value="${article.createTime }"/></span>
    <span class="glyphicon glyphicon-folder-open" style="margin-left: 8px"> ${article.category.name } </span>
    <span>
      <span class="glyphicon glyphicon-tags" style="margin-right: 6px;margin-left: 12px"></span> 
      <c:forEach items="${article.tags }" var="tag">
        <span class="label label-info">${tag.name }</span>
      </c:forEach>
    </span>
  </div>
  <c:if test="${not empty articleId || not empty shareId}">
    <div>${article.content }</div>
  </c:if>
</div>