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
  <title>安装包管理</title>
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
  <li class="active"><a href="${ctx}/sys/apk/">安装包列表</a></li>
  <shiro:hasPermission name="sys:apk:upload"><li><a href="${ctx}/sys/apk/form">安装包添加</a></li></shiro:hasPermission>
</ul>
<form:form id="searchForm" modelAttribute="apk" action="${ctx}/sys/apk/" method="post" class="breadcrumb form-search">
  <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
  <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
  <label>包名 ：</label><form:input path="packageName" htmlEscape="false" maxlength="50" class="input-medium"/>
  &nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-hover table-bordered table-condensed">
  <thead><tr><th>名称</th><th>包名</th><th>版本号</th><th>安装包大小</th><th>描述</th><th>操作</th></tr></thead>
  <tbody>
  <c:forEach items="${page.list}" var="apk">
    <tr>
      <td>${apk.name}</td>
      <td>${apk.packageName}</td>
      <td>${apk.version}</td>
      <td>${apk.size}</td>
      <td>${apk.description}</td>
      <td>
          <a href="${ctx}/userfiles/${apk.url}")>下载</a>
        <shiro:hasPermission name="sys:apk:upload">
          <a href="${ctx}/sys/apk/form?id=${apk.id}">修改</a>
          <a href="${ctx}/sys/apk/delete?id=${apk.id}">删除</a>
        </shiro:hasPermission>
      </td>
    </tr>
  </c:forEach>
  </tbody>
</table>
<div class="pagination">${page}</div>

</body>
</html>
