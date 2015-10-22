<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>移动用户</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
	<form:form id="searchForm" modelAttribute="appUser" action="${ctx}/crm/appuser/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
        <sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
        <ul class="ul-form">
            <li><label>姓&nbsp;&nbsp;&nbsp;名：</label><form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/></li>
            <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
            <li class="clearfix" ></li>
        </ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th class="sort-column name">姓名</th><th>性别</th><th>护照</th><th>电话</th><th>身份证</th><th>邮件</th></thead>
		<tbody>
		<c:forEach items="${page.list}" var="appUser">
			<tr>
				<td>${appUser.name}</td>
				<td>${fns:getDictLabel(appUser.gender, 'sex', '未知')}</td>
				<td><strong>${appUser.passport}</strong></td>
				<td>${appUser.phone}</td>
				<td>${appUser.identityCard}</td>
				<td><a href="mailto:${appUser.email}">${appUser.email}</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>