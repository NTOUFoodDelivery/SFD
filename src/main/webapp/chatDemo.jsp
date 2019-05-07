<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<html>
<head>
    <title>SFD TEST</title>

    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel='stylesheet' href='https://use.fontawesome.com/releases/v5.7.0/css/all.css' integrity='sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ' crossorigin='anonymous'>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <!--reconnect websocket-->
    <script src="assets/js/reconnectingWebSocket/reconnecting-websocket.min.js"></script>

</head>
<body>
<h2>Hello World!</h2>
your name??<input id="nameInput" type="text" />

<section id="mySection">
    <!-- ---------   chatBox   ---------- -->


        <div class="card border-warning mb-3">
            <div class="card-header text-white bg-warning" style="height: 43px;">CAHT</div>
            <div class="card-body" id="showDialogue" style="height: 247px; overflow-y:scroll;"></div>
            <div class="card-footer border-top" style="height: 90px;">
                <div class="input-group">
                    <input type="text" id="textInput" class="form-control" style="width:180px; margin-bottom:5px">
                    <div style="width:100%; text-align:center">

                        <button id="submit">submit</button>
                    </div>
                </div>
            </div>
        </div>

    <!-- ---------   chatBox   ---------- -->
</section>
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
                this.webSocket.onopen = function(event) {
                    console.log("onopen::" + JSON.stringify(event, null, 4));
                };

                // 接收Websocket Server傳來的資料
                this.webSocket.onmessage = function(event) {
                    var msg = event.data;
                    var name = msg.split("&says&")[0];
                    var text = msg.split("&says&")[1];
                    var showDialogue = $("#showDialogue");
                    showDialogue.append('<div class="card text-dark bg-light m-2" style="margin-bottom:10px;line-height:20px;padding:3px;float:left;max-width:70%;text-align:left">'+name+'：<br/>&ensp;' + text + '</div><div style="clear:both"></div>');
                    showDialogue[0].scrollTop = showDialogue[0].scrollHeight;
                }
                this.webSocket.onclose = function(event) {
                    console.log("onclose::" + JSON.stringify(event, null, 4));
                };
                this.webSocket.onerror = function(event) {
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
    let client = new WebSocketClient("ws", "127.0.0.1", 8080, "/SFD/pushOrderEndpoint"); // 測試
    // let client = new WebSocketClient("wss", "ntou-sfd.herokuapp.com","", "/pushOrderEndpoint"); // 正式
    client.connect();

    document.getElementById('submit').addEventListener("click", function () {
        let name =document.getElementById('nameInput').value;
        let text =document.getElementById('textInput').value;
        let jsonObject = {
            orderID: name+"&says&"+text,
            accept: true
        }
        var showDialogue = $("#showDialogue");
        showDialogue.append('<div class="card text-dark bg-light m-2" style="line-height:20px;padding:3px;float:right;max-width: 70%;text-align:left">'+name+'：<br/>&ensp;' + text + '</div><div style="clear:both"></div>');
        showDialogue[0].scrollTop = showDialogue[0].scrollHeight;
        client.send(JSON.stringify(jsonObject));
        document.getElementById('textInput').value="";
    })
</script>
</html>
