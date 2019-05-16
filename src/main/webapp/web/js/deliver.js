function deliverOffline(deliver_status) {
	
	var deliver_temp="";
	
				if(deliver_status==0)
				{
					deliver_temp=deliver_temp+'<img src="images/offline.svg" style="z-index:999;position:absolute;width:100%;" />';
					deliver_status=1;
				}
				else
				{
					
					deliver_temp="";
					deliver_status=0;
				}
			document.getElementById("offlineImg").innerHTML = deliver_temp;
			//document.getElementById("content").innerHTML = deliver_temp;




        
    return deliver_status;
}
function OrderIsComing(deliver_order) {
	var m1,m2,m3,m4;
	m1 = deliver_order["Rest_Name"];
	m2 = deliver_order["Rest_Address"];
	m3 = deliver_order["Address"];
	m4 = deliver_order["Total"];
	var txt="餐廳:"+m1+'\n餐廳地址:'+m2+'\n目的地:'+m3+'\n代付額:'+m4+'元\n外送費:50元';
	if (confirm(txt)) {
		alert("You pressed OK!");
	} 
	else {
		alert( "You pressed Cancel!");
	}

}
function TransferDeliverOrder(event){
	//var NumOfJData = event.data.length;
	var m1,m2,m3,m4;
	var J_data=JSON.parse(event.data);
	m1 = J_data["Customer_Id"];
	alert(m1);
    m2 = event.data["Cost"];
    m3 = event.data["Description"];
    m4 = event.data["Image"];
                    //var stringJData = JSON.stringify(JData);
                    /*for (var i = 0; i < NumOfJData; i++) {
                        /*$("body").append("<tr>" +
                            "<td>" + JData.result[i]["Rest_Id"] + " </td>" +  //i=0→Wing; i=1→Wind; i=2→Edge
                            "<td>" + JData.result[i]["Rest_Name"] + " </td>" +   //i=0→20;   i=1→18;   i=2→25
                            "<td>" + JData.result[i]["Rest_Address"] + " </td>" +
                            "<td>" + JData.result[i]["Description"] + " </td>" +//i=0→182;  i=1→165;  i=2→171
                            "<br>" +
							
                    }*/
	return m1;
}