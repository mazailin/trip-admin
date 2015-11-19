<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
  <title>酒店管理</title>
  <meta name="decorator" content="default"/>
  <script type="text/javascript">
    $(document).ready(function() {
      $("#inputForm").validate({
        rules : {
          score : {
            range : [0, 5],
            digits: true
          },
          latitude : {
            number : true
          },
          longitude : {
            number : true
          },
          level : {
            range : [0, 5],
            digits: true
          },
          "city.id" : {
            required:true
          },
          "city.name" : {
            required:true
          }
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
  <li><a href="${ctx}/tim/hotel/list">酒店列表</a></li>
  <li class="active"><a href="${ctx}/tim/hotel/form?id=${customer.id}">酒店<shiro:hasPermission name="tim:hotel:edit">${not empty hotel.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="tim:hotel:edit">查看</shiro:lacksPermission></a></li>
</ul><br/>
<form:form id="inputForm" modelAttribute="hotel" action="${ctx}/tim/hotel/save" method="post" class="form-horizontal">
  <form:hidden path="id"/>
  <sys:message content="${message}"/>
  <div class="control-group">
    <label class="control-label">城市:</label>
    <div class="controls">
      <sys:treeselect id="city" name="city.id"  value="${hotel.city.id}" labelName="city.name" labelValue="${hotel.city.name}"
                      title="城市" url="/tim/city/treeData" notAllowSelectParent="true" cssClass="required input-small" allowClear="false"/>
      <span class="help-inline"><font color="red">*</font> </span>
    </div>
  </div>
  <div class="control-group">
    <label class="control-label">名称:</label>
    <div class="controls">
      <form:input id="name" path="name" htmlEscape="false" maxlength="255" class="required input-xxlarge"/>
      <span class="help-inline"><font color="red">*</font> </span>
    </div>
  </div>
  <div class="control-group">
    <label class="control-label">地址:</label>
    <div class="controls">
      <form:textarea path="address" htmlEscape="false" rows="3" maxlength="255" class="input-xlarge"/>
    </div>
  </div>
  <div class="control-group">
    <label class="control-label">纬度:</label>
    <div class="controls">
      <form:input id="latitude" path="latitude" htmlEscape="false" maxlength="255" class="required"/>
    </div>
  </div>
  <div class="control-group">
    <label class="control-label">经度:</label>
    <div class="controls">
      <form:input id="longitude" path="longitude" htmlEscape="false" maxlength="255" class="required"/>
    </div>
  </div>
  <div class="control-group">
    <label class="control-label">预约电话:</label>
    <div class="controls">
      <form:input id="phone" path="phone" htmlEscape="false" maxlength="64"/>
    </div>
  </div>
  <div class="control-group">
    <label class="control-label">描述:</label>
    <div class="controls">
      <form:textarea path="description" htmlEscape="false" rows="5" maxlength="2000" class="input-xxlarge"/>
    </div>
  </div>
  <div class="form-actions">
    <shiro:hasPermission name="tim:hotel:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
    <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
  </div>
</form:form>
</body>
</html>