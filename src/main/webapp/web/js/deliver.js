

function deliverOffline(deliver_status) {
	if(deliver_now_order==""){
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




	}
	else
	{
		alert("訂單尚未完成");	
	}
    return deliver_status;
}
function generatResult(Deliver_Id,accept){
	return{
		"Deliver_Id":Deliver_Id,
		"accept":accept
	};
}
function OrderIsComing(deliver_order) {
	
	var m1,m2,m3,m4,m5,m6;
	m1 = deliver_order["Rest_Name"];
	m2 = deliver_order["Rest_Address"];
	m3 = deliver_order["Address"];
	m4 = deliver_order["Total"];
	m6 = deliver_order["Type_Count"]
	m5 = deliver_order["meals"][0]["Food_Name"];
	a = deliver_order["Deliver_Id"];
	rest_address_for_map=m2;
	DeleteMarkers();
	//var test=new string(m2);

	var txt="餐廳:"+m1+'\n餐廳地址:'+m2+'\n目的地:'+m3+'\n代付額:'+m4+'元\n外送費:50元\n訂單明細:\n';
	for(var i=0;i<m6;i++)
	{
		txt=txt+deliver_order["meals"][i]["Food_Name"]+':'+deliver_order["meals"][i]["Count"]+"個\n";
	}
	
	//import_Order_menu(m1,m2,txt);
	//initialPlace = new google.maps.LatLng(25.150892, 121.772461);
	var geocoder = new google.maps.Geocoder();
	geocoder.geocode( { 'address':m2}, function(results, status) {
	  if (status == google.maps.GeocoderStatus.OK) {
		map.setCenter(results[0].geometry.location);
		var marker = new google.maps.Marker({
			map: map,
			position: results[0].geometry.location
		});
	  } else {
		alert("Geocode was not successful for the following reason: " + status);
	  }
	  markers.push(marker);   
		
		//alert("clear");		
		marker.setMap(map);
	});
	deliver_now_order=txt;


}
 function DeleteMarkers() {   
        //Loop through all the markers and remove   
        for (var i = 0; i < markers.length; i++) {   
            markers[i].setMap(null);   
        }   
        markers = [];   
    };  
function post_Order_status() {
var rest_inner;
$.ajax({
    url: "https://ntou-sfd.herokuapp.com/ShowDeliveryStaffCurrentOrderServlet?userID=3",
    type: "GET",
    dataType: "json",
    success: function (JData) {

        //alert("SUCCESS!!!");
        var i = 0;
        //var stringJData = JSON.stringify(JData);
        //alert(stringJData);
        //這裡改用.each這個函式來取出JData裡的物件
        $.each(JData, function () {

            var NumOfJData = JData.result.length;

             rest_inner = document.getElementById("content").value;
            var a1, a2, restimg;
            rest_inner = rest_inner + '<div class="container">';
			// a1 = "'" + JData.result[0]["Rest_Name"] + "'";
            for (var i = 0; i < NumOfJData; i++) {
                var abc = 0;
                a1 = "'" + JData.result[i]["Rest_Name"] + "'";
                //a2 = "'" + JData.result[i]["Rest_Address"] + "'";
                  restimg = "images/Logo.jpg";
                if (i % 3 == 0 && abc % 3 == 0) {
                    rest_inner = rest_inner + '<div class="row no-collapse-1">';



                    rest_inner = rest_inner + '<section class="4u">' +
                        '<a href="#" class="image featured">' +
                        '<img src="' + restimg + '" alt="">' +
                        '</a>' +
                        '<div class="box">' +
                        '<p>' + JData.result[i]["Rest_Name"] + "<br>" + JData.result[i]["Food_Name"] + '</p>' +
                        '<a href="#" class="button" onclick="import_menu(' + a1 + ',' + a2 + ')" >Read More</a>' +
                        '</div>' +
                        '</section>';

                }
                else {
                    rest_inner = rest_inner + '<section class="4u">' +
                    '<a href="#" class="image featured">' +
                    '<img src="' + restimg + '" alt="">' +
                    '</a>' +
                    '<div class="box">' +
                    '<p>' + JData.result[i]["Rest_Name"] + "<br>" + JData.result[i]["Food_Name"] + '</p>' +
                    '<a href="#" class="button" onclick="import_menu(' + a1 + ',' + a2 + ')" >Read More</a>' +
                    '</div>' +
                    '</section>';
                }
                abc++;
                if (i % 3 == 2 || i == NumOfJData - 1) { rest_inner = rest_inner + '</div>'; }
            }
            //JData = JSON.parse(stringJData);
            rest_inner = rest_inner + "</div>";
            document.getElementById("content").innerHTML = rest_inner;


        });
    },

    error: function () {
        alert("無法取得餐廳資訊，請重新整理");
    }
});

}


function deliver_check_now_order()
{
	if(deliver_now_order=="")
	{
		alert("你現在沒有訂單喔<3")
	}
	else
	{
		alert(deliver_now_order);
	}
}
function deliver_finish_order()
{
	if(deliver_now_order!=""){
	if (confirm('完成以下訂單?\n'+deliver_now_order+'\n')) {
		alert("訂單完成!");
		deliver_now_order="";
		//client.send(generatResult(a,true));
	} 
	else {
		alert( "訂單尚未完成，同志尚須努力");
		//client.send(generatResult(a,false));
	}
	}
	else
	{
		alert("你現在沒有訂單喔<3");
	}
}
