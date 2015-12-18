<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品报废管理</title>
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
		<li class="active"><a href="${ctx}/iom/product/discard">产品报废</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="productDiscard" action="${ctx}/iom/product/discard/list" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<li><label>时&nbsp;&nbsp;&nbsp;间：</label><input id="disDate" name="disDate" type="text" readonly="readonly" maxlength="20" class="input-large Wdate"
                                                           value="<fmt:formatDate value="${productDiscard.disDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" /></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>产品</th><th>产品明细</th><th>报废日期</th><th>报废数量</th><th>报废原因</th></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="productDiscard">
			<tr>
				<td><a href="${ctx}/iom/product/form?id=${productDiscard.product.id}">${productDiscard.product.name}</a></td>
				<td><a href="${ctx}/iom/product/detail/form?id=${productDiscard.productDetail.id}">${productDiscard.productDetail.code}</a></td>
				<td><fmt:formatDate value="${productDiscard.disDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${productDiscard.amount}</td>
				<td>${productDiscard.reasons}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>