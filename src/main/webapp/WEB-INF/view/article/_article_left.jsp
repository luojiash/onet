<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="col-md-3">
	<div class="panel panel-info" id="article-category-list">
        <div class="panel-heading">文章分类 
          <a class="pull-right" href="/article/category/manage" target="_blank">
            <span class="glyphicon glyphicon-wrench" title="管理文章分类"></span>
          </a>
        </div>
  		<div class="">
            <c:forEach items="${categories }" var="category">
            <a class="list-group-item <c:if test="${category.id eq cid }">active</c:if>" href="/article/list/category/${category.id }">
              <span class="badge">${category.articleCount }</span> ${category.name }
            </a>
            </c:forEach>
        </div>
	</div> <!-- category -->

  <div class="panel panel-info" id="note-tag-list">
  	<div class="panel-heading">文章标签 </div>
  	<div>
      <c:forEach items="${tags }" var="tag">
      	<a href="/article/list/tag/${tag.id }" class="list-group-item <c:if test="${tag.id eq tid }">active</c:if>">
          <span class="badge">${tag.referCount }</span> ${tag.name }
        </a>
      </c:forEach>
  	</div>
  </div> <!-- tags -->
  <div class="panel panel-info">
    <div class="">
      <a href="/article/share" class="list-group-item list-group-item-success <c:if test="${share }">active</c:if>">我的分享</a>
    </div>
  </div>
  <div class="panel">
    <div class="">
      <a href="/article/trash" class="list-group-item list-group-item-warning <c:if test="${trash }">active</c:if>">回收站</a>
    </div>
  </div>
</div> <!-- left -->