<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>美食管理</title>
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
                        number : true
                    },
                    longitude : {
                        number : true
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
		<li><a href="${ctx}/tim/food/list">美食列表</a></li>
		<li class="active"><a href="${ctx}/tim/food/form?id=${customer.id}">美食<shiro:hasPermission name="tim:food:edit">${not empty food.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="tim:food:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="food" action="${ctx}/tim/food/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
        <div class="control-group">
            <label class="control-label">城市:</label>
            <div class="controls">
                <sys:treeselect id="city" name="city.id" value="${food.city.id}" labelName="city.name" labelValue="${food.city.name}"
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
                    <form:options items="${fns:getDictList('food_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">评分:</label>
            <div class="controls">
                <form:input id="name" path="score" htmlEscape="false" maxlength="255" class="required input-min"/>
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
                <form:input id="name" path="latitude" htmlEscape="false" maxlength="255" class="required"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">经度:</label>
            <div class="controls">
                <form:input id="name" path="longitude" htmlEscape="false" maxlength="255" class="required"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">营业时间:</label>
            <div class="controls">
                <form:textarea path="worktime" htmlEscape="false" rows="3" maxlength="255" class="input-xlarge"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">人均价格:</label>
            <div class="controls">
                <form:input id="name" path="price" htmlEscape="false" maxlength="64" class="input-xlarge"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">预约电话:</label>
            <div class="controls">
                <form:input id="name" path="phone" htmlEscape="false" maxlength="64"/>
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
                <form:input id="name" path="level" htmlEscape="false" class="input-min"/>
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">描述:</label>
			<div class="controls">
				<form:textarea path="description" htmlEscape="false" rows="5" maxlength="2000" class="input-xxlarge"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="tim:food:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>