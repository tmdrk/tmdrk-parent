<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getContextPath();
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Title</title>
    <script src="https://code.jquery.com/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="../static/js/mychat.js"></script>
    <link href="../static/css/mychat.css" rel="stylesheet" type="text/css" />
    <script>
        var basePath = '<%=basePath%>';
    </script>
    <style>
    </style>
    <script>
        var socket;
        if(!window.WebSocket){
            window.WebSocket = window.MozWebSocket;
        }
        if(window.WebSocket){
            console.log("您的浏览器支持WebSocket协议！ socket="+socket);
            socket = new WebSocket("ws://localhost:8888/ws");
            console.log("socket="+socket);
            socket.onmessage = function(event){
                // var ta = document.getElementById('responseText');
                // ta.innerHTML = event.data+"\r\n";
                appendMessage(JSON.parse(event.data),0);
            };
            socket.onopen = function(event){
                /*var ta = document.getElementById('responseText');
                ta.innerHTML = "这里显示服务器推送信息"+"\r\n";*/

            };
            socket.onclose = function(event){
                alert("服务器关闭")
            };
        }else{
            alert("您的浏览器不支持WebSocket协议！");
        }
        function sendMessage(userId,toUserId){
            var message = $('#msg_tab_'+toUserId+' #messageInfo').val();
            console.log("message:"+message)
            if(message==''){
                return;
            }
            if(!window.WebSocket){
                console.log("window.WebSocket无效")
                return;
            }
            if(socket.readyState == WebSocket.OPEN){
                var data = {
                    "from" : userId,
                    "to" : toUserId,
                    "message": message,
                    "type" : "SINGLE_SENDING"
                };
                console.log("发送消息message："+message)
                send(JSON.stringify(data));
                console.log("发送消息结束")
                appendMessage(data,1);
                $('#msg_tab_'+toUserId+' #messageInfo').val('');

            }else{
                alert("WebSocket 连接没有建立成功！");
            }

        }
        //webSocket发送数据
        function send(data){
            socket.send(data);
        }
        function register(userId){
            var data = {
                "from" : userId,
                "type" : "REGISTER"
            };
            send(JSON.stringify(data));
        }
        function appendMessage(data,flag){
            var html = '';
            var user = localStorage.getItem(data.from);
            user = JSON.parse(user);
            if(flag==1){
                html = '                      <div class="msg_content">\n' +
                    '                          <div class="msg_img"><img src="'+user.img+'" alt="">  </div>\n' +
                    '                          <div class="msg_text">\n' +
                    '                              <p class="item_left">\n' +data.message+
                    '                              </p>\n' +
                    '                          </div>\n' +
                    '                          <div style="clear:both"></div>\n' +
                    '                      </div>';
                $('#msg_tab_'+data.to+' #message_line').append(html);
            }else{
                if(!$('#msg_tab_'+data.from)[0]){
                    console.log("构建新的标签页")
                    var html = create_tab(data.from,user.name,user.img);
                    $('#msg_navigation').append(html);
                }
                html = '<div class="msg_content">\n' +
                    '                          <div class="msg_img_right"><img src="'+user.img+'" alt="">  </div>\n' +
                    '                          <div class="msg_text_right">\n' +
                    '                              <p class="item_right">\n' +data.message+
                    '                              </p>\n' +
                    '                          </div>\n' +
                    '                          <div style="clear:both"></div>\n' +
                    '                      </div>';
                $('#msg_tab_'+data.from+' #message_line').append(html);
            }
            //将div滚轮显示最下部内容
            scrollBottom('message_line');
        }
        // 将div滚轮显示最下部内容
        function scrollBottom(id){
            var messageLine = document.getElementById(id);
            messageLine.scrollTop = messageLine.scrollHeight;
        }
    </script>
</head>
<body class="ng-scope ng-isolate-scope loaded">
  <%--<h1 style="text-align:center">聊天系统后台</h1>--%>
  <div id="login" class="login" >
      <form id="loginForm">
          <span style="width:100px;display:inline-block;">用户名</span><input type="text" id="userId" name="userId" placeholder="请输入用户名" /> <br /><br />
          <span style="width:100px;display:inline-block;">用户密码</span><input type="text" id="password" name="password" placeholder="请输入用户密码" /> <br /><br />
          <a href="javascript:void(0);" class="login_btn" onclick="user_login()">登录</a>
      </form>
  </div>
  <div id="main" class="main" style="display: none">
      <div class="main_inner" >
          <div class="panel" >
              <div class="header">
                  <div class="avatar">
                      <img class="img" id="selfImg" src="../static/img/myhead.jpg">
                  </div>
                  <div class="info"><h3><span id="nickName"></span></h3></div>
              </div>
              <div class="search_bar">
                  <i class="web_wechat_search"></i>
                  <input mm-action-track="" track-type="['focus']" track-opt="{'target':'顶部搜索'}" class="frm_search ng-isolate-scope ng-pristine ng-valid" type="text" ng-model="keyword" ng-input="search($event)" ng-keydown="searchKeydown($event)" ng-blur="focus = false;" placeholder="搜索">
              </div>
