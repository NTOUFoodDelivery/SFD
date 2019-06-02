window.onload =function()
{
    $.ajax({
        url:'/SFD/LoginServlet',
        type:'GET',
        dataType:'json',
        success:function(data)
        {
            console.log(data);
            var cmd =data[0];
            if(cmd =="reload")
            {
                window.location.href = 'LoginDemo.html';
            }else{
                var userName=data.User_Name;
                var userType =data.User_Type;
                // $('#welcome_userName')[0].innerHTML =userName;
                // $('#welcome_userType')[0].innerHTML =userType;
            }
        }
    })
}