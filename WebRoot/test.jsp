<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${ctp}/static/jquery/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctp}/static/jquery/themes/icon.css">
<script type="text/javascript" src="${ctp}/static/jquery/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${ctp}/static/jquery/jquery.easyui.min.js"></script>
</head>
<body>
	<h2>test</h2>
	<ul id="tree" class="easyui-tree" data-options="url:'${ctp}/test/tree_data1.json',method:'get',animate:true"></ul>
</body>
</html>