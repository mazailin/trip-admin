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
  <title>采购手机订单管理</title>
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
    <li class="active"><a href="${ctx}/sys/dict/">订单列表</a></li>
    <li><a href="${ctx}/sys/dict/form?sort=10">订单添加</a></li>
  </ul>
  <form:form id="searchForm" modelAttribute="dict" action="${ctx}/sys/dict/" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <label>订单号 ：</label><form:input path="orderId" htmlEscape="false" maxlength="50" class="input-medium"/>
    &nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
  </form:form>
  <sys:message content="${message}"/>
  <table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead><tr><th>订单号</th><th>手机型号</th><th>单价</th><th>总价</th><th>数量</th><th>备注</th><th>操作</th></tr></thead>
    <tbody>
    <c:forEach items="${page.list}" var="order">
      <tr>
        <td>${order.orderId}</td>
        <td>${order.model}</td>
        <td>${order.unitPrice}</td>
        <td>${order.totalPrice}</td>
        <td>${order.quantity}</td>
        <td>${order.comment}</td>
        <td>
          <a href="${ctx}/ims/phoneOrder/form?id=${order.id}">修改</a>
          <a href="${ctx}/ims/phoneOrder/delete?id=${order.id}&type=${dict.type}" onclick="return confirmx('确认要删除该字典吗？', this.href)">删除</a>
          <a href="<c:url value='${fns:getAdminPath()}/sys/dict/form?type=${dict.type}&sort=${dict.sort+10}'><c:param name='description' value='${dict.description}'/></c:url>">添加键值</a>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
  <div class="pagination">${page}</div>

</body>
</html>
