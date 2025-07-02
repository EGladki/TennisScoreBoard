<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Tennis Scoreboard | Match Score</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="style.css">
</head>
<body>

<header class="header">
    <section class="nav-header">
        <div class="brand">
            <div class="nav-toggle">
            </div>
            <span class="logo-text">TennisScoreboard</span>
        </div>
        <div>
            <nav class="nav-links">
                <a class="nav-link" href="index.jsp">Home</a>
                <a class="nav-link" href="matches">Matches</a>
                <a class="nav-link" href="players">Players</a>
            </nav>
        </div>
    </section>
</header>

<main>
    <div class="container">
        <h1 class="page-title">Current Match</h1>

        <div class="form-container">
            <table style="width: 100%; border-collapse: collapse;">
                <thead>
                <tr style="background-color: #f4eae7; color: #8e605b;">
                    <th style="padding: 12px; border-bottom: 2px solid #cb7b6b;">Player</th>
                    <th style="padding: 12px; border-bottom: 2px solid #cb7b6b;">Score</th>
                    <th style="padding: 12px; border-bottom: 2px solid #cb7b6b;">Game</th>
                    <th style="padding: 12px; border-bottom: 2px solid #cb7b6b;">Set</th>
                </tr>
                </thead>
                <tbody>
                <tr style="text-align: center; background-color: #fff;">
                    <td style="padding: 10px;">${player1.name}</td>
                    <td style="padding: 10px;">${player1Score}</td>
                    <td style="padding: 10px;">${player1Game}</td>
                    <td style="padding: 10px;">${player1Set}</td>
                </tr>
                <tr style="text-align: center; background-color: #fff;">
                    <td style="padding: 10px;">${player2.name}</td>
                    <td style="padding: 10px;">${player2Score}</td>
                    <td style="padding: 10px;">${player2Game}</td>
                    <td style="padding: 10px;">${player2Set}</td>
                </tr>
                </tbody>
            </table>

            <form method="post" action="match-score?uuid=${uuid}" style="margin: 15px 0;">
                <input type="hidden" name="playerId" value="${player1.id}">
                <button type="submit" class="btn">${player1.name} win score</button>
            </form>

            <form method="post" action="match-score?uuid=${uuid}" style="margin: 15px 0;">
                <input type="hidden" name="playerId" value="${player2.id}">
                <button type="submit" class="btn">${player2.name} win score</button>
            </form>

            <div class="links" style="margin-top: 20px;">
            </div>
        </div>
    </div>
</main>

<footer>
    <p>&copy; Tennis Scoreboard, project from
        <a href="https://zhukovsd.github.io/java-backend-learning-course/" target="_blank">
            zhukovsd/java-backend-learning-course
        </a>
    </p>
</footer>

</body>
</html>
