function upload()
{
	
}
function import_menu(n1, n2) {
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
            $.each(JData_menu, function () {
                var NumOfJData_menu = JData_menu.result.length;
                var stringJData_menu = JSON.stringify(JData_menu);
                var m1, m2, m3, m4;
                var foodimg;
                var addtocart;
                $("body").append("<tr>" + stringJData_menu + "</tr>");
                for (var i = 0; i < NumOfJData_menu; i++) {
                    m1 = JData_menu.result[i]["Food_Name"];
                    m2 = JData_menu.result[i]["Cost"];
                    m3 = JData_menu.result[i]["Description"];
                    m4 = JData_menu.result[i]["Image"];
				m5 = JData_menu.result[i]["Food_Id"];
				}
			})
		}
	})
}