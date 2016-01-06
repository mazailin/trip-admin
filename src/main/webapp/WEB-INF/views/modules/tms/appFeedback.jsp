<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/1/5
  Time: 14:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
  <title>手机功能使用评价</title>
  <meta name="decorator" content="default"/>
  <script src="http://cdn.hcharts.cn/highcharts/highcharts.js"></script>
  <script src="http://cdn.hcharts.cn/highcharts/modules/data.js"></script>
  <style type="text/css">
    .popup{
      top:0%;
      left:0%;
      height: 100%;
      width: 100%;
      text-align: center;
      vertical-align:middle;
      background-color: #FDFDFD;
      z-index: 10001;
      position:fixed;
    }
    .content{
      width: 80%;
      margin-left: 100px;
    }
    .date{
      float:left;
      position: fixed;
    }
  </style>
</head>
<body>
  <a style="float:right;margin-right: 20px;cursor: pointer" onclick="_close(true);">查看所有用户评论</a>
  <div id="feedback" style="display: none;" class="popup">
    <button style="margin-right: 20px;cursor: pointer;position: fixed;bottom: 20px;right: 20px" class="btn btn-primary" onclick="_close(false);">关闭</button>
  </div>
  <div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
  <table id="datatable" class="table table-striped" style="position: absolute">
    <thead>
      <tr>
        <th></th>
        <th>喜欢</th>
        <th>还行</th>
        <th>不喜欢</th>
      </tr>
    </thead>
    <tbody>
    </tbody>
  </table>

  <script type="text/javascript">
    function _close(flag){
      if(flag){
        $("#feedback").show(300);
        return;
      }
      $("#feedback").hide(300);
    }
    $.ajax({
      url:"${ctx}/tms/feedback/app/getList",
      dataType:"json",
      type:"post",
      async:false,
      success:function(data){
        var s = data.score;
        var tbody = $("#datatable tbody");
        for(var i = 0;i < s.length - 1 ;i++){
          var tr = "<tr>";
          var obj = s[i];
          tr += "<td>" + obj.name + "</td>";
          var list = obj.list;
          tr += "<td>" + list[2].name + "</td>";
          tr += "<td>" + list[1].name + "</td>";
          tr += "<td>" + list[0].name + "</td>";
          tr += "</tr>";
          tbody.append(tr);
        }
        var feedback = data.feedback;
        var div = $("#feedback");
        for(var i = 0;i < feedback.length;i++){
          var d = "<div class='well'>";
          d += "<p class='date'>" + feedback[i].date + "</p>";
          d += "<p class='content'>" + feedback[i].feedback + "</p>";
          d += "</div>";
          div.append(d);
        }
        $('#container').highcharts({
          data: {
            table: document.getElementById('datatable')
          },
          chart: {
            type: 'column'
          },
          title: {
            text: '手机功能评价'
          },
          yAxis: {
            allowDecimals: false,
            title: {
              text: '人数'
            }
          },
          tooltip: {
            formatter: function () {
              return '<b>' + this.series.name + '</b><br/>' +
                      this.point.y + ' ' + this.point.name.toLowerCase();
            }
          }
        });
      }
    });

  </script>
</body>
</html>
