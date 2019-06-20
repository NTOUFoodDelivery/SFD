var cart_inner;
var _sent_bill;


function _additemtocart(foodname, cost, id, restaurant, restaurantadress) {
    var getcountid = "buy_count" + id;
    var count = document.getElementById(getcountid).value;
    if (_sent_bill == null) {
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

function _cartpage() {
    switchpage("cart");
    if(_sent_bill==null)
    {
        <div class="box" style="display:none"><p></p><a>刪除項目</a></div>
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


function _billpage()
{
    switchpage("bill");

}
function set_sent_meals() {

}

function _deleteitem(id)
{
    
}