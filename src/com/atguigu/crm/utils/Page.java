package com.atguigu.crm.utils;

import java.util.List;

public class Page<T> {

	private int pageNo;
	private int pageSize = Commons.PAGE_SIZE_MIN;

	private int totalElements;
	private List<T> content;
	
	private int totalPages=0;
	
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	
	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		if (pageNo < 1) {
			pageNo = 1;
		}
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(int totalElements) {
		this.totalElements = totalElements;
		if (this.getPageNo() > getTotalPages()) {
			this.pageNo = getTotalPages();
		}
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

	public int getTotalPages(){
		
		totalPages = this.totalElements / this.pageSize;
		if(this.totalElements % this.pageSize != 0){
			totalPages++;
		}
		
		return totalPages;
	}
	
	
	public boolean isHasNextPage(){
		return pageNo < getTotalPages();
	}
	
	public int getNextPage(){
		if (isHasNextPage()) {
			return pageNo + 1;
		}
		return pageNo;
	}
	
	public boolean isHasPrevPage(){
		return this.pageNo>1;
	}
	public int getPrevPage(){
		if (isHasPrevPage()) {
			return pageNo - 1;
		}
		return pageNo;
	}
	
	
	//封装链接到page类中
	private String links;
	
	public String getLinks() {
		
		StringBuffer sBuf = new StringBuffer();
		
		sBuf.append("<div style=\"text-align:right; padding:6px 6px 0 0;\">");
		
		sBuf.append("共").append(this.getTotalElements()).append("条记录 &nbsp;&nbsp;");
		
		sBuf.append("当前第").append(this.getPageNo()).append("页/共").append(this.getTotalPages()).append(" 页&nbsp;&nbsp;");
		
		sBuf.append("<c:if test=\"").append(this.isHasNextPage()).append("\">&nbsp;&nbsp;");
		sBuf.append("<a href=\"?pageNo=1\">首页</a>&nbsp;&nbsp;");
		sBuf.append("<a href=\"?pageNo=").append(this.getPrevPage()).append("\">上一页</a></c:if>	");
		
		sBuf.append("<c:if test=\"").append(this.isHasNextPage()).append("\">&nbsp;&nbsp;");
		sBuf.append("<a href=\"?pageNo=").append(this.getNextPage()).append("\">下一页</a>&nbsp;&nbsp;");
		sBuf.append("<a href=\"?pageNo=").append(this.getTotalPages()).append("\">末页</a></c:if>&nbsp;&nbsp;");
		
		sBuf.append("转到 <input id=\"pageNo\" size='1'/> 页&nbsp;&nbsp;</div>");
		
		/*sBuf.append("<script type=\"text/javascript\" src=\"${ctp}/static/jquery/jquery-1.9.1.min.js\"></script>");
		sBuf.append("<script type=\"text/javascript\">$(function(){$(\"#pageNo\").change(function(){");
		sBuf.append("var pageNo = $(this).val();").append("var reg = ").append("/^\d+$/;");
		sBuf.append("if(!reg.test(pageNo)){").append("$(this).val(\"\");").append("alert(\"输入的页码不合法\");return;}");
		sBuf.append("var pageNo2 = parseInt(pageNo);").append("if(pageNo2<1||pageNo2>parseInt(\"${page.totalPages }\")){");
		sBuf.append("$(this).val(\"\");").append("alert(\"输入的页码不合法\");").append("return;}");
		sBuf.append("window.location.href = window.location.pathname + \"?pageNo=\" + pageNo2;});})</script>");*/
		
		return sBuf.toString();
	}
	
}
