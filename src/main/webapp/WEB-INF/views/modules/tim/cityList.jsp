<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>城市管理</title>
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
		<li class="active"><a href="${ctx}/tim/city/list">城市列表</a></li>
		<shiro:hasPermission name="tim:city:edit"><li><a href="${ctx}/tim/city/form">城市添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="city" action="${ctx}/tim/city/list" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
            <li><label>国家：</label><form:select path="country.id" class="input-medium"><form:option value="" label=""/><form:options items="${countryList}" itemLabel="name" itemValue="id" htmlEscape="false"/></form:select></li>
            <li><label>名&nbsp;&nbsp;&nbsp;称：</label><form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th class="sort-column name">名称</th><th>国家</th><th style="width: 400px;">描述</th><shiro:hasPermission name="tim:city:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="city">
			<tr>
				<td><a href="${ctx}/tim/city/form?id=${city.id}">${city.name}</a></td>
				<td>${city.country.name}</td>
				<td>${city.description}</td>
				<shiro:hasPermission name="tim:city:edit"><td>
    				<a href="${ctx}/tim/city/form?id=${city.id}">修改</a>
					<a href="${ctx}/tim/city/delete?id=${city.id}" onclick="return confirmx('确认要删除该国家吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>