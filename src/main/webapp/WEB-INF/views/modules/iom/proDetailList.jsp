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
		<li class="active"><a href="${ctx}/iom/product/detail/list">产品明细</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="productDetail" action="${ctx}/iom/product/detail/list" method="post" class="breadcrumb form-search ">
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
		<thead><tr><th>编号</th><th>设备号</th><th>产品</th><th>状态</th><th>描述</th><shiro:hasPermission name="iom:product:detail:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="productDetail">
			<tr>
				<td><a href="${ctx}/iom/product/detail/form?id=${productDetail.id}">${productDetail.code}</a></td>
				<td>${productDetail.device}</td>
				<td><a href="${ctx}/iom/product/form?id=${productDetail.product.id}">${productDetail.product.name}</a></td>
				<td>${fns:getDictLabel(productDetail.status, 'phone_status', '')}</td>
				<td>${productDetail.comment}</td>
				<shiro:hasPermission name="iom:product:detail:edit"><td>
                    <c:if test="${'1' eq productDetail.status}">
    				    <a href="${ctx}/iom/product/detail/form?id=${productDetail.id}">修改</a>
                    </c:if>
                    <c:if test="${'1' eq productDetail.status}">
                        <a href="${ctx}/iom/product/detail/test?id=${productDetail.id}" onclick="return confirmx('确认此产品通过测试了吗？', this.href)">测试</a>
                    </c:if>
                    <c:if test="${'2' eq productDetail.status}">
    				    <a href="${ctx}/iom/product/detail/sale?id=${productDetail.id}" onclick="return confirmx('确认此产品要正式上架了吗？', this.href)">上架</a>
                    </c:if>
                    <c:if test="${'4' ne productDetail.status}">
                        <a href="${ctx}/iom/product/detail/discard?id=${productDetail.id}">报废</a>
                    </c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>