
$(document).ready(function(){
	 var url="https://ntou-sfd.herokuapp.com/ShowRestInfoServlet";
        $.ajax({
            type: "GET",
            url: url,
            dataType: "json",
            success: function(data) {
				var result = data.result;
                console.log(result);
				for(var i = 0;i< result.length;i++){
					var rest = result[i];
					var value = rest.Rest_Id;
					var name = rest.Rest_Name;
					var address = rest.Rest_Address;
					$('#restaurant_select').append('<option value ='+address+'>'+name+'</option>');
					$('#restaurant_select1').append('<option value ='+address+'>'+name+'</option>');
				
					
				}
				$('#restaurant_select3').on('change', function() {
						var optionSelected = $("option:selected", this);
						var valueSelected = this.value;
						console.log(valueSelected);
						console.log(optionSelected[0].innerHTML);
				});
            },
            error: function () {

            }
        })
	
})

$(document).ready(function(){
	 var url="https://ntou-sfd.herokuapp.com/ShowRestInfoServlet";
        $.ajax({
            type: "GET",
            url: url,
            dataType: "json",
            success: function(data) {
				var result = data.result;
                console.log(result);
				for(var i = 0;i< result.length;i++){
					var rest = result[i];
					var value = rest.Rest_Id;
					var name = rest.Rest_Name;
					var address = rest.Rest_Address;
					$('#restaurant_select3').append('<option value ='+address+'>'+name+'</option>');
				
					
				}
				$('#restaurant_select3').on('change', function() {
						var optionSelected = $("option:selected", this);
						var valueSelected = this.value;
						console.log(valueSelected);
						console.log(optionSelected[0].innerHTML);
						getMenu(optionSelected[0].innerHTML,valueSelected);
				});
            },
            error: function () {

            }
        })
	
})

function getMenu(Rest_Name,Rest_Address){
	$('#menu_select').empty();
	console.log("asdasd")
	var restReq= `{
		"Rest_Name":${Rest_Name},
		"Rest_Address":${Rest_Address}
	}`; 
	 var url="https://ntou-sfd.herokuapp.com/ShowMenuServlet";
        $.ajax({
            type: "POST",
            url: url,
            dataType: "json",
			data:restReq,
            success: function(data) {
				var result = data.result;
                console.log(result);
				
				for(var i = 0;i< result.length;i++){
					var meal = result[i];
					var value = meal.Food_Id;
					var name = meal.Food_Name;
					$('#menu_select').append('<option value ='+value+'>'+name+'</option>');
				}
            },
            error: function () {

            }
        })

}