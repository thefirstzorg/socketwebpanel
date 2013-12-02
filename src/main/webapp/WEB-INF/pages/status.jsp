<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
    <a href="/view/live">LIVE(${live})</a><br/>
    <a href="/view/info">INFO(${info})</a><br/>
    <a href="/">Start/restart sockets</a><br/>
    <iframe src="/view/live" style="width:800px; height: 250px; border: none;"></iframe>
    <iframe src="/view/info" style="width:800px; height: 250px; border: none;"></iframe>
</body>
</html>