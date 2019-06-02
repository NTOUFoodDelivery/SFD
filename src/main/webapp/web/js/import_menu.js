var showmenutemp;
var menu_infor;
function import_menu(n1, n2) {
    pagetype = "menu";
    var JData_menu;
    var sJson = JSON.stringify
        ({
            Rest_Name: n1, Rest_Address: n2
        });
     showmenutemp = "";
    $.ajax({
        url: "https://ntou-sfd.herokuapp.com/ShowMenuServlet",
        type: "POST",
        async: true,
        dataType: "json",
        contentType: 'application/json; charset=UTF-8',
        data: sJson,
        success: function (JData_menu) {
           // $.each(JData_menu, function () {
                var NumOfJData_menu = JData_menu.length;
                var stringJData_menu = JSON.stringify(JData_menu);
                var m1, m2, m3, m4;
                var foodimg;
                var addtocart;
                $("body").append("<tr>" + stringJData_menu + "</tr>");
                for (var i = 0; i < NumOfJData_menu; i++) {
                    console.log("NumOfJData_menu" +i);
                    console.log("i: " +i);
                    m1 = JData_menu[i]["Food_Name"];
                    m2 = JData_menu[i]["Cost"];
                    m3 = JData_menu[i]["Description"];
                    m4 = JData_menu[i]["Image"];
                    m5 = JData_menu[i]["Food_Id"];

                    var abc = 0;
                    if (m4 == null) {
                        foodimg = "images/Logo.jpg";

                    }
                    else {
                        foodimg = m4;
                    }


                    if (i % 3 == 0 && abc % 3 == 0) {

                        showmenutemp = showmenutemp + '<div class="row no-collapse-1">';


                        showmenutemp = showmenutemp + '<section class="4u">' +
                            '<a href="#" class="image featured">' +
                            '<img src="' + foodimg + '" alt="">' +
                            '</a>' +
                            '<div class="box">' +
                            '<p>' + m1 + "<br>" + '單價  :' + m2 + '數量  :' +
                            '<input id="buy_count'+m5+'" type="number" min="1" max="500" maxlength="3" value="1">' + '</p>' ;
                            m1="'"+m1+"'";
                            m2="'"+m2+"'";
                            m5="'"+m5+"'";
                            showmenutemp = showmenutemp +'<a  class="button" onclick="additemwithoutcount(' + m2 + ',' + m1 +',' + m5 + ')" >加入購物車</a>' +
                            '</div>' +
                            '</section>';
                    }
                    else {

                        showmenutemp = showmenutemp + '<section class="4u">' +
                            '<a href="#" class="image featured">' +
                            '<img src="' + foodimg + '" alt="">' +
                            '</a>' +
                            '<div class="box">' +
                            '<p>' + m1 + "<br>" + '單價  :' + m2 + '數量  :' +
                            '<input id="buy_count'+m5+'" type="number" min="1" max="500" maxlength="3" value="1">' + '</p>' ;
                            m1="'"+m1+"'";
                            m2="'"+m2+"'";
                            m5="'"+m5+"'";
                            showmenutemp = showmenutemp +'<a class="button" onclick="additemwithoutcount(' + m2 + ',' + m1 +',' + m5 + ')" >加入購物車</a>' +
                            '</div>' +
                            '</section>';//onclick="import_menu(' + a1 + ',' + a2 + ')" 
                    }
                    abc++;
                    if (i % 3 == 2 || i == NumOfJData_menu - 1) { showmenutemp = showmenutemp + '</div>'; }
                }
                //JData_menu = JSON.parse(stringJData_menu);
                //push test
                document.getElementById("extra").innerHTML = showmenutemp;

            //});


        },

        error: function () {
            alert("無法取得餐廳餐點資訊，請重新整理");
        }


    });
}

function changetomenu()
{
    document.getElementById("extra").innerHTML = showmenutemp;
}
