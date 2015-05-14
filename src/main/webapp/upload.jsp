<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>test</title>
	<link rel="stylesheet" type="text/css" href="//oimg.com/css/lib/bootstrap.min.css" />
</head>
<body>
<div class="container">
  <form method="POST" action="/upload" enctype="multipart/form-data">
    <div class="form-group">
      <label for="exampleInputEmail1">Email address</label>
      <input type="email" class="form-control" name="email" id="exampleInputEmail1" placeholder="Enter email">
    </div>
    <div class="form-group">
      <label for="exampleInputFile">File input</label>
      <input type="file" name="mfile" id="exampleInputFile">
      <p class="help-block">Example block-level help text here.</p>
    </div>
    <button type="submit" class="btn btn-default">Submit</button>
  </form>
</div>

	<script type="text/javascript" src="//oimg.com/js/lib/jquery-1.11.2.min.js"></script>
	<script type="text/javascript" src="//oimg.com/js/lib/bootstrap.min.js"></script>
</body>
</html>
