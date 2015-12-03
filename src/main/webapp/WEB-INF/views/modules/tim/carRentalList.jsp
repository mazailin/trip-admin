<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>租车管理</title>
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
		<li class="active"><a href="${ctx}/tim/car/list">租车列表</a></li>
		<shiro:hasPermission name="tim:car:edit"><li><a href="${ctx}/tim/car/form">租车添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="carRental" action="${ctx}/tim/car/list" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
            <li><label>国家：</label><form:select path="country.id" class="input-medium"><form:option value="" label=""/><form:options items="${countryList}" itemLabel="name" itemValue="id" htmlEscape="false"/></form:select></li>
            <li><label>类型：</label><form:select path="type" class="input-medium"><form:option value="" label=""/><form:options items="${fns:getDictList('car_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/></form:select></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>国家</th><th>类型</th><th>电话</th><shiro:hasPermission name="tim:car:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="carRental">
			<tr>
				<td><a href="${ctx}/tim/car/form?id=${carRental.id}">${carRental.country.name}</a></td>
				<td>${fns:getDictLabel(carRental.type, 'car_type', '')}</td>
				<td>${carRental.phone}</td>
				<shiro:hasPermission name="tim:car:edit"><td>
    				<a href="${ctx}/tim/car/form?id=${carRental.id}">修改</a>
					<a href="${ctx}/tim/car/delete?id=${carRental.id}" onclick="return confirmx('确认要删除该租车信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>