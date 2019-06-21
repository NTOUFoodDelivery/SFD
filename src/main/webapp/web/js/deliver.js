function deliverOffline(deliver_status) {
  if (deliver_now_order == "") {
    var deliver_temp = "";

    if (deliver_status == 0) {
      deliver_temp = deliver_temp
          + '<img src="images/offline.svg" style="z-index:950;position:absolute;width:100%;" />';
      deliver_status = 1;
      $.ajax({
        url: "/SwitchStatusServlet?userStatus=DELIVER_OFF",
        type: "POST",
        async: true,
        dataType: "json",
        contentType: 'application/json; charset=UTF-8',
        data: null,
        success:

            function (JData_menu) {

              alert("switch to OFF");
            }
      });
    } else {
      deliver_temp = "";
      deliver_status = 0;
      $.ajax({
        url: "/SwitchStatusServlet?userStatus=DELIVER_ON",
        type: "POST",
        async: true,
        dataType: "json",
        contentType: 'application/json; charset=UTF-8',
        data: null,
        success:

            function (JData_menu) {

              alert("switch to ON");
            }
      });
    }
    document.getElementById("offlineImg").innerHTML = deliver_temp;
    //document.getElementById("content").innerHTML = deliver_temp;

  } else {
    alert("訂單尚未完成");
  }
  return deliver_status;
}

function generatResult(Deliver_Id, accept) {
  return {
    "Deliver_Id": Deliver_Id,
    "accept": accept
  };
}
//var rest_address= new Array();
//var rest_ID= new Array();
function OrderIsComing(deliver_order) {
	//alert(deliver_order);
  console.log(deliver_order)
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
			rest_address.push(deliver_order["Order"]["Meals"][i]["Rest_Address"]);
			rest_ID.push(deliver_order["Order"]["Meals"][i]["Rest_Name"]);
		}
	}

  //alert(rest_ID);
  var txt = '目的地:' + m3 + '\n代付額:' + m4
      + '元\n外送費:50元\n訂單明細:\n';
	//var selectRest="";
	for(var j=0;j<rest_ID.length;j++)
	{
		txt=txt+"\n餐廳:" + rest_ID[j] + '\n餐廳地址:' + rest_address[j] +'\n'; 
		for (var i = 0; i < m6; i++) {
		
			if(deliver_order["Order"]["Meals"][i]["Rest_Name"]==rest_ID[j]){

				txt = txt + deliver_order["Order"]["Meals"][i]["Food_Name"] + ':'
				+ deliver_order["Order"]["Meals"][i]["Count"] + "個\n";
				}
		}
		//selectRest=selectRest+'<option value="'+rest_address[j]+'">餐廳'+(j+1)+'</option>';
		//document.getElementById("restSelect").innerHTML = selectRest;
	}
	
  //import_Order_menu(m1,m2,txt);
  //initialPlace = new google.maps.LatLng(25.150892, 121.772461);
  var geocoder = new google.maps.Geocoder();
  geocoder.geocode({'address': m2}, function (results, status) {
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
  deliver_now_order = txt+"\n備註:"+deliver_order["Customer"]["Other"];
  customer_Inf="食客名稱:"+deliver_order["Customer"]["User_Name"]+"\n食客電話:"+deliver_order["Customer"]["Phone_Number"]+"\n";
}
function mark_rest()
{
	DeleteMarkers();
	rest_address_for_map=document.getElementById("restSelect").value;
	var geocoder = new google.maps.Geocoder();
	
  geocoder.geocode({'address': document.getElementById("restSelect").value}, function (results, status) {
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
}

function DeleteMarkers() {
  //Loop through all the markers and remove
  for (var i = 0; i < markers.length; i++) {
    markers[i].setMap(null);
  }
  markers = [];
};
var Deliver_order_ID;

function post_Order_status() {
	//window.open ('index_eater.html') ;
	window.open ('index_eater.html','newwindow','height=800,width=800,toolbar=yes,menubar=yes,scrollbars=no,resizable=yes,location=no,status=no'); 
  var rest_inner;
  $.ajax({
    url: "/ShowCurrentOrderServlet",
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
        restimg = "images/Logo.jpg";
        rest_inner = rest_inner + '<div class="row no-collapse-1">';
        rest_inner = rest_inner + '<section class="4u">' +
            '<a href="#" class="image featured">' +
            '<img src="' + restimg + '" alt="">' +
            '</a>' +
            '<div class="box">' + '<br>顧客名稱:' + JData.result[0]["User_Name"]
            + '<br>餐廳:' + JData.result[0]["Rest_Name"] + "<br>";
        for (var i = 0; i < NumOfJData; i++) {
          var abc = 0;
          a1 = "'" + JData.result[i]["Rest_Name"] + "'";
          //a2 = "'" + JData.result[i]["Rest_Address"] + "'";
          rest_inner = rest_inner +
              '<p>' + JData.result[i]["Food_Name"] + '<br>數量:'
              + JData.result[i]["Count"] + '</p>'
          '<a href="#" class="button" onclick="import_menu(' + a1 + ',' + a2
          + ')" >Read More</a>' +
          '</div>' +
          '</section>';

          abc++;
          if (i % 3 == 2 || i == NumOfJData - 1) {
            rest_inner = rest_inner + '</div>';
          }
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

function deliver_check_now_order() {
  if (deliver_now_order == "") {
    alert("你現在沒有訂單喔<3")
  } else {
    alert(customer_Inf+deliver_now_order);
  }
}

function deliver_finish_order() {
  if (deliver_now_order != "") {
    if (confirm('完成以下訂單?\n' + customer_Inf + deliver_now_order + '\n')) {

      deliver_now_order = "";
      //client.send(generatResult(a,true));
      var temp123;
      temp123 = "/SendOrderServlet?orderID=" + Deliver_order_ID;

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
      alert("訂單尚未完成，同志尚須努力");
      //client.send(generatResult(a,false));
    }
  } else {
    alert("你現在沒有訂單喔<3");
  }
}

function SwitchStatusToCu() {
  $.ajax({
    url: "/SwitchTypeServlet?userNow=Customer",
    type: "POST",
    async: true,
    dataType: "json",
    contentType: 'application/json; charset=UTF-8',
    data: null,
    success:

        function (JData_menu) {

          window.location = "./index_eater.html";

          //alert("switch to deliver");
        }
  });
}

function generatResult(Order_Inf,yesOrNot)
{
	Order_Inf=
	{
		"orderID" : Deliver_order_ID,
		"deliverID":"",
		"accept":yesOrNot
	};
}

function logoutConfirm()
{
	if(deliver_now_order=="")
	{
		Member_logout();
	}
	else
	{
		alert("訂單還沒完成就想下線阿?");
	}
}