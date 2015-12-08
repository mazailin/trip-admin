<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>产品入库管理</title>
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
    <li><a href="${ctx}/iom/product/in/list">产品入库</a></li>
    <li class="active"><a href="${ctx}/iom/product/in/form?id=${productIn.id}">入库<shiro:hasPermission name="iom:product:in:edit">${not empty productIn.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="iom:product:in:edit">查看</shiro:lacksPermission></a></li>
</ul><br/>
<form:form id="inputForm" modelAttribute="productIn" action="${ctx}/iom/product/in/save" method="post" class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">单据编号:</label>
        <div class="controls">
            <form:input path="code" htmlEscape="false" disabled="true"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">入库日期:</label>
        <div class="controls">
            <input id="inDate" name="inDate" type="text" readonly="readonly" maxlength="20" class="input-large Wdate"
                   value="<fmt:formatDate value="${productIn.inDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">入库类型:</label>
        <div class="controls">
            <form:select path="inType">
                <form:options items="${fns:getDictList('product_in_type')}" itemLabel="label" itemValue="value" htmlEscape="false" class="input-small"/>
            </form:select>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">经办人:</label>
        <div class="controls">
            <form:input path="operator" htmlEscape="false" maxlength="64"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">采购单据:</label>
        <div class="controls">
            <form:input path="bills" htmlEscape="false" maxlength="255"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">是否购买保险:</label>
        <div class="controls">
            <form:select path="insure">
                <form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="input-small"/>
            </form:select>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">备注:</label>
        <div class="controls">
            <form:textarea path="comment" htmlEscape="false" rows="5" maxlength="2000" class="input-xxlarge"/>
        </div>
    </div>
    <c:if test="${not empty productIn.id}">
        <div class="control-group">
            <label class="control-label">入库明细:</label>
            <div class="controls">
                <table id="contentTable" class="table table-striped table-bordered table-condensed">
                    <thead><tr><th>产品</th><th>数量</th><th>采购数量</th><th>单价</th><th>总价</th><shiro:hasPermission name="iom:product:in:edit"><th>操作</th></shiro:hasPermission></tr></thead>
                    <tbody>
                    </tbody>
                </table>
                <input class="btn btn-primary" type="button" value="增加明细" onclick="history.go(-1)"/>
            </div>
        </div>
    </c:if>
    <div class="form-actions">
        <shiro:hasPermission name="iom:product:in:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>