function import_menu(n1, n2) {
    var JData_menu;
    var sJson = JSON.stringify
        ({
            Rest_Name: n1, Rest_Address: n2
        });
        var showmenutemp=document.getElementById("extra").value;
    $.ajax({
        url: "https://ntou-sfd.herokuapp.com/ShowMenuServlet",
        type: "POST",
        async: true,
        dataType: "json",
        contentType: 'application/json; charset=UTF-8',
        data: sJson,
        success: function (JData_menu) {

            alert("menu_SUCCESS!!!" + n1 + n2);
            $.each(JData_menu, function () {
                var NumOfJData_menu = JData_menu.result.length;
                var stringJData_menu = JSON.stringify(JData_menu);
                var m1, m2, m3,m4;
                var addtocart;
                $("body").append("<tr>" + stringJData_menu + "</tr>");
                for (var i = 0; i < NumOfJData_menu; i++) {
                    m1 = JData_menu.result[i]["Food_Name"];
                    m2 = JData_menu.result[i]["Cost"];
                    m3 = JData_menu.result[i]["Description"];
                    m4 = JData_menu.result[i]["Image"];

                    var abc = 0;
                    

                    if (i % 3 == 0 && abc % 3 == 0) {
                        showmenutemp = showmenutemp + '<div class="row no-collapse-1">';


                        showmenutemp = showmenutemp + '<section class="4u">' +
                            '<a href="#" class="image featured">' +
                            '<img src="'+m4+'" alt="">' +
                            '</a>' +
                            '<div class="box">' +
                            '<p>' + m1 + "<br>" + m2 + '</p>' +
                            '<a href="#" class="button"  >加入購物車</a>' +
                            '</div>' +
                            '</section>';
                    }
                    else {
                        showmenutemp = showmenutemp + '<section class="4u">' +
                        '<a href="#" class="image featured">' +
                        '<img src="images/pic01.jpg" alt="">' +
                        '</a>' +
                        '<div class="box">' +
                        '<p>' + m1 + "<br>" + m2 + '</p>' +
                        '<a href="#" class="button"  >加入購物車</a>' +
                        '</div>' +
                        '</section>';
                    }
                    abc++;
                    if (i % 3 == 2 || i == NumOfJData_menu - 1) { showmenutemp = showmenutemp + '</div>'; }
                }
                //JData_menu = JSON.parse(stringJData_menu);
                //push test


            });


        },

        error: function () {
            alert("無法取得餐廳餐點資訊，請重新整理");
        }


    });
}


