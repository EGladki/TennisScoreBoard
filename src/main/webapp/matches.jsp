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
        </nav>
    </section>
</header>

<ul>
    <c:forEach var="match" items="${matches}">
        <li>
            Match #${match.id}:
                ${match.player1.name} vs ${match.player2.name}
            Winner: ${match.winner.name}
        </li>
    </c:forEach>
</ul>

<main>
    <div class="container">
        <h1>Matches</h1>
        <div class="input-container">
            <input class="input-filter" placeholder="Filter by name" type="text"/>
            <div>
                <a href="#">
                    <button class="btn-filter">Reset Filter</button>
                </a>
            </div>
        </div>

        <table class="table-matches">


            <tr>
                <th>Player One</th>
                <th>Player Two</th>
                <th>Winner</th>
            </tr>
            <tr>
                <td>Rafael Nadal</td>
                <td>Roger Federer</td>
                <td><span class="winner-name-td">Rafael Nadal</span></td>
            </tr>
            <tr>
                <td>Rafael Nadal</td>
                <td>Roger Federer</td>
                <td><span class="winner-name-td">Roger Federer</span></td>
            </tr>
            <tr>
                <td>Rafael Nadal</td>
                <td>Roger Federer</td>
                <td><span class="winner-name-td">Rafael Nadal</span></td>
            </tr>
            <tr>
                <td>Rafael Nadal</td>
                <td>Roger Federer</td>
                <td><span class="winner-name-td">Roger Federer</span></td>
            </tr>
            <tr>
                <td>Rafael Nadal</td>
                <td>Roger Federer</td>
                <td><span class="winner-name-td">Rafael Nadal</span></td>
            </tr>
        </table>

        <div class="pagination">
            <a class="prev" href="#"> < </a>
            <a class="num-page current" href="#">1</a>
            <a class="num-page" href="#">2</a>
            <a class="num-page" href="#">3</a>
            <a class="next" href="#"> > </a>
        </div>
    </div>
</main>

<%--<main>--%>
<%--    <div class="container">--%>
<%--        <h1 class="page-title">Matches</h1>--%>
<%--        <div class="form-container">--%>
<%--            <c:choose>--%>
<%--                <c:when test="${empty matches}">--%>
<%--                    <p>No matches found.</p>--%>
<%--                </c:when>--%>
<%--                <c:otherwise>--%>
<%--                    <ul style="list-style: none; padding: 0; margin: 0;">--%>
<%--                        <c:forEach var="match" items="${matches}">--%>
<%--                            <li style="margin-bottom: 15px; padding: 15px; border-radius: 12px; background-color: #f4eae7; border: 1px solid #cb7b6b;">--%>
<%--                                <div style="color: #473a3d; font-weight: 500;">--%>
<%--                                    Match #${match.id}:--%>
<%--                                    <strong>${match.player1.name}</strong> vs <strong>${match.player2.name}</strong><br>--%>
<%--                                    Winner: <span style="color: #8e605b; font-weight: bold;">${match.winner.name}</span>--%>
<%--                                </div>--%>
<%--                            </li>--%>
<%--                        </c:forEach>--%>
<%--                    </ul>--%>
<%--                </c:otherwise>--%>
<%--            </c:choose>--%>
<%--        </div>--%>

<%--        <div class="links">--%>
<%--            <a href="new-match.jsp">Start New Match</a>--%>
<%--            <a href="players">Player List</a>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--</main>--%>

<footer>
    <p>&copy; Tennis Scoreboard, project from
        <a href="https://zhukovsd.github.io/java-backend-learning-course/" target="_blank">
            zhukovsd/java-backend-learning-course
        </a>
    </p>
</footer>

</body>
</html>
