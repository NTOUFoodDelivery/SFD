<%--
  Created by IntelliJ IDEA.
  User: berserker
  Date: 2019-05-10
  Time: 20:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>
<h1>OOSOSOSO</h1>
<button id="testSubmit">testSubmit</button><br>
<a href="LogoutDemo.jsp">Logout</a><br>
<script>
    $('#testSubmit').click(function () {
        $.ajax({
            xhrFields: {
                withCredentials: true
            },
            crossDomain: true,
            type: "GET",
            url: "http://localhost:8080/SFD/IdentityRedirectServlet",
            dataType: "json",
            success: function(data) {
                console.log(data);
            },
            error: function () {

            }
        })
    })
</script>
</body>
</html>
