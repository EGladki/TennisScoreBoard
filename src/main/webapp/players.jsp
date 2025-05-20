
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Список игроков</title>
</head>
<body>
<h1>Игроки:</h1>
<ul>
    <c:forEach var="player" items="${players}">
    <li>${player.name}</li>
    </c:forEach>
</ul>
</body>
</html>