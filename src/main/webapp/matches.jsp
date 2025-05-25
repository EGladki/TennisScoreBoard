<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Список матчей</title>
    <style>
        body {
            background-color: #121212;
            color: #e0e0e0;
            font-family: Arial, sans-serif;
            padding: 40px;
        }

        h1 {
            color: #ffffff;
        }

        ul {
            list-style: none;
            padding-left: 0;
        }

        li {
            background-color: #1e1e1e;
            margin-bottom: 10px;
            padding: 15px;
            border-radius: 8px;
            font-size: 15px;
            border: 1px solid #2a2a2a;
        }

        a {
            color: #03dac6;
            text-decoration: none;
        }

        a:hover {
            color: #00bfa5;
        }
    </style>
</head>
<body>
<h1>Матчи</h1>
<ul>
    <c:forEach var="match" items="${matches}">
        <li>
               Матч #${match.id}:
            ${match.player1.name} vs ${match.player2.name}
            Победитель: ${match.winner.name}
        </li>
    </c:forEach>
</ul>

</body>
</html>