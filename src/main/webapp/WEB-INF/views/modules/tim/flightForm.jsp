<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
  <title>航班管理</title>
  <meta name="decorator" content="default"/>
  <script type="text/javascript">
    $(document).ready(function() {
      $("#inputForm").validate({
        rules : {
        },
        submitHandler: function(form){
          loading('正在提交，请稍等...');
          form.submit();
        },
        errorContainer: "#messageBox",
        errorPlacement: function(error, element) {
          $("#messageBox").text("输入有误，请先更正。");
          if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
            error.appendTo(element.parent().parent());
          } else {
            error.insertAfter(element);
          }
        }
      });
    });
  </script>
</head>
<body>
<ul class="nav nav-tabs">
  <li><a href="${ctx}/tim/flight/list">航班列表</a></li>
  <li class="active"><a href="${ctx}/tim/flight/form?id=${id}">航班<shiro:hasPermission name="tim:flight:edit">${not empty flight.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="tim:flight:edit">查看</shiro:lacksPermission></a></li>
</ul><br/>
<form:form id="inputForm" modelAttribute="flight" action="${ctx}/tim/flight/save" method="post" class="form-horizontal">
  <form:hidden path="id"/>
  <sys:message content="${message}"/>
  <div class="control-group">
    <label class="control-label">航班号:</label>
    <div class="controls">
      <form:input id="flightNo" path="flightNo" htmlEscape="false" maxlength="255" class="required input-xxlarge"/>
      <span class="help-inline"><font color="red">*</font> </span>
    </div>
  </div>
  <div class="control-group">
    <label class="control-label">航空公司:</label>
    <div class="controls">
      <form:input id="company" path="company" htmlEscape="false" maxlength="255" class="required input-xxlarge"/>
      <span class="help-inline"><font color="red">*</font> </span>
    </div>
  </div>
  <div class="control-group">
    <label class="control-label">出发城市:</label>
    <div class="controls">
      <sys:treeselect id="departureCity" name="departureCity.id" value="${flight.departureCity.id}" labelName="departureCity.name" labelValue="${flight.departureCity.name}"
                      title="城市" url="/tim/city/treeData" notAllowSelectParent="true" cssClass="required input-small" allowClear="false"/>
      <span class="help-inline"><font color="red">*</font> </span>
    </div>
  </div>
  <div class="control-group">
    <label class="control-label">到达城市:</label>
    <div class="controls">
      <sys:treeselect id="arrivalCity" name="arrivalCity.id" value="${flight.arrivalCity.id}" labelName="arrivalCity.name" labelValue="${flight.arrivalCity.name}"
                      title="城市" url="/tim/city/treeData" notAllowSelectParent="true" cssClass="required input-small" allowClear="false"/>
      <span class="help-inline"><font color="red">*</font> </span>
    </div>
  </div>
  <div class="control-group">
    <label class="control-label">出发航站楼:</label>
    <div class="controls">
      <form:input id="departureTerminal" path="departureTerminal" htmlEscape="false" maxlength="50" class="required input-xxlarge"/>
      <span class="help-inline"><font color="red">*</font> </span>
    </div>
  </div>
  <div class="control-group">
    <label class="control-label">到达航站楼:</label>
    <div class="controls">
      <form:input id="arrivalTerminal" path="arrivalTerminal" htmlEscape="false" maxlength="50" class="required input-xxlarge"/>
      <span class="help-inline"><font color="red">*</font> </span>
    </div>
  </div>
  <div class="control-group">
    <label class="control-label">出发时间:</label>
    <div class="controls">
      <form:input type="text" path="departureTime" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'HH:mm'})" cssClass="Wdate"/>
      <span class="help-inline"><font color="red">*</font> </span>
    </div>
  </div>
  <div class="control-group">
    <label class="control-label">到达时间:</label>
    <div class="controls">
      <form:input type="text" path="arrivalTime" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'HH:mm'})" cssClass="Wdate"/>
      <span class="help-inline"><font color="red">*</font> </span>
    </div>
  </div>
  <div class="form-actions">
    <shiro:hasPermission name="tim:flight:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
    <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
  </div>
</form:form>
</body>
</html>