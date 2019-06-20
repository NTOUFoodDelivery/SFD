
 var te="";
 var orderid;
function eatergetorder() {

    $.ajax({
        url: "/ShowOrderServlet",
        type: "GET",
        dataType: "json",
        success: function (JData) {

            //alert("SUCCESS!!!");
            var i = 0;
           
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


function eater_finish_order() {


    
    if (te != "") {
      if (confirm('完成以下訂單?\n'  + te + '\n')) {
  
        te = "";
        //client.send(generatResult(a,true));
        var temp123;
        temp123 = "/SendOrderServlet?orderID=" + $.ajax({url: "/ShowOrderServlet",type: "GET",dataType: "json",success: function (JData) {return JData["Order"]["Order_Id"]}});
  
        $.ajax({
          url: temp123,//"/SendOrderServlet?orderID="+Order_Id,
          type: "get",
          async: true,
          dataType: "json",
          contentType: 'application/json; charset=UTF-8',
          data: null,
          success:
  
              function (JData_menu) {
                alert("訂單完成!");
                //window.location="./index_eater.html";
  
                //alert("switch to deliver");
              }
        });
  
      } else {
        alert("訂單尚未完成");
        //client.send(generatResult(a,false));
      }
    } else {
      alert("你現在沒有訂單喔<3");
    }
  }