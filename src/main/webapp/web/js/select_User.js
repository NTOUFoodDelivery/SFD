$(document).ready(function(){
    const url = "/MemberServlet/showUsers"; // 正式 url
        $.ajax({
        type: "GET",
        url: url,
        dataType: "json",
        success: function(data) {
            console.log(data);
            for(var i = 0;i< data.length;i++){
                var user = data[i];
                var value = user.User_Id;
                var name = user.Account;
                $('#user_select').append('<option value ='+value+'>'+name+'</option>');
                $('#user_select1').append('<option value ='+value+'>'+name+'</option>');
            }
        },
        error: function () {

        }
    })

});