var ALLeaterHistoryOrder;

function eaterCheckHistoryOrder() {
	var historyOrder;
	ALLeaterHistoryOrder="";
	$.ajax({
            url: "/ShowHistoryOrderServlet",
            type: "GET",
            dataType: "json",
            success: function (JData) {
                //alert("get");
				for(var i=0;i<JData.length;i++)
				{
					generateHistoryOrder ( JData[i]);
				}
                
                //alert("success catch");
                SHoweaterHistoryOrder();
            },
        
            error: function () {
                alert("無法取得歷史紀錄，請重新整理或稍後再試");
            }
        });
}
function generateHistoryOrder(historyOrder){	
	//console.log(historyOrder);
  var m1, m2, m3, m4, m5, m6;
	
	
	
  //Deliver_order_ID = deliver_order["Order"]["Order_Id"];
  m1 = historyOrder["Deliver"]["User_Name"];
  m2 = historyOrder["Deliver"]["Account"];
  m3 = historyOrder["Customer"]["Address"];
  m4 = historyOrder["Order"]["Total"];
  m5 = historyOrder["Order"]["Start_Time"];
  m6 = historyOrder["Order"]["Type_Count"];
  
  //m5 = deliver_order["meals"][0]["Food_Name"];
  //a = deliver_order["Deliver_Id"];
  //var test=new string(m2);
  var rest_ID= new Array();
  
  rest_ID.push(historyOrder["Order"]["Meals"][0]["Rest_Name"]);
  
	for (var i = 0; i < m6; i++) 
	{
		var checkID=0;
		for(var j=0;j<=i;j++){
			
			if(rest_ID[j]==historyOrder["Order"]["Meals"][i]["Rest_Name"])
			{
				checkID=1;
			}
		}
		if(checkID==0)
		{
			
			rest_ID.push(historyOrder["Order"]["Meals"][i]["Rest_Name"]);
		}
	}

  //alert(rest_ID);
  var txt ='訂購時間'+m5+ '<br>外送員:'+m1+'<br>外送員帳號:'+m2+'<br>送達地址:' + m3 + '<br>食物總價:' + m4
      + '元<br>外送費:'+50*rest_ID.length+'元<br>訂單明細:<br>';
	for(var j=0;j<rest_ID.length;j++)
	{
		txt=txt+"<br>餐廳:" + rest_ID[j]  +'<br>'; 
		for (var i = 0; i < m6; i++) {
		
			if(historyOrder["Order"]["Meals"][i]["Rest_Name"]==rest_ID[j]){

				txt = txt + historyOrder["Order"]["Meals"][i]["Food_Name"] + ':'
				+ historyOrder["Order"]["Meals"][i]["Count"] + "個<br>";
				}
		}
		
	}
	ALLeaterHistoryOrder=ALLeaterHistoryOrder+txt;
}	
		
function SHoweaterHistoryOrder()
{	
		
   
    OpenWindow=window.open("", "newwin", "height=250, width=250,toolbar=no,scrollbars="+scroll+",menubar=no");
   
    OpenWindow.document.write("<TITLE>訂購紀錄</TITLE>");
   
    OpenWindow.document.write("<BODY BGCOLOR=#ffffff>");
   
   
   	OpenWindow.document.write(ALLeaterHistoryOrder);
    /*OpenWindow.document.write("<h1>Hello!</h1>");

   
    OpenWindow.document.write("New window opened!");*/
	//OpenWindow.document.write("New window opened!<br>123");
    OpenWindow.document.write("</BODY>");
   
    OpenWindow.document.write("</HTML>");
   
    OpenWindow.document.close();
}
