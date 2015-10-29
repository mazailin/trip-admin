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
  <script type="text/javascript">
    function page(n,s){
      $("#pageNo").val(n);
      $("#pageSize").val(s);
      $("#searchForm").submit();
      return false;
    }
  </script>
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
      FILTER: progid:DXImageTransform.Microsoft.Gradient(gradientType=0,startColorStr=#b8c4cb,endColorStr=#f6f6f8); /*IE 6 7 8*/

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
        <form:select path="template">
          <form:options items="${fns:phoneOrderIds()}" itemLabel="orderId" itemValue="id" htmlEscape="false"/>
        </form:select>
      </label>
    </p>
    <ul id="sortable1" class="connectedSortable">
      <c:forEach items="${list}" var="journeyDay">
          <li style="list-style-type:none;">
            <div style="width:400px;background-color: #ECECEC" id="${journeyDay.id}">
              <div class="title">
                <p class="p-header-top">
                  ${journeyDay.title}
                  <i class="fa fa-times" style="float: right;margin-right: 10px;"></i>
                </p>

              </div>
              <div class="content">
                <ul class="sortable-content">
                <c:forEach items="${journeyDay.list}" var="plan">
                  <li>
                    <div id="${plan.id}" class="border">
                      <p class="p-header">${plan.name}</p>
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
      <label style="font-size: 15px;font-weight:bold;">名称:</label>
      <form:input path="title" htmlEscape="false" maxlength="50" class="required"/><br>
      <input id="btnSubmit" class="btn btn-default" type="submit" value="保 存"/>&nbsp;
      <input id="btnCancel" class="btn" type="button" value="返 回" />

    </form:form>
  </div>
</div>

<script type="text/javascript">
  $(function(){
    $("#addJourneyDay").click(function(){
      $("#journeyList").show(300);
    });
    $("#btnCancel").click(function(){
      $("#journeyList").hide(200);
    });
    $("#importJourney").click(function(){

      $("#modelList").show(300);
    });
    $(".title").click(function(e){
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
    $(".fa-times").mouseover(function(){
      $(this).addClass("fa-spin");
    });
    $(".fa-times").mouseout(function(){
      $(this).removeClass("fa-spin");
    });
    $(".fa-times").click(function(){
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
    $(".journeyTemplate").select2();

  });

</script>
</body>
</html>
