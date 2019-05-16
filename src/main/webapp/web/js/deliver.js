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
	
	var m1,m2,m3,m4,m5,m6,a;
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
	function generatResult(Deliver_Id,accept){
		return{
			"Deliver_Id":Deliver_Id,
			"accept":accept
		};
	}
	var txt="餐廳:"+m1+'\n餐廳地址:'+m2+'\n目的地:'+m3+'\n代付額:'+m4+'元\n外送費:50元\n訂單明細:\n';
	for(var i=0;i<m6;i++)
	{
		txt=txt+deliver_order["meals"][i]["Food_Name"]+':'+deliver_order["meals"][i]["Count"]+"個\n";
	}
	deliver_now_order=txt;
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

	if (confirm(txt)) {
		alert("You pressed OK!");
		//client.send(generatResult(a,true));
	} 
	else {
		alert( "You pressed Cancel!");
		//client.send(generatResult(a,false));
	}

}
 function DeleteMarkers() {   
        //Loop through all the markers and remove   
        for (var i = 0; i < markers.length; i++) {   
            markers[i].setMap(null);   
        }   
        markers = [];   
    };  

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
function post_Order_status(n1, n2) {
    var JData_menu;
    var sJson = JSON.stringify
        ({
            deliverID: n1, accept: n2
        });
        var showmenutemp="";
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
                var m1, m2, m3,m4;
                var foodimg;
                var addtocart;
                $("body").append("<tr>" + stringJData_menu + "</tr>");
                for (var i = 0; i < NumOfJData_menu; i++) {
                    m1 = JData_menu.result[i]["Food_Name"];
                    m2 = JData_menu.result[i]["Cost"];
                    m3 = JData_menu.result[i]["Description"];
                    m4 = JData_menu.result[i]["Image"];

                    var abc = 0;
                    if (m4 == null) {
                        foodimg = "images/Logo.jpg";
    
                    }
                    else {
                        foodimg = m4;
                    }
                    

                    if (i % 3 == 0 && abc % 3 == 0) {
                      
                        showmenutemp = showmenutemp + '<div class="row no-collapse-1">';


                        showmenutemp = showmenutemp + '<section class="4u">' +
                            '<a href="#" class="image featured">' +
                            '<img src="'+foodimg+'" alt="">' +
                            '</a>' +
                            '<div class="box">' +
                            '<p>' + m1 + "<br>" + m2 + '</p>' +
                            '<a href="#" class="button"  >加入購物車</a>' +
                            '</div>' +
                            '</section>';
                    }
                    else {
                       
                        showmenutemp = showmenutemp + '<section class="4u">' +
                        '<a href="#" class="image featured">' +
                        '<img src="'+foodimg+'" alt="">' +
                        '</a>' +
                        '<div class="box">' +
                        '<p>' + m1 + "<br>" + m2 + '</p>' +
                        '<a href="#" class="button"  >加入購物車</a>' +
                        '</div>' +
                        '</section>';
                    }
                    abc++;
                    if (i % 3 == 2 || i == NumOfJData_menu - 1) { showmenutemp = showmenutemp + '</div>'; }
                }
                //JData_menu = JSON.parse(stringJData_menu);
                //push test
                document.getElementById("extra").innerHTML = showmenutemp;

            });


        },

        error: function () {
            alert("無法取得餐廳餐點資訊，請重新整理");
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
