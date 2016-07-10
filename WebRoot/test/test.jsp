<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="../static/jquery/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../static/jquery/themes/icon.css">
<script type="text/javascript" src="../static/jquery/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="../static/jquery/jquery.easyui.min.js"></script>

</head>
<body>

	<!--  
	1. 把 jquery-easyui-1.3.5 复制到 tomcat 的 webapps 目录下
	2. 编辑 tomcat 的 conf/tomcat-user.xml 文件. 加入如下配置:
	<role rolename="manager-gui"/>
	<user username="a" password="a" roles="manager-gui"/>
	3. 编辑 tomcat 的 conf/web.xml 文件. 修改 DefaultServlet 的 listings 参数值为 true
	4. 点击 http://localhost:8080/ 下的 manager app 链接.
	或直接输入 http://localhost:8080/jquery-easyui-1.3.5/demo/tree/animation.html 地址。 
	5. 按官方给的例子, 在 eclipse 下把自己的页面跑起来. 
	-->

	<ul class="easyui-tree" data-options="url:'tree_data1.json',method:'get',animate:true"></ul>

</body>
</html>