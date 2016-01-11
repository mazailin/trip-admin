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
      overflow:auto;
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
  <div style="z-index: 9999;position: fixed;width: 100%">
    <a style="float:right;margin-right: 20px;cursor: pointer;" onclick="_close(true);">查看所有用户评论</a>
    <button style="float:left;margin-left: 20px;" id="show" class="btn btn-warning" onclick="parse(2);">显示人数</button>
  </div>
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
    var $data = "";
    function _close(flag){
      if(flag){
        $("#feedback").show(300);
        return;
      }
      $("#feedback").hide(300);
    }
    function showUser(e){
      var $this = $(e);
      var id = $this.attr('id');
      $.ajax({
        url:"${ctx}/tms/feedback/app/getUser?code="+id,
        dataType:"json",
        type:"post",
        async:false,
        success:function(data){
          swal(data.id,data.name);
        }
      });
    }
    $.ajax({
      url:"${ctx}/tms/feedback/app/getList",
      dataType:"json",
      type:"post",
      async:false,
      success:function(data){
        $data = data;
        parse(1);

      }
    });
    function parse(type){//1百分比 2人数
      if(type==1){
        $('#show').html('显示人数');
        $('#show').attr('onclick','parse(2)');
      }else{
        $('#show').html('显示百分比');
        $('#show').attr('onclick','parse(1)');
      }
      var data = $data;
      var s = data.score;
      var tbody = $("#datatable tbody");
      tbody.html('');
      if(type == 2) {
        for (var i = 0; i < s.length - 1; i++) {
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
      }else{
        for (var i = 0; i < s.length - 1; i++) {
          var tr = "<tr>";
          var obj = s[i];
          tr += "<td>" + obj.name + "</td>";
          var list = obj.list;
          var sum = list[0].name*1 + list[1].name*1 + list[2].name*1;
          var avg = list[2].name/sum*100;
          tr += "<td>" + avg.toFixed(2) + "</td>";
          avg = list[1].name/sum*100;
          tr += "<td>" + avg.toFixed(2) + "</td>";
          avg = list[0].name/sum*100;
          tr += "<td>" + avg.toFixed(2) + "</td>";
          tr += "</tr>";
          tbody.append(tr);
        }
      }
      var feedback = data.feedback;
      var div = $("#feedback");
      if(div.find('div').length == 0) {
        for (var i = 0; i < feedback.length; i++) {
          var d = "<div class='well'>";
          d += "<p class='date'>" + feedback[i].date + "</p>";
          d += "<p class='content'>" + feedback[i].feedback + "</p>";
          d += "<a style='float:right;cursor:pointer' id= '"+ feedback[i].userCode  +"' onclick='showUser(this)'>" + '查看评价用户信息' + "</a>";
          d += "</div>";
          div.append(d);
        }
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
            text: type == 1 ? '%':'人数'
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
  </script>
</body>
</html>
