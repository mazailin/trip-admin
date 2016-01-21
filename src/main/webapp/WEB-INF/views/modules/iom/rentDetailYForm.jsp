<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>租赁明细管理</title>
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
		<li><a href="${ctx}/iom/product/rent/detail/list?rent.id=${rentDetail.rent.id}">租赁明细</a></li>
        <li><a href="${ctx}/iom/product/rent/detail/form?rent.id=${rentDetail.rent.id}">非明细产品添加</a></li>
        <li class="active"><a href="${ctx}/iom/product/rent/detail/yform?rent.id=${rentDetail.rent.id}">明细产品添加</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="rentDetail" action="${ctx}/iom/product/rent/detail/ysave" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="rent.id"/>
		<form:hidden path="product.id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">产品明细:</label>
			<div class="controls">
                <form:select id="productDetail" path="productDetail.id" class="input-large">
                    <form:options items="${productDetailList}" itemLabel="code" itemValue="id" htmlEscape="false"/>
                </form:select>
                <span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
        <input name="amount" value="1" type="hidden"/>
		<div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="5" maxlength="255" class="input-xxlarge"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="iom:product:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>