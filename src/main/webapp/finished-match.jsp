<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Завершённый матч</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="style.css">
    <style>
        .completed-match-container {
            max-width: 600px;
            margin: 60px auto;
            background-color: #ffffff;
            border-radius: 12px;
            padding: 30px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
            color: #473a3d;
            text-align: center;
        }

        .completed-match-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        .completed-match-table th,
        .completed-match-table td {
            border: 1px solid #e0d1ce;
            padding: 12px;
            font-size: 1rem;
        }

        .completed-match-table th {
            background-color: #f4eae7;
            font-weight: 600;
            color: #8e605b;
        }

        .completed-match-table td {
            background-color: #fff8f6;
        }

        .winner-name {
            color: #cb7b6b;
            font-weight: bold;
        }

        .back-link {
            display: inline-block;
            margin-top: 30px;
            text-decoration: none;
            color: #8e605b;
            font-weight: 500;
        }

        .back-link:hover {
            text-decoration: underline;
        }
    </style>
</head>
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
<body>

<main>
    <div class="completed-match-container">
        <h2 class="page-title">Match finished</h2>

        <table class="completed-match-table">
            <tr>
                <th>Player 1</th>
                <th>Player 2</th>
                <th>Winner</th>
            </tr>
            <tr>
                <td>${player1.name}</td>
                <td>${player2.name}</td>
                <td class="winner-name">${winner.name}</td>
            </tr>
        </table>

        <a href="new-match.jsp" class="back-link">Create new match</a>
    </div>
</main>

</body>
</html>
