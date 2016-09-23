<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品明细管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#inputForm").validate({
                rules: {
                    device: {remote: "${ctx}/iom/product/detail/checkDevice?product.id=" + encodeURIComponent('${productDetail.product.id}') + "&oldDevice=" + encodeURIComponent('${productDetail.device}')}
                },
                messages: {
                    device: {remote: "设备已存在"}
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
		<li><a href="${ctx}/iom/product/detail/list">产品明细</a></li>
		<li class="active"><a href="${ctx}/iom/product/detail/form?id=${productDetail.id}">明细<shiro:hasPermission name="iom:product:detail:edit">${not empty productDetail.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="iom:product:detail:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="productDetail" action="${ctx}/iom/product/detail/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">产品:</label>
			<div class="controls">
                <form:select id="product" path="product.id" class="input-large">
                    <form:options items="${productList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
                </form:select>
				<span class="help-inline"><font color="red">*</font> </span>
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
                <form:input path="device" htmlEscape="false" maxlength="64" class="required"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
				<form:textarea path="comment" htmlEscape="false" rows="3" maxlength="255" class="input-xlarge"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="iom:product:detail:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>