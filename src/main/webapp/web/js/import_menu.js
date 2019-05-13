function import_menu(n1, n2) {
    var JData;
    var sJson = JSON.stringify
        ({
            restName: n1, restAddress: n2
        });

    $.ajax({
        url: "https://ntou-sfd.herokuapp.com/ShowMenuServlet",
        type: "POST",
        async: true,
        dataType: "json",
        contentType: 'application/json; charset=UTF-8',
        data: sJson,
        success: function (JData) {

            alert("menu_SUCCESS!!!" + n1 + n2);
            $.each(JData, function () {
                var NumOfJData = JData.result.length;
                var stringJData = JSON.stringify(JData);
                var m1, m2, m3;
                $("body").append("<tr>" + stringJData + "</tr>");
                for (var i = 0; i < NumOfJData; i++) {
                    m1 = JData.result[i]["Food_Name"];
                    m2 = JData.result[i]["Cost"];
                    m3 = JData.result[i]["Description"];

                    var abc = 0;

                    if (i % 3 == 0 && abc % 3 == 0) {
                        temp = temp + '<div class="row no-collapse-1">';


                        temp = temp + '<section class="4u">' +
                            '<a href="#" class="image featured">' +
                            '<img src="images/pic01.jpg" alt="">' +
                            '</a>' +
                            '<div class="box">' +
                            '<p>' + m1 + "<br>" + m2 + '</p>' +
                            '<a href="#" class="button"  >Read More</a>' +
                            '</div>' +
                            '</section>';
                    }
                    else {
                        temp = temp + '<section class="4u">' +
                        '<a href="#" class="image featured">' +
                        '<img src="images/pic01.jpg" alt="">' +
                        '</a>' +
                        '<div class="box">' +
                        '<p>' + m1 + "<br>" + m2 + '</p>' +
                        '<a href="#" class="button"  >Read More</a>' +
                        '</div>' +
                        '</section>';
                    }
                    abc++;
                    if (i % 3 == 2 || i == NumOfJData - 1) { temp = temp + '</div>'; }
                }
                //JData = JSON.parse(stringJData);
                //push test


            });


        },

        error: function () {
            alert("ERROR!!!");
        }


    });
}
