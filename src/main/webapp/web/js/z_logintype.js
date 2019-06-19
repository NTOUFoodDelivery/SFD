window.onload =function()
{
    $.ajax({
        url:'/LoginServlet',
        type:'GET',
        dataType:'json',
        success:function(data)
        {
            console.log(data);
            if(data === null)
            {
                window.location.href = '/web/login.html';
            }
        }
    })
}