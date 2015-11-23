<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/11/19
  Time: 19:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
  <meta name="decorator" content="default"/>
    <title>错误信息</title>
</head>
<body>
  <div>
    ${error}

  </div>
  <button onclick="history.go(-1)" style="float:right">返回</button>
  <script type="text/javascript">
  </script>
</body>
</html>
