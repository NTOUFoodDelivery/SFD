// html裡面加
// <!--reconnect websocket-->
// <script src="assets/js/reconnectingWebSocket/reconnecting-websocket.min.js"></script>



$(document).ready(function () {
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
                    // 客製化-----BEGIN
                    var img = 'web/images/Logo.jpg';
                    var text = 'Test SFD';
                    var notification = new Notification('Hello', { body: text, icon: img });
                    setTimeout(notification.close.bind(notification), 4000);
					var deliver_order=JSON.parse(event.data);
					OrderIsComing(deliver_order);
					//alert(deliver_order	);
					//TransferDeliverOrder(event);
                    // 客製化-----END
                    // var n = new Notification("Hi! ", {tag: 'soManyNotification'});
					
                    console.log(event);
					
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
    // let client = new WebSocketClient("ws", "localhost", 8080, "/SFD/pushOrderEndpoint"); // 測試
// let client = new WebSocketClient("wss", "ntou-sfd.herokuapp.com","", "/pushOrderEndpoint"); // 正式
    let client = new WebSocketClient("wss", "ntou-sfd.herokuapp.com", "", "/testWebsocket"); // 測試
    client.connect();

    window.onbeforeunload = function() {
        client.close();
    };
})


