<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品报废管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#inputForm").validate({
				rules: {
                    amount: {
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
		<li class="active"><a href="">产品报废</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="productDiscard" action="${ctx}/iom/product/discard/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="product.id"/>
		<form:hidden path="productDetail.id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">产品:</label>
			<div class="controls">
				<form:input path="product.name" htmlEscape="false" class="input-large" disabled="true"/>
			</div>
		</div>
        <div class="control-group" ${productDiscard.productDetail.id == null ? 'hidden' : ''}>
            <label class="control-label">产品明细:</label>
            <div class="controls">
                <form:input path="productDetail.code" htmlEscape="false" class="input-large" disabled="true"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">报废日期:</label>
            <div class="controls">
                <input id="disDate" name="disDate" type="text" readonly="readonly" maxlength="20" class="input-large Wdate"
                       value="<fmt:formatDate value="${productDiscard.disDate}" pattern="yyyy-MM-dd HH:mm"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:false});" required/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
        <div class="control-group" ${productDiscard.productDetail.id == null ? '' : 'hidden'}>
            <label class="control-label">报废数量:</label>
            <div class="controls">
                <form:input path="amount" htmlEscape="false" class="input-small"/>
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">报废原因:</label>
			<div class="controls">
				<form:textarea path="reasons" htmlEscape="false" rows="5" maxlength="2000" class="input-xxlarge"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="iom:product:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>