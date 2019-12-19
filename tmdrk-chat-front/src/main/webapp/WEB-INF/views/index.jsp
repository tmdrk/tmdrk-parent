<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getContextPath();
%>
<html>
<head>
    <title>Title</title>
    <script src="https://code.jquery.com/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="../static/js/index.js"></script>
    <script>
        var basePath = '<%=basePath%>';
    </script>
</head>
<body >
  <h1 style="text-align:center">聊天系统前台</h1>
</body>
</html>