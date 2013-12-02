<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
    ${type}
    <br/>
    <form action="/view/${type}" method="POST" >
        <input type="hidden" name="type" value="${type}" />
        <textarea name="message">

        </textarea>

        <input type="submit" value="send" />
    </form>
</body>
</html>