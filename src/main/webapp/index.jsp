<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tennis Scoreboard | Home</title>
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
        <h1>Welcome to Tennis Scoreboard</h1>
        <p>Manage your tennis matches, record results, and track rankings</p>
        <div class="form-container center">
            <a class="homepage-action-button" href="new-match.jsp">
                <button class="btn start-match">
                    Start a new match
                </button>
            </a>
            <a class="homepage-action-button" href="matches">
                <button class="btn view-results">
                    View match results
                </button>
            </a>
        </div>
    </div>
</main>
<footer>
    <div class="footer">
        <p>&copy; Tennis Scoreboard, project from <a href="https://zhukovsd.github.io/java-backend-learning-course/">zhukovsd/java-backend-learning-course</a>
            roadmap.</p>
    </div>
</footer>
</body>
</html>
