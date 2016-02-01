<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品租赁管理</title>
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
        <li class="active"><a href="${ctx}/iom/product/rent/list">产品租赁单</a></li>
		<shiro:hasPermission name="iom:product:edit"><li><a href="${ctx}/iom/product/rent/form">产品租赁添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="rent" action="${ctx}/iom/product/rent/list" method="post" class="breadcrumb form-search ">
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
		<thead><tr><th class="sort-column code">单号</th><th>租赁人</th><th>经办人</th><th>取货时间</th><th>开始时间</th><th>结束时间</th><shiro:hasPermission name="iom:product:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="rent">
			<tr>
				<td><a href="${ctx}/iom/product/rent/form?id=${rent.id}">${rent.code}</a></td>
				<td>${rent.renter}</td>
				<td>${rent.operator}</td>
				<td><fmt:formatDate value="${rent.getDate}" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${rent.beginDate}" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${rent.endDate}" pattern="yyyy-MM-dd"/></td>
				<shiro:hasPermission name="iom:product:edit"><td>
    				<a href="${ctx}/iom/product/rent/form?id=${rent.id}">修改</a>
    				<a href="${ctx}/iom/product/rent/detail/list?rent.id=${rent.id}">明细</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>