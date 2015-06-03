<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<header>
<nav class="navbar navbar-default navbar-static-top">
<div class="container">
	<div class="navbar-header">
		<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".header-navbar-collapse">
		    <span class="sr-only">Toggle navigation</span>
		    <span class="icon-bar"></span>
		    <span class="icon-bar"></span>
		    <span class="icon-bar"></span>
		</button>
		<a class="navbar-brand" href="#">ONET</a>			
	</div>
	<div class="collapse navbar-collapse header-navbar-collapse">
		<ul class="nav navbar-nav">
            <li class="dropdown">
              <a href="" data-toggle="dropdown" class="dropdown-toggle"><span class="glyphicon glyphicon-book"></span> 文章 <b class="caret"></b></a>
              <ul class="dropdown-menu" role="menu">
                <li><a href="/article/list">文章列表</a></li>
                <li><a href="/article/category/manage">分类管理</a></li>
                <li><a href="/article/share">我的分享</a></li>
                <li><a href="/article/edit">写文章</a></li>
              </ul>
            </li>
			<li class="dropdown">
				<a href="" data-toggle="dropdown" class="dropdown-toggle"><span class="glyphicon glyphicon-book"></span> 笔记 <b class="caret"></b></a>
				<ul class="dropdown-menu" role="menu">
                    <li><a href="/note/list">笔记列表</a></li>
                    <li><a href="/note/edit"><span class="glyphicon glyphicon-plus"></span> 新建笔记</a></li>
				</ul>
			</li>
            <li><a href="/tag/manage"><span class="glyphicon glyphicon-cog"></span> 标签管理</a></li>			
		</ul>
		<ul class="nav navbar-nav navbar-right">
			<c:if test="${role eq 'ADMIN' }">
			<li><a href="/admin/user/list"><span class="glyphicon glyphicon-wrench"></span> 用户管理</a></li>
			</c:if>
			<li class="dropdown">
				<a href="#" data-toggle="dropdown" class="dropdown-toggle"><span class="glyphicon glyphicon-user"></span> ${username } <b class="caret"></b></a>
				<ul class="dropdown-menu" role="menu">
                  <li><a href="/user/setting"><span class="glyphicon glyphicon-cog"></span> 系统设置</a></li>		
                  <li><a href="/user/profile"><span class="glyphicon glyphicon-user"></span> 账户信息</a></li>
				  <li class="divider"></li>
				  <li><a href="/user/logout"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
				</ul>
			</li>
		</ul>
	</div>
</div>
</nav>
</header>
