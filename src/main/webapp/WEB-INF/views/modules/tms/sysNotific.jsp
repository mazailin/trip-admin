<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>系统通知</title>
	<meta name="decorator" content="default"/>
    <%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
                    var ids = [], nodes = tree.getCheckedNodes(true);
                    for(var i=0; i<nodes.length; i++) {
                        ids.push(nodes[i].id);
                    }
                    $("#menuIds").val(ids);
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

            var setting = {check:{enable:true,nocheckInherit:true},view:{selectedMulti:false},
                data:{simpleData:{enable:true}},callback:{beforeClick:function(id, node){
                    tree.checkNode(node, !node.checked, true, true);
                    return false;
                }}};

            // 用户-菜单
            var zNodes=[
                    <c:forEach items="${userTree}" var="user">{id:"${user.id}", pId:"${not empty user.pid?user.pid:0}", name:"${not empty user.name?user.name:'用户列表'}"},
                </c:forEach>];
            // 初始化树结构
            var tree = $.fn.zTree.init($("#menuTree"), setting, zNodes);
            // 不选择父节点
            tree.setting.check.chkboxType = { "Y" : "ps", "N" : "s" };
		});
	</script>
</head>
<body>
<br/>
<form id="inputForm" action="${ctx}/tms/group/notice/send" method="post" class="form-inline">
    <sys:message content="${message}"/>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="span4">
                <div id="menuTree" class="ztree" style="margin-top:3px;float:left;"></div>
                <input id="menuIds" type="hidden" name="menuIds"/>
            </div>
            <div class="span6">
                <textarea name="comment" rows="10" maxlength="2000" class="input-xxlarge" required></textarea>
                <div class="clearfix">&nbsp;</div>
                <input id="btnSubmit" class="btn btn-primary text-center" type="submit" value="发送"/>
            </div>
        </div>
    </div>
</form>
</body>
</html>