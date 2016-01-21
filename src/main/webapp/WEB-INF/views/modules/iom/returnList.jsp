<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品归还管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
        <li class="active"><a href="${ctx}/iom/product/return/list">产品归还单</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="return" action="${ctx}/iom/product/return/list" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<li><label>单&nbsp;&nbsp;&nbsp;号：</label><form:input path="code" htmlEscape="false" maxlength="64" class="input-medium"/></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th class="sort-column code">单号</th><th>出租单号</th><th>经办人</th><th>归还时间</th></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="re">
			<tr>
				<td><a href="${ctx}/iom/product/return/form?id=${re.id}">${re.code}</a></td>
				<td><a href="${ctx}/iom/product/rent/list?code=${re.rent.code}">${re.rent.code}</a></td>
				<td>${re.operator}</td>
				<td><fmt:formatDate value="${re.returnDate}" pattern="yyyy-MM-dd"/></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>