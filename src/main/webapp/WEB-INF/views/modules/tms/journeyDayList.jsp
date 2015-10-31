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
  <style type="text/css">
    .popup{
      top:0%;
      left:0%;
      height: 100%;
      width: 100%;
      text-align: center;
      vertical-align:middle;
      background-color: #939595;
      opacity: 0.9;
      z-index: 10001;
      filter:Alpha(opacity=90);
      position:fixed;
    }
    .select2-drop{
      z-index: 11000;
    }

    .p-header-top{
      font-weight: bold;
      font-size: 15px;
      padding-left: 10px;
    }

    .p-header{
      font-weight: bold;
      font-size: 13px;
    }
    .p-message{
      color: #c15f2e;
      line-height: 20px;
      word-break:break-all;
    }
    .p-description{
      color: #454545;
      line-height: 20px;
      word-break:break-all;
    }
    .title{
      /*FILTER: progid:DXImageTransform.Microsoft.Gradient(gradientType=0,startColorStr=#b8c4cb,endColorStr=#f6f6f8); /!*IE 6 7 8*!/*/

      background: -ms-linear-gradient(top, #b8c4cb,#f6f6f8);        /* IE 10 */

      background:-moz-linear-gradient(top,#b8c4cb,#f6f6f8);/*火狐*/

      background:-webkit-gradient(linear, 0% 0%, 0% 100%,from(#b8c4cb), to(#f6f6f8));/*谷歌*/

      background: -webkit-gradient(linear, 0% 0%, 0% 100%, from(#b8c4cb), to(#f6f6f8));      /* Safari 4-5, Chrome 1-9*/

      background: -webkit-linear-gradient(top, #b8c4cb, #f6f6f8);   /*Safari5.1 Chrome 10+*/

      background: -o-linear-gradient(top, #b8c4cb, #f6f6f8);  /*Opera 11.10+*/
    }
    .title :hover{
      background-color: #b8c4cb;

    }
    .content{
      display: none;
    }
    .border {
      border:1px solid #cc3331;
      border-style: solid none;
    }
    #sortable1, #sortable2 {
      border: 1px solid #B4D434;
      width: 400px;
      min-height: 20px;
      list-style-type: none;
      float: left;
      margin: 0;
      padding: 5px 0 0 0;
      margin-right: 10px;
    }

  </style>
</head>
<body>
<ul class="nav nav-tabs">
  <li><a href="${ctx}/tms/groupUser/?group=${groupId}">旅游团用户列表</a></li>
  <li><a href="${ctx}/tms/groupUser/form?group=${groupId}&sort=10">旅游团用户添加</a></li>
  <li class="active"><a href="${ctx}/tms/journeyDay/?groupId=${groupId}">旅游团行程</a></li>
</ul>
<sys:message content="${message}"/>
<div style="margin-left: auto;margin-right: auto">
  <button id="addJourneyDay" type="button" class="btn btn-lg btn-primary">添加一天行程</button>&nbsp;&nbsp;&nbsp;
  <div style="margin-left: 10%;margin-top: 10px">
    <p>
      <label style="color: #dd1f26">当前团队行程：</label>
      <label style="color: #dd1f26;margin-left: 318px">团队行程模板：
        <select id="template" name="template" style="width:200px">
            <option value="" selected>清除行程模板</option>
          <c:forEach items="${fns:getGroupList()}" var="t">
            <option value="${t.id}">${t.name}</option>iug
          </c:forEach>
        </select>
      </label>
    </p>
    <ul id="sortable1" class="connectedSortable">
      <c:forEach items="${list}" var="journeyDay">
          <li style="list-style-type:none;">
            <div style="width:400px;background-color: #ECECEC" id="${journeyDay.id}">
              <div class="title">
                <p class="p-header-top">
                  ${journeyDay.title}
                  <i class="fa fa-times close-day" style="float: right;margin-right: 10px;"></i>
                  <a href="#" style="float: right;margin-right: 10px;" id="updateDay">修改</a>
                  <a href="#" style="float: right;margin-right: 10px;" id="addPlan">添加</a>
                </p>

              </div>
              <div class="content">
                <ul class="sortable-content" id="plan-${journeyDay.id}">
                <c:forEach items="${ journeyDay.list}" var="plan">
                  <li>
                    <div id="${plan.id}" class="border">
                      <p class="p-header">${plan.name}
                        <i class="fa fa-times close-plan" style="float: right;margin-right: 10px;"></i>
                        <a href="#" id="updatePlan" style="float: right;margin-right: 10px;">修改</a>
                      </p>
                      <c:if test="${plan.timeFlag==1}">
                        <p class='p-message'>${plan.time}</p>
                      </c:if>
                      <p class="p-description">${plan.description}</p>
                      <c:if test="${plan.messageFlag==1}">
                        <p class="p-message">${plan.message}</p>
                      </c:if>
                    </div>
                  </li>
                </c:forEach>
                </ul>
              </div>
            </div>

          </li>
      </c:forEach>
    </ul>
    <ul id="sortable2" class="connectedSortable">


    </ul>
  </div>
</div>
<div id="journeyList" style="display: none" class="popup">
  <div style="position:fixed;left:35%;top:50%;background-color: white;padding-top: 20px;padding-bottom: 20px">
    <form:form id="inputForm" modelAttribute="journeyDay" action="${ctx}/tms/journeyDay/save" method="post" class="form-horizontal">
      <form:hidden path="id"/>
      <form:hidden path="groupId"/>
      <sys:message content="${message}"/>
      <label style="font-size: 15px;font-weight:bold;">城市:</label>
      <sys:treeselect id="city" name="city.id" value="" labelName="city.name" labelValue=""
        cssStyle="width:300px" checked="true"   title="城市" url="/tim/city/treeData" notAllowSelectParent="true" cssClass="input-small" allowClear="true"/><br>
      <input id="btnSubmit" class="btn btn-default" type="button" value="保 存"/>&nbsp;
      <input id="btnCancel" class="btn" type="button" value="返 回" />

    </form:form>
  </div>
</div>

<div id="journeyPlan" style="display: none" class="popup">
  <%--<div style="position:fixed;left:20%;top:10%;background-color: white;padding-top: 20px;padding-bottom: 20px;width:600px">--%>
    <form style="margin-top: 30px">
        <input type="hidden" id="planId"/>
        <input type="hidden" id="dayId"/>
        <input type="hidden" id="cityIds"/>
        <div class="form-group" >
        <label for="plan-type">类型</label>
        <select id="plan-type" class="form-control"  tabindex="20000"  style="width:220px">
          <option value="" selected>清除类型模板</option>
          <c:forEach items="${fns:getDictList('journey_plan')}" var="t">
            <option value="${t.value}">${t.label}</option>
          </c:forEach>
        </select>
        </div>
        <div class="form-group">
          <label for="plan-list">列表</label>
          <select id="plan-list" class="form-control" tabindex="11000" style="width:220px">
          </select>
        </div>
        <div class="form-group">
          <label for="plan-name">标题</label>
          <input type="text" class="form-control" id="plan-name" placeholder="请输入标题"/>
        </div>
        <div class="form-group">
          <label for="hasTime">是否有时间</label>
          <label class="radio-inline" >
            <input type="radio" name="hasTime" id="hasTime" value="1"> 是
          </label>
          <label class="radio-inline" >
            <input type="radio" name="hasTime" value="0"> 否
          </label>
        </div>
        <div class="form-group">
          <label for="plan-time">时间</label>
          <input type="text" id="plan-time" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'H:mm:ss'})" class="Wdate"/>
        </div>
        <div class="form-group">
          <label for="plan-description">描述</label>
          <textarea class="form-control" rows="5" id="plan-description"></textarea>
        </div>
        <div class="form-group">
          <label for="plan-message">备注</label>
          <textarea class="form-control" rows="5" id="plan-message"></textarea>
        </div>
      <input id="plan-btnSubmit" class="btn btn-default" type="button" value="保 存"/>&nbsp;
      <input id="plan-btnCancel" class="btn" type="button" value="返 回" />
     </form>
  <%--</div>--%>
</div>

<script type="text/javascript">
  $(function(){
    $("#plan-type").change(function (e) {
      var dayId = $("#dayId").val();
      var type = e.target.value;
      $.ajax({
        url:"${ctx}/tms/journeyPlan/findTypeList",
        dataType:"json",
        type:"post",
        data:{"dayId":dayId,"type":type},
        success:function(data){
          $("#plan-list").empty();
          var str = '';
          for(var n in data){
            var obj = data[n];
            str += "<option value=\"" + obj.id + "\"/>" +obj.name+ "/>"
          }
          $("#plan-list").append(str);
        }
      })
    });

    $("#plan-btnSubmit").click(function(){
      var id = $("#planID").val();
      var type = $("#plan-type").val();
      var infoId = $("#plan-list").val();
      var dayId = $("#dayId").val();
      var name = $("#plan-name").val();
      var time = $("#plan-time").val();
      var description = $("#plan-description").val();
      var message = $("#plan-message").val();
      var hasTime = $("input[name='hasTime']:checked").val();
      var data = {
        "dayId":dayId,
        "id":id,
        "type":type,
        "name":name,
        "infoId":infoId,
        "time":time,
        "description":description,
        "message":message,
        "timeFlag":hasTime
      }
      $.ajax({
        url:"${ctx}/tms/journeyPlan/save",
        dataType:"json",
        type:"post",
        data:data,
        success:function(data){
          var str = plan(data);
          var ul = $("#plan-"+dayId);
          if(id!=undefined&&id.length>0)$("#"+id).parent().remove();
          ul.append(str);
          $("#journeyPlan").hide(300);
        }
      })
    });

    $("#updateDay").live('click',function(){
      var id = $(this).parent().parent().parent().attr("id");
      $.ajax({
        url:"${ctx}/tms/journeyDay/get?id="+id,
        type:"get",
        dataType:"json",
        ansyc :true,
        success:function(data) {
          $("#id").val(data.id);
          $("#groupId").val(data.groupId);
          $("#cityId").val(data.cityIds);
          $("#cityName").val(data.title.replace(new RegExp(/(-)/g),","));
          $("#journeyList").show(300);
          event.preventDefault();
          return false;

        }
      });
    });
    $("#addPlan").live('click',function(){
      var id = $(this).parent().parent().parent().attr("id");
      $("#plan-list").val('');
      $("#dayId").val(id);
      $("#planId").val('');
      $("#plan-name").val('');
      $("#plan-time").val('');
      $("#plan-description").val('');
      $("#plan-message").val('');
      $("input[name='hasTime'][value='0']").attr("checked",true);
      $("#journeyPlan").show(300);
    });

    $("#updatePlan").live('click',function(){
      var id = $(this).parent().parent().attr("id");
      $.ajax({
        url:"${ctx}/tms/journeyPlan/get?id="+id,
        type:"get",
        dataType:"json",
        success:function(data) {
          $("#plan-type").val(data.type).trigger("change");
          var options = data.infos;

          var str1 = ''
          for(var i in options){
            var option = options[i];
            if(option.id===data.infoId)
              str1 += "<option value='"+ option.id +"' selected />"+ option.name +"</option>";
            else
              str1 += "<option value='"+ option.id +"'/>"+ option.name +"</option>";
          }
          $("#plan-list").empty();
          $("#plan-list").val(data.infos);
          $("#planId").val(data.id);
          $("#dayId").val(data.dayId);
          $("#plan-name").val(data.name);
          $("#plan-time").val(data.time);
          $("#plan-description").val(data.description);
          $("#plan-message").val(data.message);
          $("input[name='hasTime'][value="+ data.timeFlag +"]").attr("checked",true);
          $("#journeyPlan").show(300);
        }
      });
    });

    $("#btnSubmit").click(function(){
      var cityId = $("#cityId").val();
      if(cityId.length==0){
        alertx("请选择城市！");
        return false;
      }
      $.ajax({
        url:"${ctx}/tms/journeyDay/save",
        dataType:"json",
        type:"post",
        data:{"id":$("#id").val(),"cityIds":$("#cityId").val()
          ,"title":$("#cityName").val(),"groupId":$("#groupId").val()},
        success:function(data){
          var str = "<li style=\"list-style-type:none;\">"
                  +"<div style=\"width:400px;background-color: #ECECEC\" id=\""+ data.id +"\">"
                  +"<div class=\"title\">"
                  +"<p class=\"p-header-top\">"
                  +data.title
                  +"<i class=\"fa fa-times close-day\" style=\"float: right;margin-right: 10px;\"></i>"
                  +"<a href=\"#\" style=\"float: right;margin-right: 10px;  onclick=\"updateDay('" +data.id+ "')\">修改</a>"
                  +"</p>"
                  +"</div>";
          if($("#"+ data.id).length>0)
            $("#"+ data.id).parent().remove();
          $("#sortable1").append(str);
          $("#journeyList").hide(300);
        }
      });
    });


    $("#addJourneyDay").click(function(){
      $("#cityId").val('');
      $("#cityName").val('');
      $("#id").val('');
      $("#journeyList").show(300);
    });
    $("#btnCancel").click(function(){
      $("#journeyList").hide(200);
    });
    $("#plan-btnCancel").click(function(){
      $("#journeyPlan").hide(200);
    });
    $("#importJourney").click(function(){

      $("#modelList").show(300);
    });
    $(".title").live('click',function(e){
      var $this = $(this);
      var content = $this.parent().children(".content");
      if(content.is(":hidden")){
        content.show(300);
      }else{
        content.hide(300);
      }
      e.preventDefault();
    });
    $( "#sortable1, #sortable2" ).sortable({
      connectWith: ".connectedSortable"
    }).disableSelection();
    $(".sortable-content" ).sortable({
      connectWith: ".sortable-content"
    }).disableSelection();
    $(".fa-times").live('mouseover',function(){
      $(this).addClass("fa-spin");
    });
    $(".fa-times").live('mouseout',function(){
      $(this).removeClass("fa-spin");
    });
    $(".close-day").live('click',function(){
      var _div = $(this).parent().parent().parent();
      var id = _div.attr("id");
      $.ajax({
        url:"${ctx}/tms/journeyDay/delete?id="+id,
        type:"get",
        success:function(data){
          data = eval("("+data+")");
          if(data.status == 1){
            _div.parent().remove();
          }
        }
      })
    });
    $(".close-plan").live('click',function(){
      var _div = $(this).parent().parent();
      var id = _div.attr("id");
      $.ajax({
        url:"${ctx}/tms/journeyPlan/delete?id="+id,
        type:"get",
        success:function(data){
          data = eval("("+data+")");
          if(data.status == 1){
            _div.parent().remove();
          }
        }
      })
    });
    $(".journeyTemplate").select2();
    $("#template").change(function(){
      var $this = $(this);
      var id = $this.val();
      $.ajax({
        url : "${ctx}/tms/journeyDay/getTemplate?groupId="+id,
        type:"get",
        dataType:"json",
        success : function(data){
          var str = "";
          for(var n in data){
            var obj = data[n];
            str +=  "<li style=\"list-style-type:none;\">"+
                    "<div style=\"width:400px;background-color: #ECECEC\" id=\""+ obj.id +"\">"+
                    "<div class=\"title\">"+
                    "<p class=\"p-header-top\">"+
                    obj.title +
                    "<i class=\"fa fa-times\" style=\"float: right;margin-right: 10px;\"></i>"
                    +"<a href=\"#\" style=\"float: right;margin-right: 10px;\">修改</a>"
                    +"</p>"

                    +"</div>"
                    +"<div class=\"content\">"
                    +"<ul class=\"sortable-content\" id=>";
            var str1 = "";
            for(var i in obj.list){
              var obj1 = obj.list[i];
              str1 += plan(obj1);
            }
            str += str1
                    +"</ul>"
                    +"</div>"
                    +"</div>"
                    +"</li>";
          }
          $("#sortable2").html(str);
          $( "#sortable1, #sortable2" ).sortable({
            connectWith: ".connectedSortable"
          }).disableSelection();
          $(".sortable-content" ).sortable({
            connectWith: ".sortable-content"
          }).disableSelection();
        }
      })
    });

  });
  function plan(obj1){
    var str1 = "<li>"
    +"<div id=\""+obj1.id+"\" class=\"border\">"
    +"<p class=\"p-header\">"+obj1.name
    +"<i class=\"fa fa-times\" style=\"float: right;margin-right: 10px;\"></i>"
    +"<a href=\"#\" id='updatePlan' style=\"float: right;margin-right: 10px;\">修改</a>"+
    "</p>";
    if(obj1.timeFlag==1){
      str1 += "<p class='p-message'>"+obj1.time+"</p>";
    }
    str1 += "<p class=\"p-description\">"+obj1.description+"</p>";
    str1 += "<p class=\"p-message\">"+obj1.message+"</p>";
    str1 +=  "</div>"
            + "</li>";
    return str1;
  }


</script>
</body>
</html>
