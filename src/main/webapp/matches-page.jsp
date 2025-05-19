<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Match Page</title>
</head>
<body>
<h1>New Match Started</h1>
<p>Player 1: ${player1.name}</p>
<p>Player 2: ${player2.name}</p>

<!-- Форма для ввода счёта -->
<form action="save-match" method="post">
    <input type="number" name="player1Score">
    <input type="number" name="player2Score">
    <button type="submit">Save Result</button>
</form>
</body>
</html>