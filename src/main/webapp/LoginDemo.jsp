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
    <style>
        #center {
            position:absolute; top:50%; left:50%;
            margin-left: -105px;
            margin-top: -170px;
        }
    </style>
</head>

<body>

<div id="center">
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
    <form action="LogInServlet" method="post">
        Username:<input type="text" name="account"><br>
        Password:<input type="password" name="password"><br>
        <select class="custom-select" name="userType">
            <option selected>Choose...</option>
            <option value="deliver">外送員</option>
            <option value="eater">食客</option>
            <option value="admin">admin</option>
        </select>
        <input type="submit" value="Login">
        <input type="reset" value="reset">
    </form>
</div>


</body>
</html>
