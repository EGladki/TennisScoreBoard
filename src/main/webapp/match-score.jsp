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

    h1 {
      color: #ffffff;
      margin-bottom: 30px;
    }

    .player {
      margin-bottom: 30px;
      padding: 20px;
      background-color: #1e1e1e;
      border-radius: 10px;
      border: 1px solid #2a2a2a;
    }

    .score {
      font-size: 24px;
      margin-bottom: 15px;
    }

    form {
      display: inline-block;
      margin-right: 20px;
    }

    button {
      padding: 10px 20px;
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
  </style>
</head>
<body>

<h1>Счёт матча</h1>

<div class="player">
  <div class="score">${player1.name}: ${player1Score}</div>
  <form method="post" action="score">
    <input type="hidden" name="playerId" value="${player1.id}">
    <button type="submit">Очко</button>
  </form>
</div>

<div class="player">
  <div class="score">${player2.name}: ${player2Score}</div>
  <form method="post" action="score">
    <input type="hidden" name="playerId" value="${player2.id}">
    <button type="submit">Очко</button>
  </form>
</div>

<a href="matches">⬅ Назад к матчам</a>

</body>
</html>
