<%--
  Created by IntelliJ IDEA.
  User: makun
  Date: 2015/11/18
  Time: 12:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>二维码</title>
  <script type="text/javascript" src="${ctxStatic}/common/qrcode.js"></script>
  <style type="text/css">
    .grcode {
      width:120px;
      height: 120px;
      margin: 10px;
      padding-left: 5px;
    }
    .container {
      width: 100%;
    }
    .title {
      margin-top: 10px;
    }
    .qr{
      width: auto;
      line-height: 4px;
      border: 2px solid #73AD21;
      padding: 0 10px 0 10px;
      margin: 20px;
      float: left;
    }
  </style>
</head>
<body>
<div class="container">
    <c:forEach items="${list}" var="user">
      <div class="qr">
        <div class="title">
            <p>编码:${user.code}</p>
            <p>姓名:${user.name}</p>
            <p>手机号:${user.phone}</p>
        </div>
        <div name="grcode" id="${user.code}" code="${user.code}" phone="${user.phone}" class="grcode"></div>
        <script type="text/javascript">
          var qrcode = new QRCode(document.getElementById("${user.code}"), {width: 120, height: 120});
          qrcode.makeCode("${user.code}" + "," + "${user.phone}");
        </script>
      </div>
    </c:forEach>
</div>
</body>
</html>
