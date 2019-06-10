$(document).ready(function(){
    const url = "/ModifyMemberServlet"; // 正式 url
    $.ajax({
        type: "GET",
        url: url,
        dataType: "json",
        success: function(data) {
            for(var i = 0;i< data.length;i++){
                var user = data[i];
                var value = user.User_Id;
                var name = user.Account;                           //要大寫還是小寫 Account
                $('#user_select').append('<option value ='+value+'>'+name+'</option>');
            }
            $('#user_select').on('change', function() {
                var optionSelected = $("option:selected", this);
                var valueSelected = this.value;
                console.log(valueSelected);
                console.log(optionSelected[0].innerHTML);
            });
        },
        error: function () {

        }
    })

})