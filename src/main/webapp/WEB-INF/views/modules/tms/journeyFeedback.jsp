<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/1/5
  Time: 17:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
  <title>旅程评价</title>
  <meta name="decorator" content="default"/>
  <script src="http://cdn.hcharts.cn/highcharts/highcharts.js"></script>
  <script src="http://cdn.hcharts.cn/highcharts/modules/data.js"></script>
  <style type="text/css">
    .popup{
      top:0%;
      left:0%;
      height: 100%;
      width: 100%;
      background-color: #FDFDFD;
      z-index: 10001;
      position:fixed;
    }
  </style>
</head>
<body>
  <div>
    <label style="color: #dd1f26;margin-left: 318px">请选择团队行程：
      <select id="template" name="template" style="width:200px">
        <option value="" selected>请选择团队行程</option>
        <c:forEach items="${groupList}" var="t">
          <option value="${t.id}">${t.name}</option>
        </c:forEach>
      </select>
    </label>
  </div>
  <div  style="display: none;" class="popup">
    <table id="feedback" class="table table-striped"></table>
    <button style="margin-right: 20px;cursor: pointer;position: fixed;bottom: 20px;right: 20px" class="btn btn-primary" onclick="_close(false);">关闭</button>
  </div>
  <a style="float:right;margin-right: 20px;cursor: pointer" onclick="_close(true);">查看平均评分</a>
  <div id="container" style="min-width: 310px; max-width: 800px; height: 400px; margin: 0 auto"></div>
  <table id="datatable" class="table table-striped" style="position: absolute">
    <thead>
      <tr>
        <th></th>
        <th>五星</th>
        <th>四星</th>
        <th>三星</th>
        <th>二星</th>
        <th>一星</th>
      </tr>
    </thead>
    <tbody>
    </tbody>
  </table>
  <script type="text/javascript">
    function _close(flag){
      if(flag){
        $(".popup").show(300);
        return;
      }
      $(".popup").hide(300);
    }
    $(function(){
      $('#template').change(function(){
        var groupId = $(this).val();
        $("#feedback").html('');
        $.ajax({
          url:"${ctx}/tms/feedback/journey/getList",
          dataType:"json",
          type:"post",
          async:false,
          data:{"groupId":groupId},
          success:function(data){
            var tbody = $("#datatable tbody");
            var feedback = $("#feedback");
            $.each(data,function(key){
              var _data = data[key];
              var tr = "<tr>";
              tr += "<td>"+_data.name+"</td>";
              var arr = _data.score;
              for(var i = arr.length; i > 0;i--){
                tr += "<td>" + arr[i-1] + "</td>";
              }
              tbody.append(tr);
              var div = "<tr><td>" + _data.name + "</td>"
                      + "<td>" + _data.avg + "</td></tr>";
              feedback.append(div);
            });
            $('#container').highcharts({
              chart: {
                type: 'bar'
              },
              title: {
                text: '行程信息评价统计'
              },
              yAxis: {
                min: 0,
                title: {
                  text: '人数/个',
                  align: 'high'
                },
                labels: {
                  overflow: 'justify'
                }
              },
              tooltip: {
                valueSuffix: '人'
              },
              plotOptions: {
                bar: {
                  dataLabels: {
                    enabled: true
                  }
                }
              },
              credits: {
                enabled: false
              },
              data: {
                table: 'datatable'
              }
            });
          }
        })
      });

    });

  </script>
</body>
</html>
