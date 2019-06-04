<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<html>
<head>
    <title>SFD TEST</title>

    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel='stylesheet' href='https://use.fontawesome.com/releases/v5.7.0/css/all.css'
          integrity='sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ'
          crossorigin='anonymous'>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <!--reconnect websocket-->
    <script src="assets/js/reconnectingWebSocket/reconnecting-websocket.min.js"></script>
    <%--    <script src="https://unpkg.com/vue/dist/vue.js"></script>--%>
    <link rel="manifest" href="manifest.json">
</head>
<body>
<%
    request.setCharacterEncoding("utf-8");
%>
<%
    String account = (String) request.getSession().getAttribute("account");
    String userType = (String) request.getSession().getAttribute("userType");
    if (account != null && userType != null) {
        session.setAttribute("account", account);
        session.setAttribute("userType", userType);
%>
<h2> Welcome <%=userType%> <%=account%>
</h2>
<%
    } else {
        response.sendRedirect("LoginDemo.jsp");
    }
%>

<section id="mySection">
    <!-- ---------   chatBox   ---------- -->


    <div class="card border-warning mb-3">
        <div class="card-header text-white bg-warning" style="height: 43px;">CAHT</div>
        <div class="card-body" id="showDialogue" style="height: 247px; overflow-y:scroll;"></div>
        <div class="card-footer border-top" style="height: 90px;">
            <div class="input-group">
                <input type="text" id="textInput" class="form-control"
                       style="width:180px; margin-bottom:5px">
                <div style="width:100%; text-align:center">

                    <button id="submit">submit</button>
                </div>
            </div>
        </div>
    </div>

    <!-- ---------   chatBox   ---------- -->
</section>
<br>
<%--<button id="testSubmit">testSubmit</button><br>--%>
<%--<a href="testEater.html">網頁跳轉</a><br>--%>
<%--<img id="img" />--%>
<%--<input type="file" id = "file">--%>
<%--<input id="but" value="aa" type="button" onclick="mes()">--%>
<script type="text/javascript">
  function mes() {
    var imgs = document.getElementById("file");
    var img = document.getElementById("img");
    var reader = new FileReader();
    reader.readAsArrayBuffer(imgs.files[0]);
    reader.onload = function (e) {
      var bf = this.result;
      var blob = new Blob([bf], {type: "text/plain"});
      // console.log(blob);
      var str = URL.createObjectURL(blob);
      img.src = str;
    };

  }

</script>
<%--<script>--%>
<%--    $(document).ready(function() {--%>
<%--        let json = `--%>
<%--        {--%>
<%--            \t"restName":"Apple203",--%>
<%--            \t"restAddress":"中正路111號"--%>
<%--        }`;--%>
<%--        $.ajax({--%>
<%--            type: "POST",--%>
<%--            url: "/ShowMenuServlet",--%>
<%--            dataType: "json",--%>
<%--            data:json,--%>
<%--            success: function(data) {--%>
<%--                console.log(data);--%>
<%--                var img = document.getElementById("img");--%>
<%--                var Image = data.result[0].Image;--%>
<%--                var str = "data:image/png;base64,"+Image;--%>
<%--                img.src = str;--%>
<%--            },--%>
<%--            error: function () {--%>

<%--            }--%>
<%--        })--%>
<%--    });--%>
<%--</script>--%>
<script>
  $('#testSubmit').click(function () {
    // var url="/IdentityRedirectServlet";
    // var url="/SFD/IdentityRedirectServlet";
    // $.ajax({
    //     xhrFields: {
    //         withCredentials: true
    //     },
    //     async:false,
    //     crossDomain: true,
    //     type: "GET",
    //     url: url,
    //     dataType: "json",
    //     success: function(data) {
    //         console.log(data);
    //     },
    //     error: function () {
    //
    //     }
    // })
  })
</script>
<script type="text/javascript">
  $(function () {
    $.ajaxSetup({
      cache: false, //關掉AJAX 緩存
      async: false, //同步請求
      contentType: "application/x-www-form-urlencoded;charset=utf-8",
      complete: function (XMLHttpRequest, textStatus) {
        // 用 XMLHttpRequest 拿 header，sessionstatus，
        var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus");
        if (sessionstatus == "timeout") {
          alert("重複登入或長時間無操作～請重新登入！！");
          parent.location.href = "LoginDemo.jsp";
        }
      }
    });
  });
</script>
<%--// 選取圖片的 input--%>
<%--<label for="upload">選擇上傳圖檔</label>--%>
<%--<input id="upload" type="file" accept="image/*" @change="showFile">--%>

<%--// 圖片標題--%>
<%--<label for="title">圖片名稱</label>--%>
<%--<input class="form-control" id="title" type="text" v-model="title" @keyup.13="submit" required>--%>

<%--// 圖片描述--%>
<%--<label for="des">圖片描述</label>--%>
<%--<input class="form-control" id="des" type="text" v-model="des" @keyup.13="submit" required>--%>

<%--// 上傳按鈕--%>
<%--<button class="btn btn-primary" type="button" id="upload">上傳</button>--%>

