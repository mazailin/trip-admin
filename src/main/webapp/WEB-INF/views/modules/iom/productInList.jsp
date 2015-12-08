<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品入库</title>
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
		<li class="active"><a href="${ctx}/iom/product/in/list">产品入库</a></li>
		<shiro:hasPermission name="iom:product:in:edit"><li><a href="${ctx}/iom/product/in/form">新增入库</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="productIn" action="${ctx}/iom/product/in/list" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<li><label>单号：</label><form:input path="code" htmlEscape="false" maxlength="36" class="input-medium"/></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>单号</th><th>日期</th><th>类型</th><th>经办人</th><shiro:hasPermission name="iom:product:in:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="productIn">
			<tr>
				<td><a href="${ctx}/iom/product/in/form?id=${productIn.id}">${productIn.code}</a></td>
				<td><fmt:formatDate value="${productIn.inDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${fns:getDictLabel(productIn.inType, 'product_in_type', '')}</td>
				<td>${productIn.operator}</td>
				<shiro:hasPermission name="iom:product:in:edit"><td>
    				<a href="${ctx}/iom/product/in/form?id=${productIn.id}">修改</a>
					<a href="${ctx}/iom/product/in/delete?id=${productIn.id}" onclick="return confirmx('确认要删除该产品入库单据吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>