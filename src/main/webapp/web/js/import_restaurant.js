var rest_inner;
$.ajax({
    url: "https://ntou-sfd.herokuapp.com/ShowRestInfoServlet",
    type: "GET",
    dataType: "json",
    success: function (JData) {

        //alert("SUCCESS!!!");
        var i = 0;
        //var stringJData = JSON.stringify(JData);
        //alert(stringJData);
        //這裡改用.each這個函式來取出JData裡的物件
        $.each(JData, function () {
            if(JData==null)alert("無法取得餐廳資訊，請重新整理");

            var NumOfJData = JData.result.length;

             rest_inner = document.getElementById("extra").value;
            var a1, a2, restimg;
            rest_inner = rest_inner + '<div class="container">';
            for (var i = 0; i < NumOfJData; i++) {
                var abc = 0;
                a1 = "'" + JData.result[i]["Rest_Name"] + "'";
                a2 = "'" + JData.result[i]["Rest_Address"] + "'";
                sent_rest_address=a2;
                sent_rest_name=a1;
                if (JData.result[i]["Rest_Photo"] == null) {
                    restimg = "images/Logo.jpg";

                }
                else {
                    restimg = JData.result[i]["Rest_Photo"];
                }
                if (i % 3 == 0 && abc % 3 == 0) {
                    rest_inner = rest_inner + '<div class="row no-collapse-1">';



                    rest_inner = rest_inner + '<section class="4u">' +
                        '<a href="#" class="image featured">' +
                        '<img src="' + restimg + '" alt="">' +
                        '</a>' +
                        '<div class="box">' +
                        '<p>' + JData.result[i]["Rest_Name"] + "<br>" + JData.result[i]["Rest_Address"] + '</p>' +
                        '<a href="#" class="button" onclick="import_menu(' + a1 + ',' + a2 + ')" >Read More</a>' +
                        '</div>' +
                        '</section>';

                }
                else {
                    rest_inner = rest_inner + '<section class="4u">' +
                    '<a href="#" class="image featured">' +
                    '<img src="' + restimg + '" alt="">' +
                    '</a>' +
                    '<div class="box">' +
                    '<p>' + JData.result[i]["Rest_Name"] + "<br>" + JData.result[i]["Rest_Address"] + '</p>' +
                    '<a href="#" class="button" onclick="import_menu(' + a1 + ',' + a2 + ')" >Read More</a>' +
                    '</div>' +
                    '</section>';
                }
                abc++;
                if (i % 3 == 2 || i == NumOfJData - 1) { rest_inner = rest_inner + '</div>'; }
            }
            //JData = JSON.parse(stringJData);
            rest_inner = rest_inner + "</div>";
            document.getElementById("extra").innerHTML = rest_inner;


        });
    },

    error: function () {
        alert("無法取得餐廳資訊，請重新整理");
    }
});



function changtorest()
{
    document.getElementById("extra").innerHTML = rest_inner;
}

function SwitchStatustode()
{
    $.ajax({
        url: "https://ntou-sfd.herokuapp.com/SwitchStatus",
        type: "POST",
        async: true,
        dataType: "json",
        contentType: 'application/json; charset=UTF-8',
        data: sJson,
        success: function (JData_menu) {alert("switch to deliver");}
    });
}