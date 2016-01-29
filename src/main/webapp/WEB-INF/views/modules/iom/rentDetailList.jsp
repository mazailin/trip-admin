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
		<thead><tr><th>产品</th><th>产品明细</th><th>数量</th><th>归还状态</th><shiro:hasPermission name="iom:product:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="rentDetail">
			<tr>
				<td><a href="${ctx}/iom/product/store?name=${rentDetail.product.name}">${rentDetail.product.name}</a></td>
				<td><a href="${ctx}/iom/product/detail/list?code=${rentDetail.productDetail.code}">${rentDetail.productDetail.code}</a></td>
				<td>${rentDetail.amount}</td>
				<td>${'0' eq rentDetail.product.useDetail ? rentDetail.returnAmount : (1 eq rentDetail.returnAmount ? '已还' : '未还')}</td>
                <shiro:hasPermission name="iom:product:edit"><td>
                    <c:choose>
                        <c:when test="${'0' eq rentDetail.product.useDetail}">
                            <a href="${ctx}/iom/product/rent/detail/nreturn?id=${rentDetail.id}&rent.id=${rentDetail.rent.id}&oldReturnAmount=${rentDetail.returnAmount}&returnAmount=" onclick="return promptx('确认归还此产品吗？', '归还数量', this.href)">归还</a>
                            <a href="${ctx}/iom/product/discard/form?product.id=${rentDetail.product.id}&product.name=${rentDetail.product.name}">报废</a>
                        </c:when>
                        <c:otherwise>
                            <a href="${ctx}/iom/product/rent/detail/yreturn?id=${rentDetail.id}&rent.id=${rentDetail.rent.id}" onclick="return confirmx('确认归还此产品吗？', this.href)">归还</a>
                            <a href="${ctx}/iom/product/discard/form?product.id=${rentDetail.product.id}&product.name=${rentDetail.product.name}&productDetail.id=${rentDetail.productDetail.id}&productDetail.code=${rentDetail.productDetail.code}">报废</a>
                        </c:otherwise>
                    </c:choose>
                </td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>