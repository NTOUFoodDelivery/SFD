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
                var name = user.Account;                           //要大寫還是小寫 Account
                $('#user_select').append('<option value ='+value+'>'+name+'</option>');
            }
        },
        error: function () {

        }
    })

});