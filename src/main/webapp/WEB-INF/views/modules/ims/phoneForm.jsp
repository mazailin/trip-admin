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
  <title>手机信息管理</title>
  <meta name="decorator" content="default"/>
  <script type="text/javascript">
    $(document).ready(function() {
      $("#value").focus();
      $("#inputForm").validate({

        submitHandler: function(form){
          if($("#id").val()!=null&&$("#id").val()!=''){
            form.submit();
            return;
          }
          $.ajax({
            url : "${ctx}/ims/phone/count?code="+$("#code").val(),
            type:"get",
            dataType:"json",
            success:function(data){
              if(data.count > 0){
                swal("插入失败", "手机编号已存在 :)", "error");
                e.preventDefault();
                return false;

              }
              loading('正在提交，请稍等...');
              form.submit();
            }
          });


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
  <li><a href="${ctx}/ims/phone/">手机信息列表</a></li>
  <li class="active"><a href="${ctx}/ims/phone/form?id=${phoneInfo.id}">手机信息${not empty phoneInfo.id?'修改':'添加'}</a></li>
</ul><br/>
<form:form id="inputForm" modelAttribute="phoneInfo" action="${ctx}/ims/phone/save" method="post" class="form-horizontal">
  <form:hidden path="id"/>
  <sys:message content="${message}"/>
  <div class="control-group">
    <label class="control-label">编号:</label>
    <div class="controls">
      <form:input path="code" htmlEscape="false" maxlength="50" class="required"/>
    </div>
  </div>
  <div class="control-group">
    <label class="control-label">订单号:</label>
    <div class="controls">
      <form:select path="stockOrderId">
        <form:options items="${fns:phoneOrderIds()}" itemLabel="orderId" itemValue="id" htmlEscape="false"/>
      </form:select>
    </div>
  </div>
  <div class="control-group">
    <label class="control-label">状态:</label>
    <div class="controls">
      <form:select path="status" cssStyle="width:150px">
        <form:options items="${fns:phoneStatus()}" itemLabel="value" itemValue="id" htmlEscape="false"/>
      </form:select>
    </div>
  </div>
  <div class="control-group">
    <label class="control-label">备注:</label>
    <div class="controls">
      <form:textarea path="comment" htmlEscape="false" rows="3" maxlength="2000" class="input-xlarge"/>
    </div>
  </div>
  <div class="form-actions">
    <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
    <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
  </div>
</form:form>
</body>
</html>
