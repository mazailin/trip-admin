<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>库存统计</title>
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
		<li class="active"><a href="${ctx}/iom/product/store">库存统计</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="product" action="${ctx}/iom/product/store" method="post" class="breadcrumb form-search ">
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
		<thead><tr><th class="sort-column name">产品</th><th>单位</th><th>上限</th><th>下限</th><th>库存数量</th><th>在租数量</th><th>可用数量</th><th>总数</th><th>操作</th></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="product">
			<tr>
				<td>${product.name}</td>
				<td>${fns:getDictLabel(product.unit, 'unit', '')}</td>
				<td ${product.rsvAmt > product.upperLimit ? 'style="color:red;" title="库存溢出"' : ''}>${product.upperLimit}</td>
				<td ${product.rsvAmt < product.lowLimit ? 'style="color:red;" title="库存不足"' : ''}>${product.lowLimit}</td>
				<td>${product.rsvAmt}</td>
                <td>${product.rentAmt}</td>
                <td>${product.avlAmt}</td>
                <td>${product.totalAmt}</td>
                <td>
                    <a href="${ctx}/iom/product/in/form?product.id=${product.id}&product.name=${product.name}&product.useDetail=${product.useDetail}">入库</a>
                    <c:choose>
                        <c:when test="${'1' eq product.useDetail}">
                            <a href="${ctx}/iom/product/detail/list?product.id=${product.id}">产品明细</a>
                        </c:when>
                        <c:otherwise>
                            <a href="${ctx}/iom/product/discard/form?product.id=${product.id}&product.name=${product.name}">报废</a>
                        </c:otherwise>
                    </c:choose>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>