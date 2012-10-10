<%-- 
    Document   : websockets
    Created on : 09-sep-2012, 23:16:12
    Author     : javier.solis.g
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>  
    <meta charset="utf-8" />  
    <title>WebSocket Test</title>  
    <script language="javascript" type="text/javascript">
        //var wsUri = "ws://echo.websocket.org/"; 
        var wsUri = "ws://localhost:9494"; 
        var output;  
        function init() 
        { 
            output = document.getElementById("output");
            testWebSocket();
        }  
        
        function testWebSocket() 
        { 
            websocket = new WebSocket(wsUri);
            websocket.onopen = function(evt) 
            { 
                onOpen(evt) };
            websocket.onclose = function(evt)
            { 
                onClose(evt)
            }; 
            websocket.onmessage = function(evt) {
                onMessage(evt)
            };
            websocket.onerror = function(evt) 
            { 
                onError(evt) 
            };
        }
        
        function onOpen(evt) 
        { 
            writeToScreen("CONNECTED");
            doSend("WebSocket rocks");
            doSend("WebSocket rocks2\r\n");
        }  
        
        function onClose(evt)
        { 
            writeToScreen("DISCONNECTED");
        }
        
        function onMessage(evt) 
        {
            writeToScreen('<span style="color: blue;">RESPONSE: ' + evt.data+'</span>'); websocket.close();
        }  
        
        function onError(evt) 
        { 
            writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
        }  
        
        function doSend(message) 
        {
            writeToScreen("SENT: " + message);  websocket.send(message);
        }  
        
        function writeToScreen(message) 
        { 
            var pre = document.createElement("p");
            pre.style.wordWrap = "break-word";
            pre.innerHTML = message;
            output.appendChild(pre);
        }  
        
        window.addEventListener("load", init, false);  
    </script>  
    <h2>WebSocket Test</h2>  
    <div id="output"></div>  
</html>
