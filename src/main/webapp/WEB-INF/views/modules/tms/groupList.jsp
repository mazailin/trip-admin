<%--
  Created by IntelliJ IDEA.
  User: makun
  Date: 2015/10/22
  Time: 11:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
  <title>旅游团管理</title>
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
<ul class="nav nav-tabs">
  <li class="active"><a href="${ctx}/tms/group/">旅游团列表</a></li>
  <shiro:hasPermission name="tms:group:edit"><li><a href="${ctx}/tms/group/form?sort=10">旅游团添加</a></li></shiro:hasPermission>
</ul>
<form:form id="searchForm" modelAttribute="group" action="${ctx}/tms/group/" method="post" class="breadcrumb form-search">
  <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
  <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
  <label>旅游团名称 ：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/>
  &nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-bordered table-condensed table-hover">
  <thead><tr><th>名称</th><th>开始日期</th><th>结束日期</th><th>旅行社</th><th>描述</th><th>操作</th></tr></thead>
  <tbody>
  <c:forEach items="${page.list}" var="group">
    <tr>
      <td><a href="${ctx}/tms/group/form?id=${group.id}">${group.name}</a></td>
      <td><fmt:formatDate value="${group.fromDate}" pattern="yyyy-MM-dd"/></td>
      <td><fmt:formatDate value="${group.toDate}" pattern="yyyy-MM-dd"/></td>
      <td>${group.customerName}</td>
      <td>${group.description}</td>
      <td>
        <shiro:hasPermission name="tms:group:edit"><a href="${ctx}/tms/group/form?id=${group.id}">修改</a>
        <a href="${ctx}/tms/groupUser/list?group=${group.id}">团队管理</a>
        <a href="${ctx}/tms/group/delete?id=${group.id}" onclick="return confirmx('确认要删除吗？', this.href)">删除</a></shiro:hasPermission>
        <a href="${ctx}/tms/position/group?group=${group.id}">实时位置</a>
      </td>
    </tr>
  </c:forEach>
  </tbody>
</table>
<div class="pagination">${page}</div>

</body>
</html>
