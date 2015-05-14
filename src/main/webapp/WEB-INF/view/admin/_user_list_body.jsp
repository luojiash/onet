<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.onet.user.constant.Role" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:forEach items="${userRes.list}" var="user">
<tr class="list-users">
	<td>${user.id}</td>
	<td>${user.email}</td>
	<td>${user.userName }</td>
	<td>${user.nickName }</td>
	<td>
		<div class="btn-group">
			<a class="btn btn-mini dropdown-toggle" data-toggle="dropdown" href="#">${user.role } <span class="caret"></span></a>
			<ul class="dropdown-menu role">
				<li class="dropdown-header">Change Role</li>
				<c:forEach items="<%= Role.values()%>" var="role">
				<li><a href="#">${role}</a></li>
				</c:forEach>
			</ul>
		</div>
	</td>
	<td>${user.registerDate }</td>
	<td>
	<c:if test="${user.active}"><span><a href="#" class="label label-success">Active</a></span></c:if>
	<c:if test="${not user.active}"><span><a href="#" class="label label-default">Inactive</a></span></c:if>
	</td>
</tr>
</c:forEach>
<script type="text/javascript">
$(function() {
	bindLabel();
	bindDropdown();
});
</script>