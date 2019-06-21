function switchpage(input)
{
    if(input=="cart")
    {
        document.getElementById("bill").style.display='none';
        document.getElementById("cart").style.display='';
        document.getElementById("main").style.display='none';
        document.getElementById("extra").style.display='none';

    }
    else if(input=="bill")
    {
        document.getElementById("bill").style.display='';
        document.getElementById("cart").style.display='none';
        document.getElementById("main").style.display='none';
        document.getElementById("extra").style.display='none';


    }
    else if(input=="rest")
    {
        document.getElementById("bill").style.display='none';
        document.getElementById("cart").style.display='none';
        document.getElementById("main").style.display='';
        document.getElementById("extra").style.display='';


    }
    else if(input=="menu")
    {
        document.getElementById("bill").style.display='none';
        document.getElementById("cart").style.display='none';
        document.getElementById("main").style.display='';
        document.getElementById("extra").style.display='';


    }
}


function switchtocart()
{
    document.getElementById("bill").style.display='none';
    document.getElementById("main").style.display='none';
    document.getElementById("extra").style.display='none';
    document.getElementById("cart").style.display='';

}

function switchtobill()
{
    document.getElementById("bill").style.display='';
    document.getElementById("main").style.display='none';
    document.getElementById("extra").style.display='none';
    document.getElementById("cart").style.display='none';

}



function seeeentbill()
{
    const dateTime = new Date().getTime();
    const timestamp = Math.floor(dateTime / 1000);
    var json = {
      "Customer": {
         "User_Id": 1,
         "User_Name": "你好",
         "Account":"sadsd",
         "Address": "我家",
         "Other": "gufjdk",
         "Phone_Number": "02222"
      },
      "Order": {
         "Order_Id": timestamp,
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
         "Start_Time": "2019-05-19 03:31:00",
         "Order_Status": "WAIT",
         "CastingPrio": 0
      }
    };
    $.ajax({
      url: "/SendOrderServlet",
      type: "POST",
      dataType: "json",
      data:JSON.stringify(json),
      success: function (data) {console.log(data)}
    })

switchpage("rest");
}