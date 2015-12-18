<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品租赁明细管理</title>
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
        <li class="active"><a href="${ctx}/iom/product/rent/detail/list?rent.id=${rentDetail.rent.id}">租赁明细</a></li>
        <shiro:hasPermission name="iom:product:edit"><li><a href="${ctx}/iom/product/rent/detail/form?rent.id=${rentDetail.rent.id}">非明细产品添加</a></li></shiro:hasPermission>
        <shiro:hasPermission name="iom:product:edit"><li><a href="${ctx}/iom/product/rent/detail/yform?rent.id=${rentDetail.rent.id}">明细产品添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="rentDetail" action="${ctx}/iom/product/rent/detail/list" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>产品</th><th>产品明细</th><th>数量</th></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="rentDetail">
			<tr>
				<td>${rentDetail.product.name}</td>
				<td>${rentDetail.productDetail.code}</td>
				<td>${rentDetail.amount}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>