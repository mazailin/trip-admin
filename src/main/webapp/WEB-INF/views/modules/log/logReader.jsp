<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/12/7
  Time: 16:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
  <title>日志监控</title>
  <meta name="decorator" content="default"/>
  <style type="text/css">
  </style>
<script src="${ctxStatic}/hchart/highcharts.js" type="text/javascript"></script>
</head>
<body>
  <form class="breadcrumb form-search">
    <ul class="ul-form">
      <li>
        <label>开始日期:</label>
        <input id="fromDate" name="fromDate" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate required"
                onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
      </li>
      <li>
        <label>结束日期:</label>
        <input id="toDate" name="toDate" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate required"
                onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
      </li>
      <li>
        <button id="search" onclick="getLog(event)" class="btn">查询</button>
      </li>
    </ul>
  </form>
  <%--<button id="param_0" name="param_0" onclick="showLog(this)" class="btn btn-warning">天气</button>--%>
  <button id="param_1" name="param_1" onclick="showLog(this)" class="btn btn-default">相机</button>
  <button id="param_2" name="param_2" onclick="showLog(this)" class="btn btn-default">工具箱</button>
  <button id="param_3" name="param_3" onclick="showLog(this)" class="btn btn-default">当地玩乐</button>
  <button id="param_4" name="param_4" onclick="showLog(this)" class="btn btn-default">地图</button>
  <button id="param_5" name="param_5" onclick="showLog(this)" class="btn btn-default">娱乐</button>
  <button id="param_6" name="param_6" onclick="showLog(this)" class="btn btn-default">音乐</button>
  <button id="param_7" name="param_7" onclick="showLog(this)" class="btn btn-default">视频</button>
  <button id="param_8" name="param_8" onclick="showLog(this)" class="btn btn-default">电话</button>
  <button id="param_9" name="param_9" onclick="showLog(this)" class="btn btn-default">聊天</button>
  <button id="param_10" name="param_10" onclick="showLog(this)" class="btn btn-default">wifi</button>
  <button id="param_11" name="param_11" onclick="showLog(this)" class="btn btn-default">紧急呼叫</button>
  <button id="param_12" name="param_12" onclick="showLog(this)" class="btn btn-default">一键还原</button>
  <%--<button id="param_13" name="param_13" onclick="showLog(this)" class="btn btn-default">图库</button>--%>
  <button id="param_14" name="param_14" onclick="showLog(this)" class="btn btn-default">翻译</button>
  <button id="param_15" name="param_15" onclick="showLog(this)" class="btn btn-default">租车</button>
  <button id="param_16" name="param_16" onclick="showLog(this)" class="btn btn-default">我的行程</button>
  <button id="param_17" name="param_17" onclick="showLog(this)" class="btn btn-default">个人信息</button>
  <div id="container"></div>
  <div id="table_data"></div>
  <script type="text/javascript">
    var json = '';
    function showLog(e){

      var $this = $(e);
      $("button").removeClass("btn-warning");
      $("button").addClass("btn-default");
      $this.removeClass("btn-default");
      $this.addClass("btn-warning");
      if(json=='')return false;
      var list = json[$this.attr("id")];
      var name = $this.text();
      var fromDate = $("#fromDate").val();
      var toDate = $("#toDate").val();
      var dateArr = DateDiff(fromDate,toDate);
      var series = [];
      for(var j = 0;j < list.length;j++){
        var c = {};
        var o = list[j];
        c.name = o.name;
        var _list = o.list;
        var _data = [];
        for (var i in dateArr) {
          for (var k = 0; k < _list.length; k++) {
            var _log = _list[k];
            var flag = true;
            if (_log.time == dateArr[i]) {
              _data[i] = _log.number;
              flag = false;
              break;
            }
          }
          if (flag) {
            _data[i] = 0;
          }
        }
        c.data = _data;
        series[j] = c;
      }
      $('#container').highcharts({
        title: {
          text: name + '——使用情况',
          x: -20 //center
        },
        plotOptions: {
          line: {
            dataLabels: {
              enabled: true
            },
            enableMouseTracking: true
          }
        },
        subtitle: {
          text: '日期：'+fromDate+"~"+toDate,
          x: -20
        },
        xAxis: {
          categories: dateArr
        },
        yAxis: {
          title: {
            text: '次数'
          },
          plotLines: [{
            value: 0,
            width: 1,
            color: '#808080'
          }]
        },
        tooltip: {
          valueSuffix: '次'
        },
        legend: {
          layout: 'vertical',
          align: 'right',
          verticalAlign: 'middle',
          borderWidth: 0
        },
        series: series
      });
      var table = "<table class='table table-striped'>";
      for(var i = 0;i < series.length;i++){
        name = series[i].name;
        var data = series[i].data;
        var trs = '';
        for(var j in data){
          var tr = "<tr>";
            var td = "<td>"+dateArr[j]+"</td>";
            td += "<td>" + name + "</td>";
            td += "<td>" + data[j] + "</td>";
          tr += td + "</tr>";
          trs += tr;
        }

        table +=trs;
      }
      table += "</table>";
      $("#table_data").html(table);

    }

    //计算天数差的函数，通用
    function  DateDiff(sDate1,  sDate2){    //sDate1和sDate2是2006-12-18格式
      var  aDate,  oDate1,  oDate2,  iDays;
      aDate  =  sDate1.split("-");
      oDate1  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0])  ;  //转换为12-18-2006格式
      aDate  =  sDate2.split("-");
      oDate2  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0]);
      iDays  =  parseInt(Math.abs(oDate1  -  oDate2)  /  86400000);    //把相差的毫秒数转换为天数
      var arr = [];
      for(var i = 0;i <= iDays;i++){
        var n = oDate1.getTime();
        n = n + 86400000 * i;
        n = new Date(n);
        arr[i] = n.getFullYear() + "-" + (n.getMonth() + 1) + "-" + (n.getDate()<10?('0'+ n.getDate()):n.getDate());
      }
      return  arr;
    }

    function getLog(e) {
      loading("正在加载，请稍后。。。");
      e.preventDefault();
      var fromDate = $("#fromDate").val();
      var toDate = $("#toDate").val();
      $.ajax({
        url:"${ctx}/log/logReader/getLog",
        dataType:"json",
        type:"post",
        data:{"fromDate":fromDate,"toDate":toDate},
        success:function(data){
          json = data;
          closeTip();
          $("#param_8").click();
        }
      });
    }

    $(function(){
      $("g").live('click',function(){
        var $this = $(this);
        var name = $this.children('text').html();
      });
    })

  </script>
</body>

</html>
