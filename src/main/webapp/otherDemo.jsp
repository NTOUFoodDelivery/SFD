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
<a href="chatDemo.jsp">網頁跳轉BACK</a><br>
<a href="LogoutDemo.jsp">Logout</a><br>
<script>
    $('#testSubmit').click(function () {
        // var url="/IdentityRedirectServlet";
        var url="/SFD/IdentityRedirectServlet";
        $.ajax({
            xhrFields: {
                withCredentials: true
            },
            async:false,
            crossDomain: true,
            type: "GET",
            url: url,
            dataType: "json",
            success: function(data) {
                console.log(data);
            },
            error: function () {

            }
        })
    })
</script>
<script type="text/javascript">
    $(function(){
        $.ajaxSetup ({
            cache: false, //關掉AJAX 緩存
            async:false, //同步請求
            contentType:"application/x-www-form-urlencoded;charset=utf-8",
            complete:function(XMLHttpRequest,textStatus){
                // 用 XMLHttpRequest 拿 header，sessionstatus，
                var sessionstatus=XMLHttpRequest.getResponseHeader("sessionstatus");
                if(sessionstatus=="timeout"){
                    alert("重複登入或長時間無操作～請重新登入！！");
                    parent.location.href = "LoginDemo.jsp";
                }
            }
        });
    });
</script>
</body>
</html>