<%--              <div class="tab" style="border:solid black 1px;">
                  <div class="tab_item">
                      <a class="chat" title="聊天" href="#"><i class="web_wechat_tab_chat web_wechat_tab_chat_hl"></i></a>
                  </div>
              </div>--%>
              <div class="nav_view ng-scope" style="border:solid black 1px;">
                  <%--<div class="scroll-wrapper chat_list scrollbar-dynamic">--%>
                      <%--<div class="chat_list scrollbar-dynamic scroll-content scroll-scrolly_visible">--%>
                      <div id="navigation" class="ng-scope">
                          <div style="height:90px;border-bottom: solid black 1px;" class="ng-scope" onclick="showTable(1)">
                              <div class="chat_item slide-left ng-scope active">
                                  <div class="ng-scope1">
                                      <div class="avatar">
                                          <img class="img" src="../static/img/friend2.jpg" alt="">
                                      </div>
                                      <div class="ng-scope2">
                                          <h3 class="nickname">
                                              <span class="nickname_text ng-binding">张三</span>
                                          </h3>
                                      </div>
                                  </div>
                              </div>
                          </div>
                          <div style="height:90px;border-bottom: solid black 1px;" class="ng-scope" onclick="showTable(2)">
                              <div class="chat_item slide-left ng-scope active">
                                  <div class="ng-scope1">
                                      <div class="avatar">
                                          <img class="img" src="../static/img/friend2.jpg" alt="">
                                      </div>
                                      <div class="ng-scope2">
                                          <h3 class="nickname">
                                              <span class="nickname_text ng-binding">李四</span>
                                          </h3>
                                      </div>
                                  </div>
                              </div>
                          </div>
                          <div style="height:90px;border-bottom: solid black 1px;" class="ng-scope" onclick="showTable(3)">
                              <div class="chat_item slide-left ng-scope active">
                                  <div class="ng-scope1">
                                      <div class="avatar">
                                          <img class="img" src="../static/img/friend2.jpg" alt="">
                                      </div>
                                      <div class="ng-scope2">
                                          <h3 class="nickname">
                                              <span class="nickname_text ng-binding">王二麻</span>
                                          </h3>
                                      </div>
                                  </div>
                              </div>
                          </div>
                      </div>
                      <%--</div>--%>
                  <%--</div>--%>
              </div>
          </div>
          <div class="ng-scope" id="msg_navigation">
              <div class="box chat ng-scope no-choose msg_tab" id="msg_tab_1">
                  <div class="box_hd">
                      <div class="title_wrap">
                          <div class="title poi">
                          </div>
                      </div>
                  </div>
                  <div class="scroll-wrapper box_bd chat_bd scrollbar-dynamic" style="position: absolute;">

                  </div>
                  <div class="box_ft ng-scope">

                  </div>
              </div>
              <%--<div class="box chat ng-scope no-choose msg_tab" id="msg_tab_1">
                  <div class="box_hd">
                      <div class="title_wrap">
                          <div class="title poi">
                              <a class="title_name ng-binding">张三</a>
                          </div>
                      </div>
                  </div>
                  <div class="scroll-wrapper box_bd chat_bd scrollbar-dynamic" style="position: absolute;">
                      <div class="msg_content">
                          <div class="msg_img"><img src="../static/img/friend2.jpg" alt="">  </div>
                          <div class="msg_text">
                              <p class="item_left">
                              嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿
                              </p>
                          </div>
                          <div style="clear:both"></div>
                      </div>
                      <div class="msg_content">
                          <div class="msg_img"><img src="../static/img/friend2.jpg" alt="">  </div>
                          <div class="msg_text">
                              <p class="item_left">
                                  嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿
                              </p>
                          </div>
                          <div style="clear:both"></div>
                      </div>
                      <div class="msg_content">
                          <div class="msg_img"><img src="../static/img/friend2.jpg" alt="">  </div>
                          <div class="msg_text">
                              <p class="item_left">
                                  嘿嘿嘿嘿嘿嘿嘿嘿嘿
                              </p>
                          </div>
                          <div style="clear:both"></div>
                      </div>
                      <div class="msg_content">
                          <div class="msg_img_right"><img src="../static/img/friend2.jpg" alt="">  </div>
                          <div class="msg_text_right">
                              <p class="item_right">
                                  嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿
                              </p>
                          </div>
                          <div style="clear:both"></div>
                      </div>
                  </div>
                  <div class="box_ft ng-scope">
                      <div class="content ng-isolate-scope">
                          <textarea class="input_content"></textarea>
                      </div>
                      <div class="action">
                          <span class="desc ng-scope">按下Ctrl+Enter换行</span>
                          <a class="btn btn_send" href="javascript:;" onclick="sendTextMessage()">发送</a>
                      </div>
                  </div>
              </div>--%>

 <%--             <div class="box chat ng-scope no-choose msg_tab" id="msg_tab_2">
                  <div class="box_hd">
                      <div class="title_wrap">
                          <div class="title poi">
                              <a class="title_name ng-binding">李四</a>
                          </div>
                      </div>
                  </div>
                  <div class="scroll-wrapper box_bd chat_bd scrollbar-dynamic" style="position: absolute;">
                      <div class="msg_content">
                          <div class="msg_img"><img src="../static/img/friend2.jpg" alt="">  </div>
                          <div class="msg_text">
                              <p class="item_left">
                                  嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿
                              </p>
                          </div>
                          <div style="clear:both"></div>

                      </div>
                      <div class="msg_content">
                          <div class="msg_img_right"><img src="../static/img/friend2.jpg" alt="">  </div>
                          <div class="msg_text_right">
                              <p class="item_right">
                                  嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿
                              </p>
                          </div>
                          <div style="clear:both"></div>
                      </div>
                      <div class="msg_content">
                          <div class="msg_img"><img src="../static/img/friend2.jpg" alt="">  </div>
                          <div class="msg_text">
                              <p class="item_left">
                                  嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿
                              </p>
                          </div>
                          <div style="clear:both"></div>
                      </div>
                      <div class="msg_content">
                          <div class="msg_img"><img src="../static/img/friend2.jpg" alt="">  </div>
                          <div class="msg_text">
                              <p class="item_left">
                                  嘿嘿嘿嘿嘿嘿嘿嘿嘿
                              </p>
                          </div>
                          <div style="clear:both"></div>
                      </div>
                      <div class="msg_content">
                          <div class="msg_img_right"><img src="../static/img/friend2.jpg" alt="">  </div>
                          <div class="msg_text_right">
                              <p class="item_right">
                                  嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿
                              </p>
                          </div>
                          <div style="clear:both"></div>
                      </div>
                  </div>
                  <div class="box_ft ng-scope">
                      <div class="content ng-isolate-scope">
                          <textarea class="input_content"></textarea>
                      </div>
                      <div class="action">
                          <span class="desc ng-scope">按下Ctrl+Enter换行</span>
                          <a class="btn btn_send" href="javascript:;" onclick="sendTextMessage()">发送</a>
                      </div>
                  </div>
              </div>--%>

              <%--<div class="box chat ng-scope no-choose msg_tab" id="msg_tab_3">
                  <div class="box_hd">
                      <div class="title_wrap">
                          <div class="title poi">
                              <a class="title_name ng-binding">王二麻</a>
                          </div>
                      </div>
                  </div>
                  <div class="scroll-wrapper box_bd chat_bd scrollbar-dynamic" style="position: absolute;">
                      <div class="msg_content">
                          <div class="msg_img"><img src="../static/img/friend2.jpg" alt="">  </div>
                          <div class="msg_text">
                              <p class="item_left">
                                  dfksdhflksjdflksdflksdflskdfjslkdfjlk
                              </p>
                          </div>
                          <div style="clear:both"></div>

                      </div>
                      <div class="msg_content">
                          <div class="msg_img_right"><img src="../static/img/friend2.jpg" alt="">  </div>
                          <div class="msg_text_right">
                              <p class="item_right">
                                  嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿
                              </p>
                          </div>
                          <div style="clear:both"></div>
                      </div>
                      <div class="msg_content">
                          <div class="msg_img"><img src="../static/img/friend2.jpg" alt="">  </div>
                          <div class="msg_text">
                              <p class="item_left">
                                  嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿
                              </p>
                          </div>
                          <div style="clear:both"></div>
                      </div>
                      <div class="msg_content">
                          <div class="msg_img"><img src="../static/img/friend2.jpg" alt="">  </div>
                          <div class="msg_text">
                              <p class="item_left">
                                  收到了快放假了盛开的房间里上课的减肥路上看到房间里谁开的房间数量肯定
                              </p>
                          </div>
                          <div style="clear:both"></div>
                      </div>
                      <div class="msg_content">
                          <div class="msg_img_right"><img src="../static/img/friend2.jpg" alt="">  </div>
                          <div class="msg_text_right">
                              <p class="item_right">
                                  嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿嘿
                              </p>
                          </div>
                          <div style="clear:both"></div>
                      </div>
                  </div>
                  <div class="box_ft ng-scope">
                      <div class="content ng-isolate-scope">
                          <textarea class="input_content"></textarea>
                      </div>
                      <div class="action">
                          <span class="desc ng-scope">按下Ctrl+Enter换行</span>
                          <a class="btn btn_send" href="javascript:;" onclick="sendTextMessage()">发送</a>
                      </div>
                  </div>
              </div>--%>
          </div>
      </div>

  </div>

</body>
</html>

