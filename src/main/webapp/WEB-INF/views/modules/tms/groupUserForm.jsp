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
  <title>旅游团用户管理</title>
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
  <li><a href="${ctx}/tms/groupUser/?group=${groupUser.group}">旅游团用户列表</a></li>
  <li class="active"><a href="${ctx}/tms/groupUser/form?id=${groupUser.id}">旅游团用户${not empty groupUser.id?'修改':'添加'}</a></li>
  <li><a href="${ctx}/tms/journeyDay/?groupId=${groupUser.group}">旅游团行程</a></li>
</ul><br/>
<form:form id="inputForm" modelAttribute="groupUser" action="${ctx}/tms/groupUser/save" method="post" class="form-horizontal">
  <form:hidden path="id"/>
  <form:hidden path="user"/>
  <form:hidden path="group"/>
  <sys:message content="${message}"/>
  <div class="control-group">
    <label class="control-label">编号:</label>
    <div class="controls">
      <form:input path="code" htmlEscape="false" maxlength="50" class="required" disabled="true"/>
    </div>
  </div>

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
    <label class="control-label">类型:</label>
    <div class="controls">
      <form:select path="type">
        <form:options items="${fns:getDictList('tourist_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
      </form:select>
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
      <form:input path="phone" htmlEscape="false" maxlength="50" class=""/>
    </div>
  </div>

  <div class="control-group">
    <label class="control-label">邮件:</label>
    <div class="controls">
      <form:input path="email" htmlEscape="false" maxlength="50" class=""/>
    </div>
  </div>

  <div class="form-actions">
    <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
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
      if(e.target.value.length < 5)return true;
      $.ajax({
        url:"${ctx}/tms/groupUser/getPassport?query="+$("#passport").val()+"&group=${groupUser.group}",
        dataType:"json",
        type:"get",
        success:function(data){
          if(data.id!=null&&data.id.length>0){
            if(data.code == 0){
              swal("用户已存在", "用户已存在于本团队或其他团队中", "error");
              $("#btnSubmit").attr("readonly","true");
              return false;
            }
            $("#passport").val(e.target.value);
            $("#user").val(data.user);
            $("#name").val(data.name);
            $("#type").val(data.type);
            $("#gender").val(data.gender);
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
