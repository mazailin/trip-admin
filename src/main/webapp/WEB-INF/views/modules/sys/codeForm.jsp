<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>编码规则管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#inputForm").validate({
				rules: {
                    numLength : {
                        range : [3, 8],
                        digits: true
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
		<li><a href="${ctx}/sys/code/list">编码规则列表</a></li>
		<li class="active"><a href="${ctx}/sys/code/form?id=${code.id}">编码规则<shiro:hasPermission name="sys:code:edit">修改</shiro:hasPermission><shiro:lacksPermission name="sys:code:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="code" action="${ctx}/sys/code/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
        <form:hidden path="modType"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">类型:</label>
			<div class="controls">
				<input type="text" value="${fns:getDictLabel(code.modType, 'code_rule', '')}" readonly="readonly"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">前缀:</label>
			<div class="controls">
				<form:input path="prefix" htmlEscape="false" maxlength="10"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">使用日期:</label>
			<div class="controls">
                <form:select path="useDate">
                    <form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">日期格式:</label>
			<div class="controls">
                <select name="dateFmt" class="input-small" required>
                    <option value="yyyyMMdd" ${('yyyyMMdd' == code.dateFmt) ? 'selected' : ''}>yyyyMMdd</option>
                    <option value="ddMMyyyy" ${('ddMMyyyy' == code.dateFmt) ? 'selected' : ''}>ddMMyyyy</option>
                    <option value="MMddyyyy" ${('MMddyyyy' == code.dateFmt) ? 'selected' : ''}>MMddyyyy</option>
                    <option value="yyMMdd" ${('yyMMdd' == code.dateFmt) ? 'selected' : ''}>yyMMdd</option>
                    <option value="ddMMyy" ${('ddMMyy' == code.dateFmt) ? 'selected' : ''}>ddMMyy</option>
                    <option value="MMddyy" ${('MMddyy' == code.dateFmt) ? 'selected' : ''}>MMddyy</option>
                    <option value="yyMM" ${('yyMM' == code.dateFmt) ? 'selected' : ''}>yyMM</option>
                    <option value="MMyy" ${('MMyy' == code.dateFmt) ? 'selected' : ''}>MMyy</option>
                    <option value="yyyy" ${('yyyy' == code.dateFmt) ? 'selected' : ''}>yyyy</option>
                    <option value="yy" ${('yy' == code.dateFmt) ? 'selected' : ''}>yy</option>
                </select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">数字长度:</label>
			<div class="controls">
				<form:input path="numLength" htmlEscape="false" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">分隔符:</label>
			<div class="controls">
				<form:input path="separator" htmlEscape="false" maxlength="2"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">下一编号:</label>
			<div class="controls">
                <input type="text" value="${code.nextNum}" readonly="readonly" class="input-small"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sys:code:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>