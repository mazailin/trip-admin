<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>国家管理</title>
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
		<li><a href="${ctx}/tim/country/list">国家列表</a></li>
		<li class="active"><a href="${ctx}/tim/country/form?id=${customer.id}">国家<shiro:hasPermission name="tim:country:edit">${not empty country.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="tim:country:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="country" action="${ctx}/tim/country/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">名称:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="64" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
        <div class="control-group">
            <label class="control-label">当地区号:</label>
            <div class="controls">
                <form:input path="zcode" htmlEscape="false" maxlength="16"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">中国区号:</label>
            <div class="controls">
                <form:input path="cnzcode" htmlEscape="false" maxlength="16"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">急救电话:</label>
            <div class="controls">
                <form:input path="ambulance" htmlEscape="false" maxlength="64"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">匪警:</label>
            <div class="controls">
                <form:input path="police" htmlEscape="false" maxlength="64"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">火警:</label>
            <div class="controls">
                <form:input path="fire" htmlEscape="false" maxlength="64"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">海上急救:</label>
            <div class="controls">
                <form:input path="seaEmerg" htmlEscape="false" maxlength="64"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">公路抢险:</label>
            <div class="controls">
                <form:input path="roadEmerg" htmlEscape="false" maxlength="64"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">银联境外客服电话:</label>
            <div class="controls">
                <form:input path="unionpayCall" htmlEscape="false" maxlength="64"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">大使馆电话:</label>
            <div class="controls">
                <form:input path="embassyCall" htmlEscape="false" maxlength="64"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">大使馆工作时间:</label>
            <div class="controls">
                <form:input path="embassyTime" htmlEscape="false" maxlength="64"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">大使馆地址:</label>
            <div class="controls">
                <form:input path="embassyAddr" htmlEscape="false" maxlength="64"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">大使馆城市:</label>
            <div class="controls">
                <form:input path="embassyCity" width="200" htmlEscape="false" maxlength="64"/>
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">描述:</label>
			<div class="controls">
				<form:textarea path="description" htmlEscape="false" rows="3" maxlength="255" class="input-xlarge"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="tim:country:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>