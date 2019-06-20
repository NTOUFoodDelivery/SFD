

function eatergetorder() {

    $.ajax({
        url: "/ShowOrderServlet",
        type: "GET",
        dataType: "json",
        success: function (JData) {

            //alert("SUCCESS!!!");
            var i = 0;
            var te="";
            var bill="";
            //var stringJData = JSON.stringify(JData);
            //alert(stringJData);
            //這裡改用.each這個函式來取出JData裡的物件
    
                if (JData == null) alert("還沒有購買喔:)");
                else {
                    var time = JDate["Order"]["Start_Time"];
                    var status=JData["Order"]["Order_Status"];
                    a = JData["Deliver_Id"];
                    for(var i =0;i<JData["Order"]["Type_Count"];i++)
                    {
                        bill=bill+"餐廳名稱: "+JData["Order"][i]["Rest_Name"]+"\n";
                        bill=bill+JData["Order"][i]["Food_Name"]+"\n 數量: "+JData["Order"][i]["Count"]+" 單價: "+JData["Order"][i]["Cost"]

                    }

                }

                te=te+"送出訂單時間: "+time+"\n"
                te=te+"目前訂單狀態: "+status+"\n"
                if(status=="DEALING"||"COMFIRMING")
                {
                    var deliver=JData["Deliver"]["User_Name"];
                    var deliverphone=JData["Deliver"]["Phone_Number"];
                    te=te+"外送員: "+deliver+"\n";
                    te=te+"外送員電話: "+deliverphone+"\n";
                }
                te=te+"餐點總金額"+JData["Order"]["Total"]+"\n";
                te=te+"訂單資料:\n"+bill;


                alert(te);
            },

        error: function () {
                    alert("無法取得餐廳資訊，請重新整理");
                }
    });
}


