var sent_cart;
var sent_customer_id = 0;
var sent_time;
var sent_rest_address;
var sent_rest_name;
var sent_address;
var sent_type_count;
var sent_Total;
var sent_meals;
var sent_other;
var switch_cartpage;
var watchres;
var switch_billpage;

sent_meals =
    [{
        "Food_Id": 31415926535,
        "Food_Name": "foodname",
        "Count": 0,
        "FPrice": 0
    }];
var fime = sent_meals;
function addtosentmeals(foodid, foodname, foodcount, foodprice) {
    var k = 0;
    var NumOfCartdata = sent_meals.length;
    if (sent_meals[1] != null) {
        for (var i = 0; i < NumOfCartdata; i++) {
            if (foodid == sent_meals[i]["Food_Id"]) {
                sent_meals[i]["Count"] = Number(sent_meals[i]["Count"]) + Number(foodcount);
                sent_meals[i]["FPrice"] = Number(sent_meals[i]["FPrice"]) + Number(foodprice);
                k = 1;
            }
        }
    }
    if (k == 0) {
        sent_meals.push(meal(foodid, foodname, foodcount, foodprice));
    }
}

function meal(foodid, foodname, foodcount, foodprice) {
    foodname = '"' + foodname + '"';
    //alert(foodid+foodname+foodcount+" "+foodprice);
    return Foosent =
        {
            Food_Id: foodid,
            Food_Name: foodname,
            Count: foodcount,
            FPrice: foodprice * foodcount,
        };
}
function converttobill() {
    var total = 0;
    var NumOfbilldata = sent_meals.length;
    switch_billpage=null;
    for (var i = 1; i < NumOfbilldata; i++) {
        var cartfoodid = sent_meals[i]["Food_Id"];
        var cartfoodname = sent_meals[i]["Food_Name"];
        var cartfoodcount = sent_meals[i]["Count"];
        var cartfoodprice = sent_meals[i]["FPrice"];
        
        switch_billpage = switch_billpage + '<div class="row no-collapse-1">' +
            '<section class="12u">' +
            '<div class="box"><a>' + '<h1>品項:' + cartfoodname + '數量: ' + cartfoodcount + '總額: ' + cartfoodprice +
            '</h1>' + '</a>';
        total = Number(total) + Number(cartfoodprice);
        if (i == NumOfbilldata - 1) {
            switch_billpage = switch_billpage + '</div>' + '</section>' + '</div>' +
                '<section class="12u">' + '<h1 style="color=white">總金額 :' + total + '<div class="box">'+'</h1>' +
                '<h1>地址:<input type="text" id="address" value=""></h1>'+
                '<h1>手機:<input type="text" id="cellphone" value=""></h1>'+
                '<h1>姓名:<input type="text" id="NaME" value=""></h1>'+
                '<h1>備註:<input type="text" id="others" value=""></h1>'+
                '<a href="' + 'javascript:add_alltosentcart()' + '" class="button"  >送出訂單</a><br>' +
                '<a href="' + 'javascript:ShowCartPage()' + '" class="button"  >返回購物車</a>' +
                '</div>' +
                '</section>' + '</div>';
        }
        document.getElementById("extra").innerHTML = switch_billpage;
        sent_Total=total;

       
    }



    // add_alltosentcart();
}

function add_alltosentcart() {
    delete sent_meals[0];
    sent_type_count=Number(sent_meals.length);
    

    sent_cart =
        {
            Customer_Id: sent_customer_id,
            Deliver_Id: 0,
            Order_Id: getorderid(),
            Start_Time: sent_time = new Date().Format("yyyy-MM-dd hh:mm:ss"),
            Rest_Address: sent_rest_address,
            Address: sent_address,
            Rest_Name: sent_rest_name,
            Type_Count: sent_type_count,
            Total: sent_Total,
            Order_Status: "WAIT",
            meals: sent_meals,
            Other: sent_other
        }
        senterPOST(sent_cart);
        sent_meals = fime ;
        changtorest();

}
//單純地將購物車的值顯示到購物車頁面
//預計此頁面的功能有: 顯示目前加入"購物車"的"品項"和"數目"和"單項總額" 總價格 刪除單項 
//                 : 將目前的"購物車"值轉交到"送出訂單頁面" 
function ChangeToCartPage() {
    //alert("switch_cartpage");
    if (sent_meals != [] && sent_meals[1] != null) {
        switch_cartpage = '<div class="container">';
        var NumOfCartdata = sent_meals.length;
        for (var i = 1; i < NumOfCartdata; i++) {
            var cartfoodid = sent_meals[i]["Food_Id"];
            var cartfoodname = sent_meals[i]["Food_Name"];
            var cartfoodcount = sent_meals[i]["Count"];
            var cartfoodprice = sent_meals[i]["FPrice"];
            //cartfoodname="'"+cartfoodname+"'";
            switch_cartpage = switch_cartpage + '<div class="row no-collapse-1">' +
                '<section class="12u">' +
                '<div class="box">' + '<h1>品項:' + cartfoodname + '數量: ' + cartfoodcount + '總額: ' + cartfoodprice +
                '</h1>' + '<a href="" onclick="deletefromcart(' + cartfoodname + ')">刪除</a>' + '</div>' + '</section>' + '</div>';
                //'</h1>' + '<a href="javascript:deletefromcart(' + cartfoodname + ')">刪除</a>' + '</div>' + '</section>' + '</div>'
                //onclick="import_menu(' + a1 + ',' + a2 + ')"
            if (i == NumOfCartdata - 1) {
                switch_cartpage = switch_cartpage + '<section class="12u">' +
                    '<div class="box">' +
                    '<a href="' + 'javascript:converttobill()' + '" class="button"  >送出訂單</a>' +
                    '<a href="' + 'javascript:changetomenu()' + '" class="button"  >繼續購物</a>' +
                    '</div>' +
                    '</section>' + '</div>';
            }




        }
    } else {
        // todo
        switch_cartpage = "";
        switch_cartpage = switch_cartpage + '<section class="12u">' +
            '<div class="box">' +
            '<p>' + '現在購物車裡沒有東西歐<3' + '</p>' +
            '<a href="' + 'javascript:changtorest()' + '" class="button"  >確定</a>' +
            '</div>' +
            '</section>' + '</div>';

    }
    document.getElementById("extra").innerHTML = switch_cartpage;
}

function clearcart() {
    sent_meals = null;
}

function additemwithoutcount(aitem, bitem, citem) {
    var tempid, tempname, tempcount;
    tempprice = aitem;
    tempname = bitem;
    tempcount = document.getElementById("buy_count" + citem).value;
    //alert("count:" + tempcount);
    addtosentmeals(citem, tempname, tempcount, tempprice);
}

function twoDigits(d) {
    if (0 <= d && d < 10) return "0" + d.toString();
    if (-10 < d && d < 0) return "-0" + (-1 * d).toString();
    return d.toString();
}


Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}
function deletefromcart(imd) {
    var k=imd;
}
function getorderid() {
    const dateTime = new Date().getTime();
    const timestamp = Math.floor(dateTime / 1000);
    return timestamp;
}

function showtobill() {

}

function ShowCartPage() {
    document.getElementById("extra").innerHTML = switch_cartpage;
}


function senterPOST(sentdata)
{
    $.ajax({
        url: "https://ntou-sfd.herokuapp.com/SendOrderServlet",
        type: "POST",
        async: true,
        dataType: "json",
        contentType: 'application/json; charset=UTF-8',
        data: sentdata,
        success: function (JData_menu) {alert("送出訂單成功");}
    });
}