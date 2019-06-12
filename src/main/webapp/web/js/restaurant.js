
$(document).ready(function(){
	const url = "/ShowRestInfoServlet"; // test url
	// const url = "/ShowRestInfoServlet"; // 正式 url
        $.ajax({
            type: "GET",
            url: url,
            dataType: "json",
            success: function(data)
			{
				console.log(data);
				for(var i = 0;i< data.length;i++){
					var rest = data[i];
					var value = rest.Rest_Id;
					var name = rest.Rest_Name;
					$('#restaurant_select').append('<option value ='+value+'>'+name+'</option>');
					$('#restaurant_select1').append('<option value ='+value+'>'+name+'</option>');
				
					
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
	const url = "/SFD/ShowRestInfoServlet"; // test url
	// const url = "/ShowRestInfoServlet"; // 正式 url
        $.ajax({
            type: "GET",
            url: url,
            dataType: "json",
            success: function(data) {
				for(var i = 0;i< data.length;i++){
					var rest = data[i];
					var value = rest.Rest_Id;
					var name = rest.Rest_Name;
					var address = rest.Rest_Address;
					$('#restaurant_select3').append('<option value ='+value+'>'+name+'</option>');
				
					
				}
				$('#restaurant_select3').on('change', function() {
						const valueSelected = this.value;
						getMenu(valueSelected);
				});
            },
            error: function () {

            }
        })
	
})

function getMenu(Rest_Id){
	$('#menu_select').empty();
	$('#menu_select1').empty();
	const url = "/SFD/ShowMenuServlet?restID="+Rest_Id; // test url
	// const url = "/ShowMenuServlet"; // 正式 url
        $.ajax({
            type: "GET",
            url: url,
            dataType: "json",
			// data:restReq,
            success: function(data) {
				for(var i = 0;i< data.length;i++){
					var meal = data[i];
					var value = meal.Food_Id;
					var name = meal.Food_Name;
					$('#menu_select').append('<option value ='+value+'>'+name+'</option>');
					$('#menu_select1').append('<option value ='+value+'>'+name+'</option>');
				}
            },
            error: function () {

            }
        })

}