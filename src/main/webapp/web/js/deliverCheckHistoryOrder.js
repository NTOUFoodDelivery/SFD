function deliverCheckHistoryOrder() {
	var historyOrder;
	$.ajax({
            url: "/ShowHistoryOrderServlet",
            type: "GET",
            dataType: "json",
            success: function (JData) {
                //alert("get");
                historyOrder = JData;
                alert(historyOrder);
                
            },
        
            error: function () {
                alert("無法取得歷史紀錄，請重新整理或稍後再試");
            }
        });
		
		
	/*console.log(deliver_order)
  var m1, m2, m3, m4, m5, m6;
	
	Order_Inf=
	{
		"orderID" : deliver_order["Order"]["Order_Id"],
		"deliverID":""	
	};
	
	
  Deliver_order_ID = deliver_order["Order"]["Order_Id"];
  m1 = deliver_order["Order"]["Meals"][0]["Rest_Name"];
  m2 = deliver_order["Order"]["Meals"][0]["Rest_Address"];
  m3 = deliver_order["Customer"]["Address"];
  m4 = deliver_order["Order"]["Total"];
  m6 = deliver_order["Order"]["Type_Count"]
  //m5 = deliver_order["meals"][0]["Food_Name"];
  a = deliver_order["Deliver_Id"];
  rest_address_for_map = m2;
  DeleteMarkers();
  //var test=new string(m2);
  var rest_ID= new Array();
  var rest_address= new Array();
  rest_ID.push(deliver_order["Order"]["Meals"][0]["Rest_Name"]);
  rest_address.push(deliver_order["Order"]["Meals"][0]["Rest_Address"]);
	for (var i = 0; i < m6; i++) 
	{
		var checkID=0;
		for(var j=0;j<=i;j++){
			
			if(rest_ID[j]==deliver_order["Order"]["Meals"][i]["Rest_Name"])
			{
				checkID=1;
			}
		}
		if(checkID==0)
		{
			rest_address.push(deliver_order["Order"]["Meals"][0]["Rest_Address"]);
			rest_ID.push(deliver_order["Order"]["Meals"][i]["Rest_Name"]);
		}
	}

  //alert(rest_ID);
  var txt = '目的地:' + m3 + '\n代付額:' + m4
      + '元\n外送費:50元\n訂單明細:\n';
	for(var j=0;j<rest_ID.length;j++)
	{
		txt=txt+"\n餐廳:" + rest_ID[j] + '\n餐廳地址:' + rest_address[j] +'\n'; 
		for (var i = 0; i < m6; i++) {
		
			if(deliver_order["Order"]["Meals"][i]["Rest_Name"]==rest_ID[j]){

				txt = txt + deliver_order["Order"]["Meals"][i]["Food_Name"] + ':'
				+ deliver_order["Order"]["Meals"][i]["Count"] + "個\n";
				}
		}
		
	}
	*/
		
		
		
		
   
    OpenWindow=window.open("", "newwin", "height=250, width=250,toolbar=no,scrollbars="+scroll+",menubar=no");
   
    OpenWindow.document.write("<TITLE>配送紀錄</TITLE>");
   
    OpenWindow.document.write("<BODY BGCOLOR=#ffffff>");
   
    OpenWindow.document.write("<h1>Hello!</h1>");
   
    OpenWindow.document.write("New window opened!");
	
    OpenWindow.document.write("</BODY>");
   
    OpenWindow.document.write("</HTML>");
   
    OpenWindow.document.close();
   
}