<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>景点</title>
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
		<li class="active"><a href="${ctx}/tim/scenic/list">景点列表</a></li>
		<shiro:hasPermission name="tim:scenic:edit"><li><a href="${ctx}/tim/scenic/form">景点添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="scenic" action="${ctx}/tim/scenic/list" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
            <li><label>城市：</label><sys:treeselect id="city" name="city.id" value="${scenic.city.id}" labelName="city.name" labelValue="${scenic.city.name}"
                title="城市" url="/tim/city/treeData" notAllowSelectParent="true" cssClass="input-small" allowClear="true"/></li>
            <li><label>名称：</label><form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/></li>
            <li class="clearfix"></li>
            <li><label>创建人：</label>&nbsp;<form:input path="createBy.name" htmlEscape="false" maxlength="100" class="input-medium"/></li>
            <li><label>修改人：</label><form:input path="updateBy.name" htmlEscape="false" maxlength="100" class="input-medium"/></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed" style="table-layout:fixed">
		<thead><tr><th class="sort-column name" style="width:200px;">名称</th><th>类型</th><th>城市</th><th>创建人</th><th>修改人</th><th style="width: 400px;">描述</th>
            <shiro:hasPermission name="tim:scenic:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="scenic">
			<tr>
				<td title="${scenic.name}"><a href="${ctx}/tim/scenic/form?id=${scenic.id}">${fns:abbr(scenic.name,30)}</a></td>
				<td>${fns:getDictLabel(scenic.type, 'scenic_type', '')}</td>
				<td>${scenic.city.name}</td>
				<td>${scenic.createBy.name}</td>
				<td>${scenic.updateBy.name}</td>
				<td title="${scenic.description}">${fns:abbr(scenic.description, 60)}</td>
				<shiro:hasPermission name="tim:scenic:edit"><td>
    				<a href="${ctx}/tim/scenic/form?id=${scenic.id}">修改</a>
					<a href="${ctx}/tim/scenic/delete?id=${scenic.id}" onclick="return confirmx('确认要删除该景点吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>