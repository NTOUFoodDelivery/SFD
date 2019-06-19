function upload(cmd,where) // upload img and return img_url
{
    console.log("IN")
    const id = '1573e8c523b6e8c'; // 填入 App 的 Client ID
    const token = '2288b6ecd6c0eb7545b4c083c9e0f212eb6ab9ed'; // 填入 token
    const album = 'vNnSeO4'; // 若要指定傳到某個相簿，就填入相簿的 ID

    var fileTarget = "#"+cmd+where+"File";
    var file_data = $(fileTarget).prop('files')[0]; //取得上傳檔案屬性

    let settings = {
        crossDomain: true,
        processData: false,
        contentType: false,
        type: 'POST',
        url: 'https://api.imgur.com/3/image',
        headers: {
            Authorization: 'Bearer ' + token
        },
        mimeType: 'multipart/form-data'
    };

    var nameTarget = "#"+cmd+where+"Name";
    var descriptionTarget = "#"+cmd+where+"Description";

    var title = $(nameTarget)[0].value;
    var description = $(descriptionTarget)[0].value;

    let form = new FormData();
    form.append('image', file_data);
    form.append('title', title);
    form.append('description', description);
    form.append('album', album); // 有要指定的相簿就加這行

    settings.data = form;

    $.ajax(settings).done(function(res) {
        console.log("ININ")
        // console.log(res); // 可以看見上傳成功後回的值
        var img_url = JSON.parse(res).data.link;
        console.log(img_url); // ------------------------- 圖片網址
        var imgUrlTarget = "#"+cmd+where+"ImgUrl";
        $(imgUrlTarget)[0].value = img_url;
        alert('上傳完成，稍待一會兒就可以在底部的列表上看見了。')
    });

}

function import_menu(n1, n2) {
    var JData_menu;
    var sJson = JSON.stringify
    ({
        Rest_Name: n1, Rest_Address: n2
    });
    showmenutemp = "";
    const url = "/ShowMenuServlet"; // test url
    // const url = "/ShowMenuServlet"; // 正式 url
    $.ajax({
        url: url,
        type: "POST",
        async: true,
        dataType: "json",
        contentType: 'application/json; charset=UTF-8',
        data: sJson,
        success: function (JData_menu) {
            $.each(JData_menu, function () {
                var NumOfJData_menu = JData_menu.result.length;
                var stringJData_menu = JSON.stringify(JData_menu);
                var m1, m2, m3, m4;
                var foodimg;
                var addtocart;
                $("body").append("<tr>" + stringJData_menu + "</tr>");
                for (var i = 0; i < NumOfJData_menu; i++) {
                    m1 = JData_menu.result[i]["Food_Name"];
                    m2 = JData_menu.result[i]["Cost"];
                    m3 = JData_menu.result[i]["Description"];
                    m4 = JData_menu.result[i]["Image"];
                    m5 = JData_menu.result[i]["Food_Id"];
                }
            })
        }
    })
}