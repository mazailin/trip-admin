<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品明细</title>
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
		<li class="active"><a href="${ctx}/iom/product/detail/in/list?inId=${inId}&product.id=${productDetail.product.id}&product.name=${productDetail.product.name}&product.code=${productDetail.product.code}">产品明细</a></li>
		<li><a href="${ctx}/iom/product/detail/in/form?inId=${inId}&product.id=${productDetail.product.id}&product.name=${productDetail.product.name}&product.code=${productDetail.product.code}">明细添加</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="productDetail" action="${ctx}/iom/product/detail/in/list?inId=${inId}&product.id=${productDetail.product.id}&product.name=${productDetail.product.name}&product.code=${productDetail.product.code}" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<li><label>设备号：</label><form:input path="device" htmlEscape="false" maxlength="64" class="input-medium"/></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>编号</th><th>设备号</th><th>产品</th><th>描述</th></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="productDetail">
			<tr>
				<td>${productDetail.code}</td>
				<td>${productDetail.device}</td>
				<td>${productDetail.product.name}</td>
				<td>${productDetail.comment}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>