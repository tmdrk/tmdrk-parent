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
        function queryIndexList() {
            $.ajax({
                url: "/chat/login",
                type: 'POST',
                dataType: 'json',
                data: $("#loginForm ").serialize(),
                async: false,
                success: function (data) {
                    if (data.code == 1) {
                        console.log(data);
                        var html = convertLoginList(data.list);
                        $("#loginList").empty();
                        $("#loginList").append(html);
                    } else {
                        alert("登录失败");
                    }
                }
            });
        }
    </script>
</head>
<body >
  <h1 style="text-align:center">聊天系统后台</h1>
  <form id="loginForm" action="/login">
      <input type="text" id="userId" name="userId" placeholder="请输入用户名" /> <br />
      <input type="text" id="password" name="password" placeholder="请输入用户密码" /> <br />
  </form>
  <div id="loginList">
      <div></div>
      <div></div>
      <div></div>
      <div></div>
  </div>
</body>
</html>

