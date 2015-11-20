<%--
  Created by IntelliJ IDEA.
  User: makun
  Date: 2015/10/22
  Time: 11:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
  <title>旅游团用户管理</title>
  <meta name="decorator" content="default"/>
  <script type="text/javascript" src="${ctxStatic}/common/qrcode.js"></script>
  <script type="text/javascript">
    function page(n,s){
      $("#pageNo").val(n);
      $("#pageSize").val(s);
      $("#searchForm").submit();
      return false;
    }
  </script>
</head>
<body>
<ul class="nav nav-tabs">
  <li class="active"><a href="${ctx}/tms/groupUser/?group=${groupId}">旅游团用户列表</a></li>
  <li><a href="${ctx}/tms/groupUser/form?group=${groupId}&sort=10">旅游团用户添加</a></li>
  <li><a href="${ctx}/tms/journeyDay/?groupId=${groupId}">旅游团行程</a></li>
</ul>
<form:form id="searchForm" modelAttribute="groupUser" action="${ctx}/tms/groupUser/" method="post" class="breadcrumb form-search">
  <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
  <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
  <input type="hidden" id="group" name="group" value="${groupId}"/>
  <ul class="ul-form">
    <li><label>用户名称 ：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/></li>
    <li><label>护照号 ：</label><form:input path="passport" htmlEscape="false" maxlength="50" class="input-medium"/></li>
    <li><label>手机号 ：</label><form:input path="phone" htmlEscape="false" maxlength="50" class="input-medium"/></li>
    <li class="clearfix"></li>
    <li><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
    <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="generate" class="btn" type="button" value="批量生成二维码" onclick="qrcode();"/></li>
    <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<shiro:hasPermission name="tms:group:import">
    <button id="import" class="btn">导入Excel</button>
    </shiro:hasPermission></li>
  </ul>
</form:form>
<div id="importBox" class="hide">
  <form id="importForm" action="${ctx}/tms/groupUser/import" method="post" enctype="multipart/form-data"
        class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在上传，请稍后...')"><br/>
    <input type="hidden" id="groupId" name="groupId" value="${groupId}"/>
    <input id="file" name="file" type="file" style="width:330px" value=""/><br/><br/>　　
    <input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   " onclick="_import(event)"/>
    <a href="http://7xluz8.dl1.z0.glb.clouddn.com/%E6%97%85%E8%A1%8C%E5%9B%A2%E7%94%A8%E6%88%B7%E5%AF%BC%E5%85%A5%E6%A8%A1%E6%9D%BF.xls?attrname=">下载模板</a>
  </form>
</div>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
  <thead><tr><th>编号</th><th>护照</th><th>姓名</th><th>类型</th><th>手机</th><th>操作</th></tr></thead>
  <tbody>
  <c:forEach items="${page.list}" var="groupUser">
    <tr>
      <td>${groupUser.code}</td>
      <td>${groupUser.passport}</td>
      <td>${groupUser.name}</td>
      <td>${fns:getDictLabel(groupUser.type, 'tourist_type', '')}</td>
      <td>${groupUser.phone}</td>
      <td>
        <a href="#" onclick="getBarcode('${groupUser.name}','${groupUser.code}','${groupUser.phone}');">生成二维码</a>
        <a href="${ctx}/tms/groupUser/form?id=${groupUser.id}&&group=${groupUser.group}">修改</a>
        <a href="${ctx}/tms/groupUser/delete?id=${groupUser.id}&&group=${groupUser.group}" onclick="return confirmx('确认要删除吗？', this.href)">删除</a>
      </td>
    </tr>
  </c:forEach>
  </tbody>
</table>
<div class="pagination">${page}</div>
<script type="text/javascript">
  function getBarcode(name,code,phone){
      swal.setDefaults({'margin-top':'0px'});
    swal({  title: name,
            text: '<div id="qrcode" style="margin-left:80px;width: 300px;height: 300px"></div>',
            html: true });
    var qrcode = new QRCode(document.getElementById("qrcode"), {width:300,height:300});
    qrcode.makeCode(code+","+phone);
  }
  function qrcode(){
    window.open("${ctx}/tms/groupUser/groupQRCode?groupId=${groupId}");
  }
  function _import(e){
    var files = $("input[name='file']");
    var file = "";
    for(var i = 0;i<files.length;i++){
      if(files[i].value.length>0){
        file = files[i].value;
        break;
      }
    }
    if(file.length==0){
      alertx("文件为空，请导入“xls”或“xlsx”格式文件");
      e.preventDefault();
      return false;
    }
    if( file.indexOf(".xls")<0){
      alertx("仅允许导入“xls”或“xlsx”格式文件");
      e.preventDefault();
      return false;
    }
  }
  $(function(){
    $("#import").click(function(e){
      e.preventDefault();
      $.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true},
        bottomText:"仅允许导入“xls”或“xlsx”格式文件！"});
    });
  });
</script>
</body>
</html>
