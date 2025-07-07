<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Error | Tennis Scoreboard</title>
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
      <a href="players">Players</a>
    </nav>
  </section>
</header>

<main>
  <div class="form-container">
    <h1 class="page-title">Oops! Something went wrong.</h1>

    <c:choose>
      <c:when test="${not empty message}">
        <p>${message}</p>
      </c:when>
      <c:otherwise>
        <p>An unexpected error occurred. Please try again later.</p>
      </c:otherwise>
    </c:choose>

    <div class="homepage-action-button">
      <a href="index.jsp" class="btn">Go to Home</a>
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
