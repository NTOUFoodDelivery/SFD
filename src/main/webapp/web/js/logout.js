function Member_logout()
{
    $.ajax({
        url:' /LogoutServlet',
        type:'GET',
        dataType:'json',
        success:function(data)
        {
            if(data.result=="SUCCESS")
            {
            window.location.href="./nologin.html";
            }
            else
            {
                alert("登出失敗，請稍後再試\n\n\n\n\n\n\n\n雖然這是遊戲但這可不是鬧著玩的!!!");

            }
        },
        error:function()
        {
            alert("登出失敗，請稍後再試\n\n\n\n\n\n\n\n雖然這是遊戲但這可不是鬧著玩的!!!");

        }

    })
}