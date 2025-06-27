<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Tennis Scoreboard | New Match</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="style.css">

    <script src="js/app.js"></script>
</head>
<body>

<header class="header">
    <section class="nav-header">
        <div class="brand">
            <span class="logo-text">TennisScoreboard</span>
        </div>
        <nav class="nav-links">
            <a href="index.jsp">Home</a>
            <a href="matches">Matches</a>
            <a class="nav-link" href="players">Players</a>
        </nav>
    </section>
</header>

<main>
    <div class="form-container">
        <h1 class="page-title">Create New Match</h1>
        <form method="post" action="new-match">
            <c:if test="${not empty errorMessage}">
                <div style="color: red; margin-bottom: 20px; font-weight: bold;">
                        ${errorMessage}
                </div>
            </c:if>
            <label class="label-player">
                Player 1:
                <input type="text" name="player1" class="input-player" required>
            </label>
            <label class="label-player">
                Player 2:
                <input type="text" name="player2" class="input-player" required>
            </label>
            <button type="submit" class="form-button">Add Match</button>
        </form>

        <div class="links">
            <a href="matches">Match List</a>
            <a href="players">Player List</a>
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
