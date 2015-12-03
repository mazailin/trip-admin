<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品管理</title>
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
		<li class="active"><a href="${ctx}/iom/product/list">产品列表</a></li>
		<shiro:hasPermission name="iom:product:edit"><li><a href="${ctx}/iom/product/form">产品添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="product" action="${ctx}/iom/product/list" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<li><label>名&nbsp;&nbsp;&nbsp;称：</label><form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th class="sort-column name">名称</th><th>描述</th><shiro:hasPermission name="iom:product:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="product">
			<tr>
				<td><a href="${ctx}/iom/product/form?id=${product.id}">${product.name}</a></td>
				<td>${product.comment}</td>
				<shiro:hasPermission name="iom:product:edit"><td>
    				<a href="${ctx}/iom/product/form?id=${product.id}">修改</a>
					<a href="${ctx}/iom/product/delete?id=${product.id}" onclick="return confirmx('确认要删除该产品吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>