
$.ajax({
    url: "https://ntou-sfd.herokuapp.com/ShowRestInfoServlet",
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

            var temp=document.getElementById("extra").value;
            var a1,a2;
            temp=temp+'<div class="container">';
            for (var i = 0; i < NumOfJData; i++) {
                var abc=0;
                a1="'"+JData.result[i]["Rest_Name"]+"'";
                a2= "'"+JData.result[i]["Rest_Address"]+"'";
                if(i%3==0&&abc%3==0)
                {temp=temp+'<div class="row no-collapse-1">';
                
               
                temp=temp+'<section class="4u">'+
                '<a href="#" class="image featured">'+
                '<img src="images/pic01.jpg" alt="">'+
                '</a>'+
                '<div class="box">'+
                '<p>'+JData.result[i]["Rest_Name"]+"<br>"+JData.result[i]["Rest_Address"]+'</p>'+
                '<a href="#" class="button" onclick="import_menu('+a1+','+a2+')" >Read More</a>'+
                 '</div>'+
                '</section>';
            }
                else{temp=temp+'<section class="4u">'+
                '<a href="#" class="image featured">'+
                '<img src="images/pic01.jpg" alt="">'+
                '</a>'+
                '<div class="box">'+
                '<p>'+JData.result[i]["Rest_Name"]+"<br>"+JData.result[i]["Rest_Address"]+'</p>'+
                '<a href="#" class="button" onclick="import_menu('+a1+','+a2+')" >Read More</a>'+
                 '</div>'+
                '</section>';}
                abc++;
                    if(i%3==2||i==NumOfJData-1){temp=temp+'</div>';}
            }
            //JData = JSON.parse(stringJData);
            temp=temp+"</div>";
            document.getElementById("extra").innerHTML=temp;


        });
    },

    error: function () {
        alert("import restaurant ERROR!!!");
    }
});

