var rest_inner;
var rest_infor;




$.ajax({
    url: "https://ntou-sfd.herokuapp.com/ShowRestInfoServlet",
    type: "GET",
    dataType: "json",
    success: function (JData) {
        //alert("get");
        rest_infor = JData;
        alert("get");
    },

    error: function () {
        alert("無法取得餐廳資訊，請重新整理或稍後再試");
    }
});

import_restaurant("");
function import_restaurant(searcher) {
    pagetype = "restaurant";


            //alert("SUCCESS!!!");
            var i = 0;
            //var stringJData = JSON.stringify(JData);
            //alert(stringJData);
            //這裡改用.each這個函式來取出JData裡的物件
            alert(rest_infor);
                if (rest_infor == null) alert("無法取得餐廳資訊，請重新整理");

                var NumOfJData = rest_infor.length;

                //rest_inner = document.getElementById("extra").value;
                var a1, a2, restimg;
                rest_inner = '<div class="container">';
                for (var i = 0, indexi = 0; i < NumOfJData; i++) {
                    if ($.inArray(searcher, rest_infor[i]["Rest_Name"]) != -1 || searcher == "") {
                        
                        var abc = 0;
                        a1 = "'" + rest_infor[i]["Rest_Name"] + "'";
                        a2 = "'" + rest_infor[i]["Rest_Address"] + "'";
                        sent_rest_address = a2;
                        sent_rest_name = a1;
                        if (rest_infor[i]["Rest_Photo"] == null) {
                            restimg = "images/Logo.jpg";

                        }
                        else {
                            restimg = rest_infor[i]["Rest_Photo"];
                        }
                        if (indexi % 3 == 0) {
                            rest_inner = rest_inner + '<div class="row no-collapse-1">';



                            rest_inner = rest_inner + '<section class="4u">' +
                                '<a href="#" class="image featured">' +
                                '<img src="' + restimg + '" alt="">' +
                                '</a>' +
                                '<div class="box">' +
                                '<p>' + rest_infor[i]["Rest_Name"] + "<br>" + rest_infor[i]["Rest_Address"] + '</p>' +
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
                                '<p>' + rest_infor[i]["Rest_Name"] + "<br>" + rest_infor[i]["Rest_Address"] + '</p>' +
                                '<a href="#" class="button" onclick="import_menu(' + a1 + ',' + a2 + ')" >Read More</a>' +
                                '</div>' +
                                '</section>';
                        }
                        abc++;
                        if (indexi % 3 == 2 || indexi == NumOfJData - 1) { rest_inner = rest_inner + '</div>'; }
                        indexi++;
                    }
                }
                //JData = JSON.parse(stringJData);
                rest_inner = rest_inner + "</div>";
                document.getElementById("extra").innerHTML = rest_inner;


           




}


function changtorest() {
    document.getElementById("extra").innerHTML = rest_inner;
    pagetype = "restaurant";
}

function SwitchStatustode() {
    $.ajax({
        url: "https://ntou-sfd.herokuapp.com/SwitchStatus",
        type: "POST",
        async: true,
        dataType: "json",
        contentType: 'application/json; charset=UTF-8',
        data: sJson,
        success: function (JData_menu) { alert("switch to deliver"); }
    });
}