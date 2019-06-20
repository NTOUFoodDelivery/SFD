function deliverCheckHistoryOrder() {
	var historyOrder;
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
                
                alert("success catch");
                
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
  m1 = historyOrder["Order"]["Meals"][0]["Rest_Name"];
  m2 = historyOrder["Order"]["Meals"][0]["Rest_Address"];
  m3 = historyOrder["Customer"]["Address"];
  m4 = historyOrder["Order"]["Total"];
  m6 = historyOrder["Order"]["Type_Count"]
  alert("m1");
  //m5 = deliver_order["meals"][0]["Food_Name"];
  //a = deliver_order["Deliver_Id"];
  rest_address_for_map = m2;
  DeleteMarkers();
  //var test=new string(m2);
  var rest_ID= new Array();
  var rest_address= new Array();
  rest_ID.push(historyOrder["Order"]["Meals"][0]["Rest_Name"]);
  rest_address.push(historyOrder["Order"]["Meals"][0]["Rest_Address"]);
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
			rest_address.push(historyOrder["Order"]["Meals"][0]["Rest_Address"]);
			rest_ID.push(historyOrder["Order"]["Meals"][i]["Rest_Name"]);
		}
	}

  //alert(rest_ID);
  var txt = '目的地:' + m3 + '<br>代付額:' + m4
      + '元<br>外送費:50元<br>訂單明細:<br>';
	for(var j=0;j<rest_ID.length;j++)
	{
		txt=txt+"<br>餐廳:" + rest_ID[j] + '<br>餐廳地址:' + rest_address[j] +'<br>'; 
		for (var i = 0; i < m6; i++) {
		
			if(historyOrder["Order"]["Meals"][i]["Rest_Name"]==rest_ID[j]){

				txt = txt + historyOrder["Order"]["Meals"][i]["Food_Name"] + ':'
				+ historyOrder["Order"]["Meals"][i]["Count"] + "個<br>";
				}
		}
		
	}
	
		
		
		
		
   
    OpenWindow=window.open("", "newwin", "height=250, width=250,toolbar=no,scrollbars="+scroll+",menubar=no");
   
    OpenWindow.document.write("<TITLE>配送紀錄</TITLE>");
   
    OpenWindow.document.write("<BODY BGCOLOR=#ffffff>");
   
   
   	OpenWindow.document.write(txt);
    /*OpenWindow.document.write("<h1>Hello!</h1>");

   
    OpenWindow.document.write("New window opened!");*/
	//OpenWindow.document.write("New window opened!<br>123");
    OpenWindow.document.write("</BODY>");
   
    OpenWindow.document.write("</HTML>");
   
    OpenWindow.document.close();
   
}