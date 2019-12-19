<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getContextPath();
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Title</title>
    <script src="https://code.jquery.com/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="../static/js/index.js"></script>
    <script>
        var basePath = '<%=basePath%>';

        var socket;
        if(!window.WebSocket){
            window.WebSocket = window.MozWebSocket;
        }
        if(window.WebSocket){
            console.log("您的浏览器支持WebSocket协议！ socket="+socket);
            socket = new WebSocket("ws://localhost:8888/ws");
            console.log("socket="+socket);
            socket.onmessage = function(event){
                var ta = document.getElementById('responseText');
                ta.innerHTML = event.data+"\r\n";
                appendMessage(JSON.parse(event.data),0);
            };
            socket.onopen = function(event){
                var ta = document.getElementById('responseText');
                ta.innerHTML = "这里显示服务器推送信息"+"\r\n";

            };
            socket.onclose = function(event){
                var ta = document.getElementById('responseText');
                ta.innerHTML = "";
                ta.innerHTML  = "WebSocket 关闭"+"\r\n";
            };
        }else{
            alert("您的浏览器不支持WebSocket协议！");
        }
        function sendMessage(userId,toUserId,message){
            console.log("message:"+message)
            if(!window.WebSocket){
                console.log("window.WebSocket无效")
                return;
            }
            if(socket.readyState == WebSocket.OPEN){
                var data = {
                    "userId" : userId,
                    "to" : toUserId,
                    "message": message,
                    "type" : "SINGLE_SENDING"
                };
                console.log("发送消息message："+message)
                send(JSON.stringify(data));
                console.log("发送消息结束")
                appendMessage(data,1);
            }else{
                alert("WebSocket 连接没有建立成功！");
            }

        }
        //webSocket发送数据
        function send(data){
            socket.send(data);
        }
        function register(userId,toUserId){
            var data = {
                "userId" : userId,
                "to" : toUserId,
                "type" : "REGISTER"
            };
            send(JSON.stringify(data));
        }
        function appendMessage(data,flag){
            if(flag==1){
                $('#messageContent').append('<div style="text-align:left;padding-right:30px"><span></span>'+data.userId+'<span>&nbsp&nbsp&nbsp</span><span>'+data.message+'</span><br></div>');
            }else{
                $('#messageContent').append(' <div style="text-align:right;padding-left:30px"><span>'+data.message+'</span><span>&nbsp&nbsp&nbsp</span><span>'+data.userId+'</span><br></div>');
            }
        }
    </script>
</head>
<body >
  <h1 style="text-align:center">聊天系统后台</h1>
  <form onSubmit="return false;">
      <input type="text" id="userId" name="userId" placeholder="这里输入用户编号" /> <br />
      <input type="text" id="toUserId" name="toUserId" placeholder="这里输入接收人编号" /> <br />
      <input type="text" id="message" name="message" placeholder="这里输入消息" /> <br />
      <br /> <input type="button" value="发送 WebSocket 请求消息"
                    onClick="sendMessage(this.form.userId.value,this.form.toUserId.value,this.form.message.value)" />
      <br /> <input type="button" value="登录"
                    onClick="register(this.form.userId.value,this.form.toUserId.value)" />
      <hr color="blue" />
      <h3>服务端返回的应答消息</h3>
      <span id="responseText"></span>
      <div id="messageContent" style="border:solid black 1px;width:600px;height:300px">

      </div>
  </form>
</body>
</html>

