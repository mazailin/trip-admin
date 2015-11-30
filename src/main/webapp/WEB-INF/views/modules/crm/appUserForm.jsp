<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/10/22
  Time: 14:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
  <title>用户管理</title>
  <meta name="decorator" content="default"/>
  <script type="text/javascript">
    $(document).ready(function() {
      $("#value").focus();
      $("#inputForm").validate({

        submitHandler: function(form){
          var file = $("#photoFile").val();
          if(file.length>0){
            file = file.split('.');
            file = file[file.length-1];
            file = file.toLocaleLowerCase();
            if(file!='jpg'&&file!='jpeg'&&file!='bmp'&&file!='png'){
              alertx("请上传图片格式的文件");
              document.getElementById('passportFile').src='';
              document.getElementById('photoFile').value = '';
              return false;
            }
          }else{
            $("#photoFile").val(null);
          }
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
  <style type="text/css">
    img { max-width:300px;
      max-height: 500px; }
  </style>
</head>
<body>
<ul class="nav nav-tabs">
  <li><a href="${ctx}/crm/appuser/">用户列表</a></li>
  <li class="active"><a href="${ctx}/crm/appuser/form?id=${appUser.id}">用户${not empty appUser.id?'修改':'添加'}</a></li>
</ul><br/>
<form:form id="inputForm" modelAttribute="appUser" action="${ctx}/crm/appuser/save" method="post" class="form-horizontal" enctype="multipart/form-data">
  <form:hidden path="id"/>
  <form:hidden path="passportPhoto"/>
  <sys:message content="${message}"/>
  <div class="control-group">
    <label class="control-label">头像:</label>
    <div class="controls">
      <img <c:if test="${appUser.photo!=null && appUser.photo.length()>0}"> src="${appUser.photo}" </c:if> />
    </div>
  </div>
  <div class="control-group">
    <label class="control-label">护照:</label>
    <div class="controls">
      <form:input path="passport" htmlEscape="false" maxlength="50" class="required"/>
    </div>
  </div>

  <div class="control-group">
    <label class="control-label">姓名:</label>
    <div class="controls">
      <form:input path="name" htmlEscape="false" maxlength="50" class="required"/>
    </div>
  </div>

  <div class="control-group">
    <label class="control-label">性别:</label>
    <div class="controls">
      <form:select path="gender">
        <form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
      </form:select>
    </div>
  </div>

  <div class="control-group">
    <label class="control-label">电话:</label>
    <div class="controls">
      <form:input path="phone" htmlEscape="false" maxlength="50" class="required"/>
    </div>
  </div>

  <div class="control-group">
    <label class="control-label">微信:</label>
    <div class="controls">
      <form:input path="weChat" htmlEscape="false" maxlength="50" class=""/>
    </div>
  </div>

  <div class="control-group">
    <label class="control-label">QQ:</label>
    <div class="controls">
      <form:input path="qq" htmlEscape="false" maxlength="50" class=""/>
    </div>
  </div>

  <div class="control-group">
    <label class="control-label">出生日期:</label>
    <div class="controls">
      <input id="birth" name="birth" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
             value="<fmt:formatDate value="${appUser.birth}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
    </div>
  </div>

  <div class="control-group">
    <label class="control-label">出生地:</label>
    <div class="controls">
      <form:input path="birthPlace" htmlEscape="false" maxlength="50" class=""/>
    </div>
  </div>

  <div class="control-group">
    <label class="control-label">签发日期:</label>
    <div class="controls">
      <input id="issueDate" name="issueDate" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
             value="<fmt:formatDate value="${appUser.issueDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
    </div>
  </div>

  <div class="control-group">
    <label class="control-label">签发地点:</label>
    <div class="controls">
      <form:input path="issuePlace" htmlEscape="false" maxlength="50" class=""/>
    </div>
  </div>

  <div class="control-group">
    <label class="control-label">到期日期:</label>
    <div class="controls">
      <input id="expiryDate" name="expiryDate" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
             value="<fmt:formatDate value="${appUser.expiryDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
    </div>
  </div>

  <div class="control-group">
    <label class="control-label">邮件:</label>
    <div class="controls">
      <form:input path="email" htmlEscape="false" maxlength="50" class=""/>
    </div>
  </div>

  <div class="control-group">
    <label class="control-label">护照首页图片:</label>
    <div class="controls">
      <input type="file" id="photoFile" name="photoFile" onchange="previewImage(this)"/><button type="button" onclick="deletePhoto(event);">删除图片</button><br/>
      <div id="preview">
        <img id="passportFile" <c:if test="${appUser.passportPhoto != null && appUser.passportPhoto.length()>0}"> src="${ctx}/userfiles/${appUser.passportPhoto}" </c:if>/>
      </div>
    </div>
  </div>

  <div class="form-actions">
    <shiro:hasPermission name="crm:appuser:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/></shiro:hasPermission>&nbsp;
    <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
  </div>
</form:form>

<script type="text/javascript">


  function deletePhoto(e){
    e.preventDefault();
    document.getElementById('passportFile').src='';
    document.getElementById('photoFile').value = '';
    document.getElementById('passportPhoto').value = '';
  }

  function previewImage(file)
  {
    var MAXWIDTH  = 300;
    var MAXHEIGHT = 500;
    var div = document.getElementById('preview');
    if(file.value.length==0)return false;
    var v = file.value.split('.');
    v = v[v.length-1];
    v = v.toLocaleLowerCase();
    if(v!='jpg'&&v!='jpeg'&&v!='bmp'&&v!='png'){
      alertx("请上传图片格式的文件");
      document.getElementById('passportFile').src='';
      file.value = '';
      return false;
    }
    if (file.files && file.files[0])
    {
      div.innerHTML ='<img id=passportFile>';
      var img = document.getElementById('passportFile');
      img.onload = function(){
        var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
        img.width  =  rect.width;
        img.height =  rect.height;
      }
      var reader = new FileReader();
      reader.onload = function(evt){img.src = evt.target.result;}
      reader.readAsDataURL(file.files[0]);
    }
    else //兼容IE
    {
      var sFilter='filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src="';
      file.select();
      var src = document.selection.createRange().text;
      div.innerHTML = '<img id=passportFile>';
      var img = document.getElementById('passportFile');
      img.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = src;
      var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
      status =('rect:'+rect.top+','+rect.left+','+rect.width+','+rect.height);
      div.innerHTML = "<div id=divhead style='width:"+rect.width+"px;height:"+rect.height+"px;"+sFilter+src+"\"'></div>";
    }
  }
  function clacImgZoomParam( maxWidth, maxHeight, width, height ){
    var param = {top:0, left:0, width:width, height:height};
    if( width>maxWidth || height>maxHeight )
    {
      rateWidth = width / maxWidth;
      rateHeight = height / maxHeight;

      if( rateWidth > rateHeight )
      {
        param.width =  maxWidth;
        param.height = Math.round(height / rateWidth);
      }else
      {
        param.width = Math.round(width / rateHeight);
        param.height = maxHeight;
      }
    }

    param.left = Math.round((maxWidth - param.width) / 2);
    param.top = Math.round((maxHeight - param.height) / 2);
    return param;
  }
  $(function(){
    if($("#user").val()!=null&&$("#user").val().length>0){
      $("#passport").attr("disabled",true);
    }else{
      $("#passport").attr("disabled",false);
    }
    $("#passport").blur(function(e){
      if($("#passport").val().length == 0){
        return false;
      }
      $.ajax({
        url:"${ctx}/crm/appuser/hasPassport?passport="+$("#passport").val(),
        dataType:"json",
        type:"get",
        success:function(data){
          if(data.id!=null&&data.id.length>0){
            if($("#id").val()==data.id){
              return true;
            }
            swal("用户已存在", "姓名："+data.name+",电话："+data.phone, "error");
            $("#btnSubmit").hide();
            return true;
          }
          $("#btnSubmit").show();
        }
      })
    })
  })

</script>
</body>
</html>

