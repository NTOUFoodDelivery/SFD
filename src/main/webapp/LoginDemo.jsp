<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: berserker
  Date: 2019-05-07
  Time: 17:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>SFD登入測試</title>
</head>
<body>
<h1>登入後台測試</h1>
<%
    request.setCharacterEncoding("utf-8");
%>
<%
    List<String> info=(List<String>)request.getAttribute("info");
    if(info!=null){
        Iterator<String> iter=info.iterator();
        while(iter.hasNext()){
%>
<h4><%=iter.next()%></h4>
<%
        }
    }
%>
<form action="LogInServlet" method="post" onSubmit="return validate(this)">
    Username:<input type="text" name="userID"><br>
    Password:<input type="password" name="password"><br>
    <input type="submit" value="Login">
    <input type="reset" value="reset">
</form>
</body>
</html>
