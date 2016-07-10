<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="page" rtexprvalue="true"
	type="com.atguigu.crm.utils.Page" required="true"%>
<%@ attribute name="queryString" rtexprvalue="true"
	type="java.lang.String" required="false"%>

<div style="text-align: right; padding: 6px 6px 0 0;">

	共 ${page.totalElements } 条记录 &nbsp;&nbsp; 当前第 ${page.pageNo } 页/共
	${page.totalPages } 页 &nbsp;&nbsp;

	<c:if test="${page.hasPrevPage }">
				&nbsp;&nbsp;
				<a href="?pageNo=1${queryString }">首页</a>
				&nbsp;&nbsp;
				<a href="?pageNo=${page.prevPage }${queryString }">上一页</a>
	</c:if>

	<c:if test="${page.hasNextPage }">
				&nbsp;&nbsp;
				<a href="?pageNo=${page.nextPage }${queryString }">下一页</a>
				&nbsp;&nbsp;
				<a href="?pageNo=${page.totalPages }${queryString }">末页</a>
	</c:if>

	&nbsp;&nbsp; 转到 <input id="pageNo" size='1' /> 页 &nbsp;&nbsp;

</div>
<script type="text/javascript"
	src="${ctp}/static/jquery/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
	$(function() {

		$("#pageNo").change(
				function() {

					var pageNo = $(this).val();
					var reg = /^\d+$/;
					if (!reg.test(pageNo)) {
						$(this).val("");
						alert("输入的页码不合法");
						return;
					}

					var pageNo2 = parseInt(pageNo);
					if (pageNo2<1||pageNo2>parseInt("${page.totalPages }")) {
						$(this).val("");
						alert("输入的页码不合法");
						return;
					}

					//查询条件需要放入到 class='condition' 的隐藏域中. 
					window.location.href = window.location.pathname
							+ "?pageNo=" + pageNo2 + ${queryString };

				});
	})
</script>
