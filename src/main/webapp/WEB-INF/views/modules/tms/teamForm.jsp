<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>旅行团小组管理</title>
	<meta name="decorator" content="default"/>
    <%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<script type="text/javascript">

        var selectedTree;//zTree已选择对象
        var prepareTree;//zTree已选择对象
        var delUsers = [], addUsers = [];

        $(document).ready(function () {
            $("#inputForm").validate({
                submitHandler: function (form) {
                    $("#addUserIds").val(addUsers);
                    $("#delUserIds").val(delUsers);
                    loading('正在提交，请稍等...');
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });

            selectedTree = $.fn.zTree.init($("#selectedTree"), setting, selectedNodes);
            prepareTree = $.fn.zTree.init($("#userTree"), setting, preUserNodes);
        });

        var setting = {
            view: {
                showIcon: false,
                showLine: false,
                selectedMulti: false,
                nameIsHTML: true,
                showTitle: false,
                dblClickExpand: false
            },
            data: {simpleData: {enable: true}},
            callback: {onClick: treeOnClick}
        };

        var selectedNodes = [
            <c:forEach items="${team.userList}" var="user">
            {
                id: "${user.id}",
                pId: "0",
                name: "<font style='font-weight:bold;'>[${user.code}]${user.name}</font>"
            },
            </c:forEach>];

        var preUserNodes = [
            <c:forEach items="${preUserList}" var="user">
            {
                id: "${user.id}",
                pId: "0",
                name: "<font style='font-weight:bold;'>[${user.code}]${user.name}</font>"
            },
            </c:forEach>];

        var selected_ids = "${team.selectIds}".split(",");

        //点击选择项回调
        function treeOnClick(event, treeId, treeNode, clickFlag) {
            $.fn.zTree.getZTreeObj(treeId).expandNode(treeNode);
            if ("userTree" == treeId) {
                var addId = String(treeNode.id);
                if ($.inArray(addId, selected_ids) < 0 && $.inArray(addId, addUsers) < 0) {
                    addUsers.push(addId);
                }
                if ($.inArray(addId, delUsers) >= 0) {
                    delUsers.splice($.inArray(addId, delUsers), 1);
                }
                prepareTree.removeNode(treeNode);
                selectedTree.addNodes(null, treeNode);
            }
            if ("selectedTree" == treeId) {
                var delId = String(treeNode.id);
                if ($.inArray(delId, selected_ids) >= 0 && $.inArray(delId, delUsers) < 0) {
                    delUsers.push(delId);
                }
                if ($.inArray(delId, addUsers) >= 0) {
                    addUsers.splice($.inArray(addId, addUsers), 1);
                }
                selectedTree.removeNode(treeNode);
                prepareTree.addNodes(null, treeNode);
            }
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
        <li><a href="${ctx}/tms/team/list?group.id=${team.group.id}">小组列表</a></li>
        <li class="active"><a href="${ctx}/tms/team/form?id=${team.id}&group.id=${team.group.id}">小组${not empty team.id?'修改':'添加'}</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="team" action="${ctx}/tms/team/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="group.id"/>
		<sys:message content="${message}"/>
        <div class="control-group">
            <label class="control-label">名称:</label>
            <div class="controls">
				<form:input id="name" path="name" htmlEscape="false" maxlength="255" class="required"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
        <div class="control-group">
            <div id="assignRole" class="controls row-fluid span4">
                <div class="span2">
                    <p>待选人员：</p>
                    <div id="userTree" class="ztree"></div>
                </div>
                <div class="span2" style="padding-left:16px;border-left: 1px solid #A8A8A8;">
                    <p>已选人员：</p>
                    <div id="selectedTree" class="ztree"></div>
                </div>
                <form:hidden id="addUserIds" path="addUserIds" />
                <form:hidden id="delUserIds" path="delUserIds" />
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
				<form:textarea path="comment" htmlEscape="false" rows="5" maxlength="2000" class="input-xlarge"/>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>