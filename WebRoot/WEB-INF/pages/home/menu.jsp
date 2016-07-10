<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${ctp}/static/jquery/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctp}/static/jquery/themes/icon.css">
<script type="text/javascript" src="${ctp}/static/jquery/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${ctp}/static/jquery/jquery.easyui.min.js"></script>
<script type="text/javascript">
	$(function(){
		//alert("haha");
		/* $("#tree").tree({
			onClick:function(node){
				if(node.attributes.url){
					alert(node.attributes.url);
					window.parent.document.getElementById("content").src=node.attributes.url;
				}
			}
		}); */
		$('#tree').tree({
			onClick: function(node){
				if(node.attributes.url){
					//alert(node.attributes.url);
					window.parent.document.getElementById("content").src = node.attributes.url;
				}
			}
		});
	})
</script>
</head>
<body>

	<ul id="tree" class="easyui-tree" data-options="url:'${ctp}/test/tree_data1.json',method:'get',animate:true"></ul>

</body>
</html>