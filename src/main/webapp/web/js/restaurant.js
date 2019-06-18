
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
	const url = "/ShowRestInfoServlet"; // test url
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
					$('#restaurant_select1').append('<option value ='+value+'>'+name+'</option>');
					
				}
				$('#restaurant_select3').on('change', function() {
						var valueSelected = this.value;
						getMenu('#menu_select',valueSelected);
				});
				$('#restaurant_select1').on('change', function() {
					var valueSelected = this.value;
					getMenu('#menu_fix_select',valueSelected);
				});
            },
            error: function () {

            }
        })
	
});



function getMenu(where,Rest_Id){
	$(where).empty();
	const url = "/ShowMenuServlet?restID="+Rest_Id; // test url
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
					$(where).append('<option value ='+value+'>'+name+'</option>');
				}
            },
            error: function () {

            }
        })

}