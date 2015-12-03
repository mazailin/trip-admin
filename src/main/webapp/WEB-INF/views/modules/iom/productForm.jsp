<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#inputForm").validate({
				rules: {
					name: {remote: "${ctx}/tim/country/checkName?oldName=" + encodeURIComponent('${country.name}')}
				},
				messages: {
                    name: {remote: "国家已存在"}
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
		<li><a href="${ctx}/iom/product/list">产品列表</a></li>
		<li class="active"><a href="${ctx}/iom/product/form?id=${product.id}">产品<shiro:hasPermission name="iom:product:edit">${not empty product.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="iom:product:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="product" action="${ctx}/iom/product/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">名称:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="255" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
        <div class="control-group">
            <label class="control-label">计量单位:</label>
            <div class="controls">
                <form:select path="unit">
                    <form:options items="${fns:getDictList('unit')}" itemLabel="label" itemValue="value" htmlEscape="false" class="input-small"/>
                </form:select>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">平均价格:</label>
            <div class="controls">
                <form:input path="avgPrice" htmlEscape="false" maxlength="16" disabled="true" class="input-small"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">出租单价:</label>
            <div class="controls">
                <form:input path="rentPrice" htmlEscape="false" maxlength="64" class="input-small"/>
                <label>&nbsp;&nbsp;&nbsp;元/天</label>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">赔偿价格:</label>
            <div class="controls">
                <form:input path="payPrice" htmlEscape="false" maxlength="64" class="input-small"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">库存上限:</label>
            <div class="controls">
                <form:input path="upperLimit" htmlEscape="false" maxlength="64" class="input-small"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">库存下限:</label>
            <div class="controls">
                <form:input path="lowLimit" htmlEscape="false" maxlength="64" class="input-small"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">按明细管理:</label>
            <div class="controls">
                <form:select path="useDetail">
                    <form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
				<form:textarea path="comment" htmlEscape="false" rows="3" maxlength="255" class="input-xlarge"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="iom:product:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>