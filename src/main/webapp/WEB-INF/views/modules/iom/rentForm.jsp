<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品租赁管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#inputForm").validate({
				rules: {
                    deposit: {
                        decimal: 2
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
		<li><a href="${ctx}/iom/product/rent/list">产品租赁单</a></li>
		<li class="active"><a href="${ctx}/iom/product/rent/form?id=${rent.id}">产品租赁<shiro:hasPermission name="iom:product:edit">${not empty product.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="iom:product:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="rent" action="${ctx}/iom/product/rent/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">编号:</label>
			<div class="controls">
                <form:input path="code" htmlEscape="false" disabled="true"/>
			</div>
		</div>
        <div class="control-group">
            <label class="control-label">租赁人:</label>
            <div class="controls">
                <form:input path="renter" htmlEscape="false" maxlength="255" class="input-large"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">经办人:</label>
            <div class="controls">
                <form:input path="operator" htmlEscape="false" maxlength="255" class="input-large"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">押金:</label>
            <div class="controls">
                <form:input path="deposit" htmlEscape="false" class="input-small"/>
                <label>&nbsp;&nbsp;&nbsp;元</label>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">出租日期:</label>
            <div class="controls">
                <input id="rentDate" name="rentDate" type="text" readonly="readonly" maxlength="10" class="input-large Wdate"
                       value="<fmt:formatDate value="${rent.rentDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">归还日期:</label>
            <div class="controls">
                <input id="returnDate" name="returnDate" type="text" readonly="readonly" maxlength="10" class="input-large Wdate"
                       value="<fmt:formatDate value="${rent.rentDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
				<form:textarea path="comment" htmlEscape="false" rows="5" maxlength="2000" class="input-xxlarge"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="iom:product:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>