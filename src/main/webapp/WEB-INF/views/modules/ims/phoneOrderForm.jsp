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
  <title>手机订单管理</title>
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
    <li><a href="${ctx}/ims/phoneOrder/">订单列表</a></li>
    <li class="active"><a href="${ctx}/ims/phoneOrder/form?id=${stockOrder.id}">手机订单${not empty stockOrder.id?'修改':'添加'}</a></li>
  </ul><br/>
  <form:form id="inputForm" modelAttribute="stockOrder" action="${ctx}/ims/phoneOrder/save" method="post" class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <div class="control-group">
      <label class="control-label">订单号:</label>
      <div class="controls">
        <form:input path="orderId" htmlEscape="false" maxlength="50" class="required"/>
      </div>
    </div>
    <div class="control-group">
      <label class="control-label">型号:</label>
      <div class="controls">
        <form:input path="model" htmlEscape="false" maxlength="50" class="required"/>
      </div>
    </div>
    <div class="control-group">
      <label class="control-label">单价:</label>
      <div class="controls">
        <i class="fa fa-jpy"></i>&nbsp<form:input path="unitPrice" htmlEscape="false" maxlength="50" class="required" cssClass="calc" cssStyle="width:195px"/>
      </div>
    </div>
    <div class="control-group">
      <label class="control-label">个数:</label>
      <div class="controls">
        <form:input path="quantity" htmlEscape="false" maxlength="50" class="required" cssClass="calc"/>
      </div>
    </div>
    <div class="control-group">
      <label class="control-label">总价:</label>
      <div class="controls">
        <i class="fa fa-jpy"></i>&nbsp<form:input path="totalPrice" htmlEscape="false" maxlength="11" class="required" disabled="true"  cssStyle="width:195px"/>
      </div>
    </div>
    <div class="control-group">
      <label class="control-label">保险:</label>
      <div class="controls">
        <form:radiobutton path="insurance" value="Y"/>Y
        <form:radiobutton path="insurance" value="N"/>N
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
<script type="text/javascript">
  $(function(){
    $(".calc").bind("input propertychange",function(){
      var quantity = $("#quantity").val();
      var unitPrice = $("#unitPrice").val();
      if(quantity!=null&&isNaN(quantity)){
        quantity = quantity.substring(0,quantity.length-1);
        $("#quantity").val(quantity);
        return false;
      }else if(quantity.indexOf(".")>0){
        quantity = quantity.substring(0,quantity.indexOf("."));
        $("#quantity").val(quantity);
        return false;
      }
      if(unitPrice!=null&&isNaN(unitPrice)){
        unitPrice = unitPrice.substring(0,unitPrice.length-1);
        $("#unitPrice").val(unitPrice);
        return false;
      }
      if(quantity.trim().length>0&&unitPrice.trim().length>0){
        $("#totalPrice").attr("value",unitPrice*quantity);
        return true;
      }
      return false;
    });
  })
</script>
</body>
</html>
