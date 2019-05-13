function importrestaurant() {
    $.ajax({
        url: "https://ntou-sfd.herokuapp.com/ShowRestInfoServlet",
        type: "GET",
        dataType: "json",
        success: function (JData) {

            //alert("SUCCESS!!!");
            var i = 0;

            //這裡改用.each這個函式來取出JData裡的物件
            $.each(JData, function () {
                var NumOfJData = JData.result.length;
                var stringJData = JSON.stringify(JData);
                for (var i = 0; i < NumOfJData; i++) {
                    $("body").append("<tr>" +
                        "<td>" + JData.result[i]["Rest_Id"] + " </td>" +  //i=0→Wing; i=1→Wind; i=2→Edge
                        "<td>" + JData.result[i]["Rest_Name"] + " </td>" +   //i=0→20;   i=1→18;   i=2→25
                        "<td>" + JData.result[i]["Rest_Address"] + " </td>" +
                        "<td>" + JData.result[i]["Description"] + " </td>" +//i=0→182;  i=1→165;  i=2→171
                        "<br>" +
                        "</tr>");
                }
                //JData = JSON.parse(stringJData);


            });
        },

        error: function () {
            alert("import restaurant ERROR!!!");
        }
    });

}