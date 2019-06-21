var cart_inner;
var _sent_bill=0;


function _additemtocart(foodname, cost, id, restaurant, restaurantadress) {
    var getcountid = "buy_count" + id;
    var count = document.getElementById(getcountid).value;
    if (_sent_bill == 0) {
        _sent_bill = [
            {
                "Rest_Name": restaurant,
                "Rest_Address": restaurantadress,
                "Food_Id": id,
                "Food_Name": foodname,
                "Cost": cost,
                "Count": count
            }
        ]
    } else {
        var pp = {
            "Rest_Name": restaurant,
            "Rest_Address": restaurantadress,
            "Food_Id": id,
            "Food_Name": foodname,
            "Cost": cost,
            "Count": count
        }
        var billlengh = _sent_bill.length;
        var k=0;

        for(var i=0;i<billlengh;i++)
        {
            if (pp["Food_Id"] == _sent_bill[i]["Food_Id"]) {
                _sent_bill[i]["Count"]+=pp["Count"]

                k=1;

            }
        }
        if(k==0){
            _sent_bill.push(pp);
        }
     
    }

}

function _additemtobill(cart) {

}



function _sentbill() {
    var _sent_customer_name=document.getElementById("NaMe").value;
    var _sent_address=document.getElementById("address").value;
    var _sent_other=document.getElementById("others").value;
    var _sent_phonenumber=document.getElementById("cellphone").value;

    var _sent_Total=function(){
        
        var p=0;
        for(var i=0;i<_sent_bill.length;i++)
        {
            p+=(_sent_bill[i]["Count"]*_sent_bill[i]["Cost"];
        }
        
        
        
        
        return p;}

   var  _sent_biller =
        {
            "Customer": {
                "User_Id": 1,
                "User_Name": _sent_customer_name,
                "Address": _sent_address,
                "Other": _sent_other,
                "Phone_Number": _sent_phonenumber
            },
            "Order":
            {
                "Order_Id": getorderid(),
                "Total": _sent_Total,
                "Type_Count": (_sent_bill.length)+1,
                "Meals": _sent_bill,

                "Start_Time": sent_time = new Date().Format("yyyy-MM-dd hh:mm:ss"),
                "Order_Status": "WAIT",

                "CastingPrio": 0
            },

        }
        
            $.ajax({
                url: "/SendOrderServlet",
                type: "POST",
                async: true,
                dataType: "json",
                contentType: 'application/json; charset=UTF-8',
                data: _sent_biller,
                success: function (JData_menu) {alert("送出訂單成功");}
            });
        
        _sent_bill=null;
        switchpage("rest");
}


function _billpage()
{
    switchpage("bill");

}
function set_sent_meals() {

}

var _sent_bill=0;
function opcartpage() {
    switchpage("cart");
    _sent_bill={
        "Customer": {
           "User_Id": 1,
           "User_Name": "你好",
           "Address": "我家",
           "Other": "gufjdk",
           "Phone_Number": "02222"
        },
        "Order": {
           "Order_Id": 253678911,
           "Total": 75,
           "Type_Count": 2,
           "Meals": [{
                 "Rest_Name": "阿MAN 早午餐",
                 "Rest_Address": "基隆市中正區中正路822號1樓",
                 "Food_Id": 3,
                 "Food_Name": "麥克雞塊",
                 "Cost": 25,
                 "Count": 2
              },
              {
                 "Rest_Name": "阿MAN 早午餐",
                 "Rest_Address": "基隆市中正區中正路822號1樓",
                 "Food_Id": 4,
                 "Food_Name": "蘿蔔糕",
                 "Cost": 25,
                 "Count": 1
              }
           ],
           "Start_Time": "2019-05-17 03:31:00",
           "Order_Status": "WAIT",
           "CastingPrio": 0
        }
     }
    if(_sent_bill!=null)
    {
      
        var insi="";
        for(var i=0;i<_sent_bill.length;i++)
        {
            insi=insi+'<div class="box" style="display:none">';
            insi=insi+"<p>餐廳名稱:"+sent_bill[i][Rest_Name]+'</p>';
            insi=insi+"<p>品項:"+sent_bill[i][foodname]+'</p>';
            insi=insi+"<p>單價:"+sent_bill[i][Cost]+'</p>';
            insi=insi+"<p>數目:"+sent_bill[i][Count]+'</p>';
            insi=insi+"<a herf='function:_deleteitem("+sent_bill[i][Food_Id]+")'>刪除</a>"
            insi=insi+"</div>";
        }
        var pageer='<section class="12u">'+'<a href="javascript:add_alltosentcart()" class="button">送出訂單</a><br><br><br><br>'+
        '<a href="javascript:_ShowCartPage()" class="button">繼續瀏覽</a></section>';
        
        
        
        
        document.getElementById("cart").innerHTML;
       
    }

}


function _deleteitem(id)
{
    
}