<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>购物</title>
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
		<li class="active"><a href="${ctx}/tim/shop/list">购物列表</a></li>
		<shiro:hasPermission name="tim:shop:edit"><li><a href="${ctx}/tim/shop/form">购物添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="shop" action="${ctx}/tim/shop/list" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
            <li><label>城市：</label><sys:treeselect id="city" name="city.id" value="${shop.city.id}" labelName="city.name" labelValue="${shop.city.name}"
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
            <shiro:hasPermission name="tim:shop:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="shop">
			<tr>
				<td title="${shop.name}"><a href="${ctx}/tim/shop/form?id=${shop.id}">${fns:abbr(shop.name,30)}</a></td>
				<td>${fns:getDictLabel(shop.type, 'shop_type', '')}</td>
				<td>${shop.city.name}</td>
				<td>${shop.createBy.name}</td>
				<td>${shop.updateBy.name}</td>
				<td title="${shop.description}">${fns:abbr(shop.description, 60)}</td>
				<shiro:hasPermission name="tim:shop:edit"><td>
    				<a href="${ctx}/tim/shop/form?id=${shop.id}">修改</a>
					<a href="${ctx}/tim/shop/delete?id=${shop.id}" onclick="return confirmx('确认要删除该购物吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>