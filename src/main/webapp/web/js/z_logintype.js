$(document).ready(function()
{
    $.ajax({
        url: '/LoginServlet',
        type: 'GET',
        dataType: 'json',
        async: false,
        success: function (data) {
            console.log(data);
            if (data) {
                var userType = data.User_Now;
                if (userType === "Administrator") {
                    window.location = "Administrator.html";
                }

            } else {
                window.location = "login.html";
                console.log("沒人登入！")
            }
        }
    })
})