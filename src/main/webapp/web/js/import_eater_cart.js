var sent_cart;
var sent_customer_id;
var sent_time;
var sent_rest_address;
var sent_rest_name;
var sent_address;
var sent_type_count;
var sent_Total;
var sent_meals;
var sent_other;
var switch_cartpage;
sent_meals=[];
function addtosentmeals(foodid,foodname,foodcount)
{
    sent_meals.push(meal(foodid,foodname,foodcount));
}

function meal(foodid,foodname,foodcount)
{   

    return Foosent=
    {
        Food_Id:foodid,
        Food_Name:foodname,
        Count:foodcount
    };
}
function converttobill() {

}

function add_alltosentcart() {
    sent_cart =
        {
            Customer_Id: sent_customer_id,
            Deliver_Id: 0,
            Order_Id: 1557915145,
            Start_Time: sent_time,
            Rest_Address: sent_rest_address,
            Address: sent_address,
            Rest_Name: sent_rest_name,
            Type_Count: sent_type_count,
            Total: sent_Total,
            Order_Status: "WAIT",
            meals: sent_meals,
            Other: sent_other
        }
}

function ChangeToCartPage()
{
    var NumOfCartdata = sent_meals.length;
    for (var i = 0; i < NumOfJData_menu; i++) 
    {
        
    }
    document.getElementById("extra").innerHTML = switch_cartpage;
}


