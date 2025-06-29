<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Tennis Scoreboard | Matches</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="style.css">
    <style>
        /* Дополнительные стили для таблицы */
        .table-container {
            overflow-x: auto;
            margin: 30px 0;
            border-radius: 12px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
        }

        .table-matches {
            width: 100%;
            border-collapse: collapse;
            background-color: #ffffff;
            color: #473a3d;
        }

        .table-matches th, .table-matches td {
            padding: 15px;
            text-align: center;
            border-bottom: 1px solid #f4eae7;
        }

        .table-matches th {
            background-color: #8e605b;
            color: #f4eae7;
            font-weight: 500;
            text-transform: uppercase;
            font-size: 0.9rem;
            letter-spacing: 0.5px;
        }

        .table-matches tr:nth-child(even) {
            background-color: #faf5f4;
        }

        .table-matches tr:hover {
            background-color: #f4eae7;
        }

        .table-matches .winner-cell {
            font-weight: 500;
            color: #8e605b;
        }

        .input-container {
            display: flex;
            justify-content: center;
            gap: 15px;
            margin-bottom: 30px;
            align-items: center;
        }

        .input-filter {
            padding: 10px 15px;
            border: 2px solid #cb7b6b;
            border-radius: 8px;
            background-color: #f4eae7;
            color: #473a3d;
            font-size: 1rem;
            width: 300px;
            max-width: 100%;
        }

        .btn-filter {
            padding: 10px 20px;
            background-color: #cb7b6b;
            color: #ffffff;
            font-weight: bold;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            font-size: 1rem;
            transition: background-color 0.3s;
        }

        .btn-filter:hover {
            background-color: #8e605b;
        }

        .pagination {
            display: flex;
            justify-content: center;
            gap: 10px;
            margin-top: 30px;
        }

        .pagination a {
            padding: 8px 15px;
            border-radius: 6px;
            text-decoration: none;
            color: #473a3d;
            font-weight: 500;
            transition: all 0.3s;
        }

        .pagination a:hover:not(.current) {
            background-color: #f4eae7;
        }

        .pagination .current {
            background-color: #8e605b;
            color: #f4eae7;
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
            <a href="players">Players</a>
        </nav>
    </section>
</header>

<main>
    <div class="container">
        <h1>Matches</h1>
        <div class="input-container">
            <form action="matches" method="get" class="input-container">

<%--            <input class="input-filter" placeholder="Filter by name" type="text"/>--%>
                <input name="filter_by_player_name"
                       class="input-filter"
                       placeholder="Filter by name"
                       type="text"/>
                <button type="submit" class="btn-filter">Select</button>
            </form>
        </div>

        <div class="table-container">
            <table class="table-matches">
                <thead>
                <tr>
                    <th>Player One</th>
                    <th>Player Two</th>
                    <th>Winner</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="match" items="${matches}">
                    <tr>
                        <td>${match.player1.name}</td>
                        <td>${match.player2.name}</td>
                        <td class="winner-cell">${match.winner.name}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="pagination">
            <a class="prev" href="#"> &lt; </a>
            <a class="num-page current" href="#">1</a>
            <a class="num-page" href="#">2</a>
            <a class="num-page" href="#">3</a>
            <a class="next" href="#"> &gt; </a>
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
