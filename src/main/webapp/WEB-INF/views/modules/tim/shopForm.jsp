<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>购物管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#inputForm").validate({
                rules : {
                    score : {
                        range : [0, 5],
                        digits: true
                    },
                    latitude : {
                        range : [-90, 90],
                        lnglat : true
                    },
                    longitude : {
                        range : [-180, 180],
                        lnglat : true
                    },
                    level : {
                        range : [0, 5],
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
		<li><a href="${ctx}/tim/shop/list">购物列表</a></li>
		<li class="active"><a href="${ctx}/tim/shop/form?id=${shop.id}">购物<shiro:hasPermission name="tim:shop:edit">${not empty shop.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="tim:shop:edit">查看</shiro:lacksPermission></a></li>
        <c:if test="${not empty shop.id}"><li><a href="${ctx}/tim/shop/image?id=${shop.id}">购物图片<shiro:hasPermission name="tim:shop:edit">维护</shiro:hasPermission><shiro:lacksPermission name="tim:shop:edit">查看</shiro:lacksPermission></a></li></c:if>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="shop" action="${ctx}/tim/shop/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
        <div class="control-group">
            <label class="control-label">城市:</label>
            <div class="controls">
                <sys:treeselect id="city" name="city.id" value="${shop.city.id}" labelName="city.name" labelValue="${shop.city.name}"
                                title="城市" url="/tim/city/treeData" notAllowSelectParent="true" cssClass="required input-small" allowClear="false"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">名称:</label>
            <div class="controls">
				<form:input id="name" path="name" htmlEscape="false" maxlength="255" class="required input-xxlarge"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">类型:</label>
            <div class="controls">
                <form:select path="type" style="width: 100px;">
                    <form:options items="${fns:getDictList('shop_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">评分:</label>
            <div class="controls">
                <form:input path="score" htmlEscape="false" maxlength="255" class="required input-min"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">地址:</label>
            <div class="controls">
                <form:textarea path="address" htmlEscape="false" rows="3" maxlength="255" class="input-xlarge"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">纬度:</label>
            <div class="controls">
                <form:input path="latitude" htmlEscape="false" maxlength="255" class="required"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">经度:</label>
            <div class="controls">
                <form:input path="longitude" htmlEscape="false" maxlength="255" class="required"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">营业时间:</label>
            <div class="controls">
                <form:textarea path="worktime" htmlEscape="false" rows="3" maxlength="255" class="input-xlarge"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">是否推荐:</label>
            <div class="controls">
                <form:select path="cooper">
                    <form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">是否发布:</label>
            <div class="controls">
                <form:select path="published">
                    <form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">推荐等级:</label>
            <div class="controls">
                <form:input path="level" htmlEscape="false" class="input-min"/>
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">描述:</label>
			<div class="controls">
				<form:textarea path="description" htmlEscape="false" rows="5" maxlength="2000" class="input-xxlarge"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="tim:shop:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>