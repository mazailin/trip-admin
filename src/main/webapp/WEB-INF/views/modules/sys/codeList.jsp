<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
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
		<li class="active"><a href="${ctx}/sys/code/list">编码规则列表</a></li>
	</ul>
    <form:form id="searchForm" modelAttribute="code" action="${ctx}/sys/code/list" method="post" hidden="true">
        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
        <sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
    </form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>类型</th><th>前缀</th><th>使用日期</th><th>日期格式</th><th>数字长度</th><th>分隔符</th><th>下一编号</th><shiro:hasPermission name="sys:code:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="code">
			<tr>
				<td><a href="${ctx}/sys/code/form?id=${code.id}">${fns:getDictLabel(code.modType, 'code_rule', '')}</a></td>
				<td>${code.prefix}</td>
				<td>${fns:getDictLabel(code.useDate, 'yes_no', '是')}</td>
				<td>${code.dateFmt}</td>
				<td>${code.numLength}</td>
				<td>${code.separator}</td>
				<td>${code.nextNum}</td>
				<shiro:hasPermission name="sys:code:edit"><td>
    				<a href="${ctx}/sys/code/form?id=${code.id}">修改</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>