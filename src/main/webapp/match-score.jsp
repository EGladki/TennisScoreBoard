<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Счёт матча</title>
  <style>
    body {
      background-color: #121212;
      color: #e0e0e0;
      font-family: Arial, sans-serif;
      padding: 40px;
    }

    table {
      width: 100%;
      border-collapse: collapse;
      margin-bottom: 30px;
    }

    th, td {
      border: 1px solid #2a2a2a;
      padding: 15px;
      text-align: center;
    }

    th {
      background-color: #1f1f1f;
    }

    td {
      background-color: #1a1a1a;
    }

    form {
      display: inline-block;
      margin: 10px;
    }

    button {
      padding: 12px 24px;
      font-size: 16px;
      background-color: #03dac6;
      border: none;
      color: #000;
      font-weight: bold;
      border-radius: 5px;
      cursor: pointer;
    }

    button:hover {
      background-color: #00bfa5;
    }

    a {
      color: #03dac6;
      text-decoration: none;
      display: inline-block;
      margin-top: 30px;
    }

    a:hover {
      color: #00bfa5;
    }
  </style>
</head>
<body>

<h1>Текущий матч</h1>

<table>
  <tr>
    <th>Игрок</th>
    <th>Score</th>
    <th>Game</th>
    <th>Set</th>
  </tr>
  <tr>
    <td>${player1.name}</td>
    <td>${player1Score}</td>
    <td>${player1Game}</td>
    <td>${player1Set}</td>
  </tr>
  <tr>
    <td>${player2.name}</td>
    <td>${player2Score}</td>
    <td>${player2Game}</td>
    <td>${player2Set}</td>
  </tr>
</table>

<!-- Кнопка: Игрок 1 выиграл очко -->
<form method="post" action="match-score?uuid=${uuid}">
  <input type="hidden" name="playerId" value="${player1.id}">
  <button type="submit">${player1.name} выиграл очко</button>
</form>

<!-- Кнопка: Игрок 2 выиграл очко -->
<form method="post" action="match-score?uuid=${uuid}">
  <input type="hidden" name="playerId" value="${player2.id}">
  <button type="submit">${player2.name} выиграл очко</button>
</form>

<a href="matches">⬅ Назад к матчам</a>

</body>
</html>
