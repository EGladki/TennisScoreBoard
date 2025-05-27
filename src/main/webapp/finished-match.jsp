<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Завершённый матч</title>
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

        table {
            width: 100%;
            border-collapse: collapse;
            background-color: #1e1e1e;
            border-radius: 8px;
            overflow: hidden;
            margin-top: 20px;
        }

        th, td {
            padding: 15px;
            text-align: center;
            border-bottom: 1px solid #2a2a2a;
        }

        th {
            background-color: #2a2a2a;
            color: #ffffff;
        }

        tr:last-child td {
            border-bottom: none;
        }

        .winner {
            color: #03dac6;
            font-weight: bold;
        }
    </style>
</head>
<body>

<h1>Завершённый матч</h1>

<table>
    <tr>
        <th>Игрок 1</th>
        <th>Игрок 2</th>
        <th>Победитель</th>
    </tr>
    <tr>
        <td>${player1.name}</td>
        <td>${player2.name}</td>
        <td class="winner">${winner.name}</td>
    </tr>
</table>

<a href="matches" style="color: #03dac6; text-decoration: none; display: inline-block; margin-top: 20px;">← Назад к матчам</a>

</body>
</html>