<input type="text" id="myText"/>
<input type="file" id="myFile" accept="image/*"/>
<input data-dismiss="modal" id="upload" type="submit"/>
<script>
  $("#upload").click(function () {

    const id = '1573e8c523b6e8c'; // 填入 App 的 Client ID
    const token = '2288b6ecd6c0eb7545b4c083c9e0f212eb6ab9ed'; // 填入 token
    const album = 'vNnSeO4'; // 若要指定傳到某個相簿，就填入相簿的 ID

    var file_data = $('#myFile').prop('files')[0]; //取得上傳檔案屬性
    var text_data = $('#myText').val();

    let settings = {
      async: true,
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

    let form = new FormData();
    form.append('image', file_data);
    form.append('title', "123");
    form.append('description', text_data);
    form.append('album', album); // 有要指定的相簿就加這行

    settings.data = form;

    $.ajax(settings).done(function (res) {
      console.log(res); // 可以看見上傳成功後回的值
      console.log(JSON.parse(res).data.link); // ------------------------- 圖片網址
      alert('上傳完成，稍待一會兒就可以在底部的列表上看見了。')
    });

  })

</script>

<script>
  var options = {
    enableHighAccuracy: true,
    timeout: 5000,
    maximumAge: 0
  };

  function success(pos) {
    var crd = pos.coords;

    console.log('Your current position is:');
    console.log('Latitude : ' + crd.latitude);
    console.log('Longitude: ' + crd.longitude);
    console.log('More or less ' + crd.accuracy + ' meters.');
  };

  function error(err) {
    console.warn('ERROR(' + err.code + '): ' + err.message);
  };

  navigator.geolocation.getCurrentPosition(success, error, options);
</script>
</body>
<script>
  class WebSocketClient {

    constructor(protocol, hostname, port, endpoint) {

      this.webSocket = null;

      this.protocol = protocol;
      this.hostname = hostname;
      this.port = port;
      this.endpoint = endpoint;
    }

    getServerUrl() {
      return this.protocol + "://" + this.hostname + ":" + this.port + this.endpoint;
    }

    connect() {
      try {
        this.webSocket = new ReconnectingWebSocket(this.getServerUrl());

        //
        // Implement WebSocket event handlers!
        //
        this.webSocket.onopen = function (event) {
          console.log("onopen::" + JSON.stringify(event, null, 4));
        };

        // 接收Websocket Server傳來的資料
        this.webSocket.onmessage = function (event) {
          // 客製化-----BEGIN
          var img = 'web/images/Logo.jpg';
          var text = 'Test SFD';
          var notification = new Notification('Hello', {body: text, icon: img});
          setTimeout(notification.close.bind(notification), 4000);
          // 客製化-----END
          // var n = new Notification("Hi! ", {tag: 'soManyNotification'});

          console.log("tyghj")
          var msg = event.data;
          var name = msg.split("&says&")[0];
          var text = msg.split("&says&")[1];
          var showDialogue = $("#showDialogue");
          showDialogue.append(
              '<div class="card text-dark bg-light m-2" style="margin-bottom:10px;line-height:20px;padding:3px;float:left;max-width:70%;text-align:left">'
              + name + '：<br/>&ensp;' + text + '</div><div style="clear:both"></div>');
          showDialogue[0].scrollTop = showDialogue[0].scrollHeight;
        }
        this.webSocket.onclose = function (event) {
          console.log("onclose::" + JSON.stringify(event, null, 4));
        };
        this.webSocket.onerror = function (event) {
          console.log("onerror::" + JSON.stringify(event, null, 4));
        }

      } catch (exception) {
        console.error(exception);
      }
    }

    getStatus() {
      return this.webSocket.readyState;
    }

    send(message) { //將資料傳給Websocket Server

      if (this.webSocket.readyState == WebSocket.OPEN) {
        this.webSocket.send(message);

      } else {
        console.error("webSocket is not open. readyState=" + this.webSocket.readyState);
      }
    }

    disconnect() {
      if (this.webSocket.readyState == WebSocket.OPEN) {
        this.webSocket.close();

      } else {
        console.error("webSocket is not open. readyState=" + this.webSocket.readyState);
      }
    }
  }

  let client = new WebSocketClient("ws", "localhost", 8080, "/SFD/pushOrderEndpoint"); // 測試
  // let client = new WebSocketClient("wss", "ntou-sfd.herokuapp.com","", "/pushOrderEndpoint"); // 正式
  client.connect();

  window.onbeforeunload = function () {
    client.close();
  };

  document.getElementById('submit').addEventListener("click", function () {
    let name = document.getElementById('nameInput').value;
    let text = document.getElementById('textInput').value;
    let jsonObject = {
      orderID: name + "&says&" + text,
      accept: true
    }
    var showDialogue = $("#showDialogue");
    showDialogue.append(
        '<div class="card text-dark bg-light m-2" style="line-height:20px;padding:3px;float:right;max-width: 70%;text-align:left">'
        + name + '：<br/>&ensp;' + text + '</div><div style="clear:both"></div>');
    showDialogue[0].scrollTop = showDialogue[0].scrollHeight;
    client.send(JSON.stringify(jsonObject));
    document.getElementById('textInput').value = "";
  })
</script>
</html>
