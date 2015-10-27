<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/10/22
  Time: 14:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
  <title>旅游团管理</title>
  <meta name="decorator" content="default"/>
  <script type="text/javascript">
    $(document).ready(function() {
      $("#value").focus();
      $("#inputForm").validate({

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
  <li><a href="${ctx}/tms/group/">旅游团列表</a></li>
  <li class="active"><a href="${ctx}/tms/group/form?id=${phoneInfo.id}">旅游团${not empty phoneInfo.id?'修改':'添加'}</a></li>
</ul><br/>
<form:form id="inputForm" modelAttribute="group" action="${ctx}/tms/group/save" method="post" class="form-horizontal">
  <form:hidden path="id"/>
  <sys:message content="${message}"/>
  <div class="control-group">
    <label class="control-label">名称:</label>
    <div class="controls">
      <form:input path="name" htmlEscape="false" maxlength="50" class="required"/>
    </div>
  </div>
  <div class="control-group">
    <label class="control-label">开始日期:</label>
    <div class="controls">
      <input id="fromDate" name="fromDate" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
             value="<fmt:formatDate value="${fromDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
    </div>
  </div>
  <div class="control-group">
    <label class="control-label">结束日期:</label>
    <div class="controls">
      <input id="toDate" name="toDate" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
             value="<fmt:formatDate value="${toDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
    </div>
  </div>
  <div class="control-group">
    <label class="control-label">描述:</label>
    <div class="controls">
      <form:textarea path="description" htmlEscape="false" rows="3" maxlength="2000" class="input-xlarge"/>
    </div>
  </div>
  <div class="form-actions">
    <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
    <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
  </div>
</form:form>
</body>
</html>
