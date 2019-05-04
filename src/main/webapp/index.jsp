<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<html>
<head>
    <title>SFD TEST</title>

    <!--reconnect websocket-->
    <script src="assets/js/reconnectingWebSocket/reconnecting-websocket.min.js"></script>

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

                    // 接收Websocket Server傳來的資料----> 訂單json <-----------
                    this.webSocket.onmessage = function(event) {
                        // var app = event.data;
                        // var msg = JSON.parse(app)[0];
                        console.log("onmessage::" + JSON.stringify(event, null, 4));
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
    </script>
</head>
<body>
<h2>Hello World!</h2>
</body>
</html>
