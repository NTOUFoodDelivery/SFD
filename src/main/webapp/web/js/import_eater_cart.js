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
sent_meals = null;
function addtosentmeals(foodid, foodname, foodcount) {
    sent_meals.push(meal(foodid, foodname, foodcount));
}

function meal(foodid, foodname, foodcount) {

    return Foosent =
        {
            Food_Id: foodid,
            Food_Name: foodname,
            Count: foodcount
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
//單純地將購物車的值顯示到購物車頁面
//預計此頁面的功能有: 顯示目前加入"購物車"的"品項"和"數目"和"單項總額" 總價格 刪除單項 
//                 : 將目前的"購物車"值轉交到"送出訂單頁面" 
function ChangeToCartPage() {
    alert("switch_cartpage");
    // // if (sent_meals != []&&sent_meals != null) {
    //      switch_cartpage='<div class="container">';
    // //     var NumOfCartdata = sent_meals.length;
    // //     for (var i = 0; i < NumOfCartdata; i++) 
    // //     {
    // //         var cartfoodid=sent_meals[i]["Food_Id"];
    // //         var cartfoodname= sent_meals[i]["Food_Name"];
    // //         var cartfoodcount= sent_meals[i]["Count"];
    // //         switch_cartpage=switch_cartpage+'<div class="row no-collapse-1">'+
    // //         '<section class="9u">'+
    // //         '<div class="box">'+
    // //         '</div>'+'</section>'+'</div>';
            
            
            
            

    // //     }
    // // }else{
    //     //todo
    //     switch_cartpage=switch_cartpage+'<section class="4u">'+
    //     '<div class="box">' +
    //     '<p>' +'現在購物車裡沒有東西歐'+'</p>' +
    //     '<a href="#" class="button"  >確定</a>' +
    //     '</div>' +
    //     '</section>'+'</div>' ;
        
    // //}
    // document.getElementById("extra").innerHTML = switch_cartpage;
}

function clearcart()
{
    sent_meals=null;
}

