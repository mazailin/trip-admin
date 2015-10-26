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
  <title>手机信息管理</title>
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
  <li class="active"><a href="${ctx}/ims/phone/">手机信息列表</a></li>
  <li><a href="${ctx}/ims/phone/form?sort=10">手机信息添加</a></li>
</ul>
<form:form id="searchForm" modelAttribute="phoneInfo" action="${ctx}/ims/phone/" method="post" class="breadcrumb form-search">
  <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
  <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
  <label>手机编号 ：</label><form:input path="code" htmlEscape="false" maxlength="50" class="input-medium"/>
  &nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
  <thead><tr><th>手机编号</th><th>订单ID</th><th>手机状态</th><th>操作</th></tr></thead>
  <tbody>
  <c:forEach items="${page.list}" var="phone">
    <tr>
      <td>${phone.code}</td>
      <td><a href="${ctx}/ims/phoneOrder/form?id=${phone.stockOrderId}">${phone.stockOrderId}</td>
      <td>${phone.statusValue}</td>
      <td>
        <a href="${ctx}/ims/phone/form?id=${phone.id}">修改</a>
        <a href="${ctx}/ims/phone/delete?id=${phone.id}" onclick="return confirmx('确认要置为报废状态吗？', this.href)">报废</a>
        <a href="${ctx}/ims/phone/refund?id=${phone.id}" onclick="return confirmx('确认要置为退货状态吗？', this.href)">更换</a>
        <a href="${ctx}/ims/phone/refund?id=${phone.id}&refundFlag=1" onclick="return confirmx('确认要置为退货状态吗？', this.href)">退货</a>
      </td>
    </tr>
  </c:forEach>
  </tbody>
</table>
<div class="pagination">${page}</div>

</body>
</html>
