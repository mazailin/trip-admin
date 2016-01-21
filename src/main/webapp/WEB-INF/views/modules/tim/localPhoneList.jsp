<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>当地电话管理</title>
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
		<li class="active"><a href="${ctx}/tim/phone/list">当地电话列表</a></li>
		<shiro:hasPermission name="tim:phone:edit"><li><a href="${ctx}/tim/phone/form">当地电话添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="localPhone" action="${ctx}/tim/phone/list" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
            <li><label>国家：</label><form:select path="country.id" class="input-medium"><form:option value="" label=""/><form:options items="${countryList}" itemLabel="name" itemValue="id" htmlEscape="false"/></form:select></li>
            <li><label>名称：</label><form:input path="name" htmlEscape="false" maxlength="255" class="input-large"/></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>国家</th><th>名称</th><th>电话</th><shiro:hasPermission name="tim:phone:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="localPhone">
			<tr>
				<td><a href="${ctx}/tim/phone/form?id=${localPhone.id}">${localPhone.country.name}</a></td>
				<td>${localPhone.name}</td>
				<td>${localPhone.phone}</td>
				<shiro:hasPermission name="tim:phone:edit"><td>
    				<a href="${ctx}/tim/phone/form?id=${localPhone.id}">修改</a>
					<a href="${ctx}/tim/phone/delete?id=${localPhone.id}" onclick="return confirmx('确认要删除该电话信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>