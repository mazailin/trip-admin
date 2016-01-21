<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品明细管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
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
		<li><a href="${ctx}/iom/product/detail/in/list?inId=${inId}">产品明细</a></li>
		<li class="active"><a href="${ctx}/iom/product/detail/in/form?inId=${inId}&id=${productDetail.id}">明细<shiro:hasPermission name="iom:product:detail:edit">${not empty productDetail.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="iom:product:detail:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="productDetail" action="${ctx}/iom/product/detail/in/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="product.id"/>
		<form:hidden path="product.code"/>
        <input type="hidden" name="inId" value="${inId}">
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">产品:</label>
			<div class="controls">
                <form:input path="product.name" class="input-large" disabled="true"/>
			</div>
		</div>
        <div class="control-group">
            <label class="control-label">编号:</label>
            <div class="controls">
                <form:input path="code" htmlEscape="false" disabled="true"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">设备号:</label>
            <div class="controls">
                <form:input path="device" htmlEscape="false" maxlength="64"/>
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
				<form:textarea path="comment" htmlEscape="false" rows="3" maxlength="255" class="input-xlarge"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="iom:product:detail:edit">
                <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
            </shiro:hasPermission>
            <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>