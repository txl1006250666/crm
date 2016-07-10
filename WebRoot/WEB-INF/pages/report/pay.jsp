<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ include file="/commons/common.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>客户贡献分析</title>
</head>
<body>

	<div class="page_title">
		客户贡献分析
	</div>
	<div class="button_bar">
		<button class="common_button" onclick="document.forms[1].submit();">
			查询
		</button>
	</div>
	
	<form action="${ctp }/report/distribution/list">
		<div id="listView" style="display:block;">
			<table class="query_form_table" border="0" cellPadding="3"
				cellSpacing="0">
				<tr>
					<th>
						客户名称
					</th>
					<td>
						<input type="text" name="search_LIKES_custName" />
					</td>
					<th>
						日期
					</th>
					<td>
						<input type="text" name="search_GED_minOrderDate" size="10" />
						-
						<input type="text" name="search_LED_maxOrderDate" size="10" />
					</td>
				</tr>
			</table>
			<!-- 列表数据 -->
			<br />
			
			<c:if test="${page != null && page.totalElements > 0 }">
				<table class="data_list_table" border="0" cellPadding="3"
					cellSpacing="0">
					<tr>
						
						<th>
							客户名称
						</th>
						<th>
							订单金额（元）
						</th>
					</tr>
					<c:forEach var="customer" items="${page.content }">
						<tr>
							
							<td align="center">
								${customer.name}
							</td>
							<td align="center">
								${customer.totalMoney }
							</td>
		
						</tr>
					</c:forEach>
				</table>
				<atguigu:Page page="${page }"></atguigu:Page>
			</c:if>
			<c:if test="${page == null || page.totalElements == 0 }">
				没有任何数据
			</c:if>
		</div>
	</form>
</body>
</html>