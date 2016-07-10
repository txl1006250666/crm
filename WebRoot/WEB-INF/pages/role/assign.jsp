<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/commons/common.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>角色管理 - 分配权限</title>
	<script type="text/javascript">
		$(function(){
			$(":checkbox[class^=parent]").click(function(){
				var flag = $(this).is(":checked");
				var parentDisplayName = $(this).next(":hidden").val();
				$(".sub-" + parentDisplayName).prop("checked", flag);
			});
			
			$(":checkbox[class^=sub]").click(function(){
				var parentDisplayName = $(this).attr("class").split("-")[1];
				var flag = $(".sub-" + parentDisplayName + ":checked").length
					== $(".sub-" + parentDisplayName).length;
				
				$(":hidden[value='" + parentDisplayName + "']").prev(":checkbox").prop("checked", flag);
			});
			
			$(":checkbox[class^=sub]").each(function(){
				var parentDisplayName = $(this).attr("class").split("-")[1];
				var flag = $(".sub-" + parentDisplayName + ":checked").length
					== $(".sub-" + parentDisplayName).length;
				
				$(":hidden[value='" + parentDisplayName + "']").prev(":checkbox").prop("checked", flag);
			});
		})
	</script>
</head>

<body class="main">
 	
 	
 	<form:form action="${ctp }/role/assign" method="post" modelAttribute="role">
 	
		<input type="hidden" name="id" value="${role.id}" />
		<input type="hidden" name="_method" value="PUT" />
		
		<div class="page_title">
			角色管理 &gt; 分配权限
		</div>
		
		<div class="button_bar">
			<button class="common_button" onclick="javascript:history.back(-1);">
				返回
			</button>
			<button class="common_button" onclick="document.forms[1].submit();">
				保存
			</button>
		</div>

		<table class="query_form_table" border="0" cellPadding="3"
			cellSpacing="0">
			<tr>
				<th class="input_title" width="10%">
					角色名
				</th>
				<td class="input_content" width="20%">
					${role.name}
				</td>
				<th class="input_title" width="10%">
					角色描述
				</th>
				<td class="input_content" width="20%">
					${role.description}
				</td>
				<th class="input_title" width="10%">
					状态
				</th>
				<td class="input_content" width="20%">
					${role.enabled? "有效" : "无效"}
				</td>
			</tr>
			<tr>
				<th class="input_title">
					权限
				</th>
				<td class="input_content" colspan="5" valign="top">
					<c:forEach var="pa" items="${parentAuthorities }" varStatus="status">
						<input type="checkbox" class="parent"/>
						<input type="hidden" value="${pa.displayName}"/>
						${pa.displayName }:
						<br>
						
						&nbsp;&nbsp;&nbsp;
						<form:checkboxes items="${pa.subAuthorities }" path="authorities2"
							itemLabel="displayName" itemValue="id" 
							delimiter="<br>&nbsp;&nbsp;&nbsp;&nbsp;"
							cssClass="sub-${pa.displayName }" /> 
						<br><br>	
					</c:forEach>
				</td>
				<%-- <td class="input_content" colspan="5" valign="top">
					<c:forEach var="pa" items="${parentAuthorities }" varStatus="status">
						<input type="checkbox" class="parent" name="authorities" value="${pa.id}" class="input"
							<c:if test="${fn:contains(userRoleStr,pa.name)}">checked</c:if>/>
						<input type="hidden" value="${pa.displayName}"/>
						${pa.displayName }:
						<br>
							
						<c:forEach var="su" items="${pa.subAuthorities }">
						&nbsp;&nbsp;&nbsp;
							<input type="checkbox" name="authorities.id" value="${su.id}" class="input"
								<c:if test="${fn:contains(userRoleStr,su.displayName)}">checked</c:if> />${su.displayName}	
						<br></c:forEach>
						<form:checkboxes items="${pa.subAuthorities }" path="authorities"
							delimiter="<br>&nbsp;&nbsp;&nbsp;&nbsp;"
							cssClass="sub-${pa.displayName }" />
						<br><br>
					</c:forEach>
				</td> --%>
			</tr>
		</table>
	</form:form>
							<%-- itemLabel="displayName" itemValue="id"  --%>
	
</body>
</html>
