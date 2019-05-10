<%@ page import="order.controller.websocket.PushOrderWebSocket" %><%--
  Created by IntelliJ IDEA.
  User: berserker
  Date: 2019-05-10
  Time: 21:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    PushOrderWebSocket.httpSessions.remove(session);
    session.invalidate(); // 銷毀 session
    response.sendRedirect("LoginDemo.jsp");
%>
</body>
</html>
