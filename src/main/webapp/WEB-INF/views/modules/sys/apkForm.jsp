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
  <title>安装包管理</title>
  <meta name="decorator" content="default"/>
  <script type="text/javascript">
    $(document).ready(function() {
      $("#value").focus();
      jQuery.validator.addMethod("tar",function(){
        var file = $("#tarFile");
        if(file.val().indexOf(".tar")>0||file.val().indexOf(".zip")>0||file.val().indexOf(".rar")>0){
          return true;
        }
        return false;
      },"请上传文件后缀为tar,zip,rar的安装包");
      $("#inputForm").validate({
        rules:{
          tarFile : {
            tar : true
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
  <li><a href="${ctx}/sys/apk/">安装包列表</a></li>
  <li class="active"><a href="${ctx}/sys/apk/form?id=${apk.id}">安装包${not empty apk.id?'修改':'添加'}</a></li>
</ul><br/>
<form:form id="inputForm" modelAttribute="apk" action="${ctx}${empty apk.id?'/sys/apk/upload':'/sys/apk/save'}"
           method="post" class="form-horizontal"  enctype="multipart/form-data">
  <form:hidden path="id"/>
  <sys:message content="${message}"/>
  <div class="control-group">
    <label class="control-label">压缩包名称:</label>
    <div class="controls">
      <form:input path="name" htmlEscape="false" maxlength="50" class="required"/>
    </div>
  </div>
  <div class="control-group">
    <label class="control-label">描述:</label>
    <div class="controls">
      <form:input path="description" htmlEscape="false" maxlength="50" class="required"/>
    </div>
  </div>
  <div class="control-group">
    <label class="control-label">包名:</label>
    <div class="controls">
      <form:input path="packageName" htmlEscape="false" maxlength="50" class="required"/>
    </div>
  </div>
  <div class="control-group">
    <label class="control-label">版本:</label>
    <div class="controls">
      <form:input path="version" htmlEscape="false" maxlength="50" class="required"/>
    </div>
  </div>
  <c:if test="${empty apk.id}">
    <div class="control-group">
      <label class="control-label">压缩包上传:</label>
      <div class="controls">
        <input type="file" id="tarFile" name="tarFile" class="required"/>
      </div>
    </div>
  </c:if>
  <div class="control-group">
    <label class="control-label">MD5:</label>
    <div class="controls">
      <form:input path="md5" htmlEscape="false" maxlength="50" class="" readonly="true"/>
    </div>
  </div>
  <div class="control-group">
    <label class="control-label">压缩包大小:</label>
    <div class="controls">
      <form:input path="size" htmlEscape="false" maxlength="50" class="" readonly="true"/>
    </div>
  </div>
  <div class="control-group">
    <label class="control-label">压缩包路径:</label>
    <div class="controls">
      <form:input path="url" htmlEscape="false" maxlength="50" class="" readonly="true"/>
    </div>
  </div>
  <div class="form-actions">
    <c:choose>
    <c:when test="${empty apk.id}">
      <input id="btnSubmit" class="btn btn-primary" type="submit" value="上 传"/>&nbsp;
    </c:when>
    <c:otherwise>
    <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
    </c:otherwise>
    </c:choose>
    <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>

  </div>
</form:form>
</body>
</html>
