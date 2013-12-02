<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
    ${type}
    <br/>
    <form action="/view/${type}" method="POST" >
        <textarea name="message" cols="100" rows="10">

        </textarea>
        <br />
        <input type="submit" value="send" />
    </form>
</body>
</html>