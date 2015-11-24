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
  <style type="text/css">
    ul{list-sytle:none}
    ul li{ list-sytle:none}
  </style>
</head>
<body>
  <div>
    <ul>
    <c:forEach items="${list}" var="journeyDay" >
      <li>
        第${journeyDay.dayNumber}天 ：${journeyDay.title}
        <ul>
        <c:forEach items="${journeyDay.list}" var="plan">
          <li>
            <div id="${plan.id}" class="border" groupId="${journeyDay.groupId}">
              <p class="p-header">${plan.name}
              </p>
              <c:if test="${plan.timeFlag==1}">
                <p class='p-message'>${plan.time}</p>
              </c:if>
              <p class="p-description">${plan.description}</p>
              <c:if test="${plan.longitude!=null && plan.latitude!=null}">
                <p class="p-longitude">维度：${plan.longitude}</p>
                <p class="p-latitude">经度：${plan.latitude}</p>
              </c:if>
              <p class="p-message">${plan.message}</p>
            </div>
          </li>
        </c:forEach>
        </ul>
      </li>
    </c:forEach>
    </ul>

  </div>
</body>
</html>
