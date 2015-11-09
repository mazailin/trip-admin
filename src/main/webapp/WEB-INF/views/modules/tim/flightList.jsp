<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
  <title>航班</title>
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
  <li class="active"><a href="${ctx}/tim/flight/list">航班列表</a></li>
  <shiro:hasPermission name="tim:flight:edit"><li><a href="${ctx}/tim/flight/form">航班添加</a></li></shiro:hasPermission>
</ul>
<form:form id="searchForm" modelAttribute="flight" action="${ctx}/tim/flight/list" method="post" class="breadcrumb form-search ">
  <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
  <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
  <sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
  <ul class="ul-form">
    <li><label>航班号：</label><form:input path="flightNo" htmlEscape="false" maxlength="64" class="input-medium"/></li>
    <li><label>航空公司：</label><form:input path="company" htmlEscape="false" maxlength="64" class="input-medium"/></li>
    <li class="clearfix"></li>
    <li><label>出发城市：</label>
      <sys:treeselect id="departureCity" name="departureCity.id" value="" labelName="departureCity.name" labelValue=""
                      cssStyle="width:300px" title="城市" url="/tim/city/treeData" notAllowSelectParent="true" cssClass="input-small" allowClear="true"/>
    </li>
    <li><label>到达城市：</label>
      <sys:treeselect id="arrivalCity" name="arrivalCity.id" value="" labelName="arrivalCity.name" labelValue=""
                      cssStyle="width:300px" title="城市" url="/tim/city/treeData" notAllowSelectParent="true" cssClass="input-small" allowClear="true"/>
    </li>
    <li class="clearfix"></li>
    <li><label>创建人：</label>&nbsp;<form:input path="createBy.name" htmlEscape="false" maxlength="100" class="input-medium"/></li>
    <li><label>修改人：</label><form:input path="updateBy.name" htmlEscape="false" maxlength="100" class="input-medium"/></li>
    <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
    <li class="clearfix"></li>
  </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-hover table-bordered table-condensed" style="table-layout:fixed">
  <thead><tr><th class="sort-column name" style="width:200px;">航班号</th><th>航空公司</th><th>起飞</th><th>到达</th><th>创建人</th><th>修改人</th>
    <shiro:hasPermission name="tim:flight:edit"><th style="width: 120px;">操作</th></shiro:hasPermission></tr></thead>
  <tbody>
  <c:forEach items="${page.list}" var="flight">
    <tr>
      <td><a href="${ctx}/tim/flight/form?id=${flight.id}">${flight.flightNo}</a></td>
      <td>${flight.company}</td>
      <td>${flight.departure}</td>
      <td>${flight.arrival}</td>
      <td>${flight.createBy.name}</td>
      <td>${flight.updateBy.name}</td>
      <shiro:hasPermission name="tim:flight:edit"><td>
        <a href="${ctx}/tim/flight/form?id=${flight.id}">修改</a>
        <a href="${ctx}/tim/flight/delete?id=${flight.id}" onclick="return confirmx('确认要删除该航班吗？', this.href)">删除</a>
      </td></shiro:hasPermission>
    </tr>
  </c:forEach>
  </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>