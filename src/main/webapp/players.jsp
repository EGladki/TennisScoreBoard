<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Tennis Scoreboard | Players</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="style.css">
    <style>
        .players-container {
            background-color: #ffffff;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
            max-width: 800px;
            margin: 0 auto;
        }

        .players-title {
            color: #8e605b;
            margin-bottom: 25px;
            text-align: center;
        }

        .players-table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
        }

        .players-table th, .players-table td {
            padding: 15px;
            text-align: left;
            border-bottom: 1px solid #f4eae7;
        }

        .players-table th {
            background-color: #8e605b;
            color: #f4eae7;
            font-weight: 500;
            text-transform: uppercase;
            font-size: 0.9rem;
            letter-spacing: 0.5px;
        }

        .players-table tr:nth-child(even) {
            background-color: #faf5f4;
        }

        .players-table tr:hover {
            background-color: #f4eae7;
        }

        .player-name {
            font-weight: 500;
            color: #473a3d;
        }

        .player-stats {
            font-weight: 400;
            color: #8e605b;
        }

        .add-player-form {
            margin-top: 30px;
            padding: 20px;
            background-color: #faf5f4;
            border-radius: 8px;
        }

        .form-group {
            margin-bottom: 15px;
        }

        .form-label {
            display: block;
            margin-bottom: 8px;
            color: #8e605b;
            font-weight: 500;
        }

        .form-input {
            width: 100%;
            padding: 10px 15px;
            border: 2px solid #cb7b6b;
            border-radius: 8px;
            background-color: #ffffff;
            color: #473a3d;
            font-size: 1rem;
        }

        .form-submit {
            width: 100%;
            padding: 12px;
            background-color: #cb7b6b;
            color: #ffffff;
            font-weight: bold;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            font-size: 1rem;
            transition: background-color 0.3s;
        }

        .form-submit:hover {
            background-color: #8e605b;
        }
    </style>
</head>
<body>

<header class="header">
    <section class="nav-header">
        <div class="brand">
            <img src="images/menu.png" alt="Logo" class="logo">
            <span class="logo-text">TennisScoreboard</span>
        </div>
        <nav class="nav-links">
            <a href="index.jsp">Home</a>
            <a href="matches">Matches</a>
            <a href="players" class="active">Players</a>
        </nav>
    </section>
</header>

<main>
    <div class="container">
        <div class="players-container">
            <h1 class="players-title">Players</h1>

            <table class="players-table">
                <thead>
                <tr>
                    <th>Player</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="player" items="${players}">
                    <tr>
                        <td class="player-name">${player.name}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

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