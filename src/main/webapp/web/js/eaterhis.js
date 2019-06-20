function eatergethistory() {
    $.ajax({
        url: "/ShowHistoryOrderServlet",
        type: "GET",
        dataType: "json",
        success: function (JData) {

            //alert("SUCCESS!!!");
            var i = 0;
            //var stringJData = JSON.stringify(JData);
            //alert(stringJData);
            //這裡改用.each這個函式來取出JData裡的物件
            // $.each(JData, function () {
            if (JData == null) alert("沒有購買紀錄悠^^");



            //  });
        },


    });
}

function eatergetorder() {

    $.ajax({
        url: "/ShowOrderServlet",
        type: "GET",
        dataType: "json",
        success: function (JData) {

            //alert("SUCCESS!!!");
            var i = 0;
            //var stringJData = JSON.stringify(JData);
            //alert(stringJData);
            //這裡改用.each這個函式來取出JData裡的物件
            $.each(JData, function () {
                if (JData == null) alert("還沒有購買喔:)");
                else {
                    var m1, m2, m3, m4, m5, m6;
                    m1 = JData["Rest_Name"];
                    m2 = JData["Rest_Address"];
                    m3 = JData["Address"];
                    m4 = JData["Total"];
                    m6 = JData["Type_Count"]
                    m5 = JData["meals"][0]["Food_Name"];
                    a = JData["Deliver_Id"];

                }


            },

                error: function () {
                    alert("無法取得餐廳資訊，請重新整理");
                }
    });
}


