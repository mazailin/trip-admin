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
  <title>用户管理</title>
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
  <li><a href="${ctx}/crm/appuser/?group=${groupUser.group}">用户列表</a></li>
  <li class="active"><a href="${ctx}/crm/appuser/form?id=${groupUser.id}">用户${not empty groupUser.id?'修改':'添加'}</a></li>
</ul><br/>
<form:form id="inputForm" modelAttribute="appUser" action="${ctx}/crm/appuser/save" method="post" class="form-horizontal">
  <form:hidden path="id"/>
  <sys:message content="${message}"/>
  <div class="control-group">
    <label class="control-label">护照:</label>
    <div class="controls">
      <form:input path="passport" htmlEscape="false" maxlength="50" class="required"/>
    </div>
  </div>

  <div class="control-group">
    <label class="control-label">姓名:</label>
    <div class="controls">
      <form:input path="name" htmlEscape="false" maxlength="50" class="required"/>
    </div>
  </div>

  <div class="control-group">
    <label class="control-label">性别:</label>
    <div class="controls">
      <form:select path="gender">
        <form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
      </form:select>
    </div>
  </div>

  <div class="control-group">
    <label class="control-label">电话:</label>
    <div class="controls">
      <form:input path="phone" htmlEscape="false" maxlength="50" class="required"/>
    </div>
  </div>

  <div class="control-group">
    <label class="control-label">微信:</label>
    <div class="controls">
      <form:input path="weChat" htmlEscape="false" maxlength="50" class=""/>
    </div>
  </div>

  <div class="control-group">
    <label class="control-label">QQ:</label>
    <div class="controls">
      <form:input path="qq" htmlEscape="false" maxlength="50" class=""/>
    </div>
  </div>

  <div class="control-group">
    <label class="control-label">出生日期:</label>
    <div class="controls">
      <input id="birth" name="birth" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate required"
             value="<fmt:formatDate value="${appUser.birth}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
    </div>
  </div>

  <div class="control-group">
    <label class="control-label">出生地:</label>
    <div class="controls">
      <form:input path="birthPlace" htmlEscape="false" maxlength="50" class=""/>
    </div>
  </div>

  <div class="control-group">
    <label class="control-label">签发日期:</label>
    <div class="controls">
      <input id="issueDate" name="issueDate" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate required"
             value="<fmt:formatDate value="${appUser.issueDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
    </div>
  </div>

  <div class="control-group">
    <label class="control-label">签发地点:</label>
    <div class="controls">
      <form:input path="issuePlace" htmlEscape="false" maxlength="50" class=""/>
    </div>
  </div>

  <div class="control-group">
    <label class="control-label">到期日期:</label>
    <div class="controls">
      <input id="expiryDate" name="expiryDate" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate required"
             value="<fmt:formatDate value="${appUser.expiryDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
    </div>
  </div>

  <div class="control-group">
    <label class="control-label">邮件:</label>
    <div class="controls">
      <form:input path="email" htmlEscape="false" maxlength="50" class=""/>
    </div>
  </div>

  <div class="form-actions">
    <shiro:hasPermission name="crm:appuser:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/></shiro:hasPermission>&nbsp;
    <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
  </div>
</form:form>

<script type="text/javascript">
  $(function(){
    if($("#user").val()!=null&&$("#user").val().length>0){
      $("#passport").attr("disabled",true);
    }else{
      $("#passport").attr("disabled",false);
    }
    $("#passport").blur(function(e){
      $.ajax({
        url:"${ctx}/crm/appuser/hasPassport?passport="+$("#passport").val(),
        dataType:"json",
        type:"get",
        success:function(data){
          if(data.id!=null&&data.id.length>0){
            if(data.code == 0){
              swal("用户已存在", "用户已存在,", "error");
              $("#btnSubmit").attr("readonly","true");
              return false;
            }
            $("#id").val('');
            $("#passport").val(e.target.value);
            $("#user").val(data.user);
            $("#name").val(data.name);
            $("#type").val(data.type);
            $("#gender").val(data.gender).trigger("change");
            $("#phone").val(data.phone);
            $("#email").val(data.email);
            $("#passport").attr("readonly","true");
          }
          $("#btnSubmit").removeAttr("disabled");
        }
      })
    })
  })

</script>
</body>
</html>

