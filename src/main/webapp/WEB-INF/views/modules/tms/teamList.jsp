<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>旅行团小组管理</title>
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
		<li class="active"><a href="${ctx}/tms/team/list?group.id=${team.group.id}">小组列表</a></li>
		<li><a href="${ctx}/tms/team/form?group.id=${team.group.id}">小组添加</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="team" action="${ctx}/tms/team/list?group.id=${team.group.id}" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>名称</th><th>备注</th><th>操作</th></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="team">
			<tr>
				<td><a href="${ctx}/tms/team/form?id=${team.id}">${team.name}</a></td>
				<td>${team.comment}</td>
				<td>
    				<a href="${ctx}/tms/team/form?id=${team.id}">修改</a>
					<a href="${ctx}/tms/team/delete?id=${team.id}&group.id=${team.group.id}" onclick="return confirmx('确认要解散该小组吗？', this.href)">解散</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>