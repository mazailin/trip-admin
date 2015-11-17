<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/11/17
  Time: 10:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>行程预览</title>
</head>
<body>
  <div>
    <c:forEach items="${journeyDayList}" var="journeyDay">
      <ul></ul>
        <c:forEach items="${journeyDay.getList}" var="plan">

        </c:forEach>

    </c:forEach>

  </div>
</body>
</html>
