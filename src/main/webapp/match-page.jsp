<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%--<html>--%>
<%--<head>--%>
<%--    <title>Новый матч</title>--%>
<%--</head>--%>
<%--<body>--%>
<%--<h1>Создать матч</h1>--%>
<%--<form method="post" action="new-match">--%>
<%--    <label>Игрок 1: <input type="text" name="player1" required></label><br><br>--%>
<%--    <label>Игрок 2: <input type="text" name="player2" required></label><br><br>--%>
<%--    <button type="submit">Добавить игроков</button>--%>
<%--</form>--%>
<%--</body>--%>
<%--</html>--%>

<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%--<html>--%>
<%--<head>--%>
<%--    <title>Новый матч</title>--%>
<%--    <style>--%>
<%--        body {--%>
<%--            font-family: Arial, sans-serif;--%>
<%--            max-width: 600px;--%>
<%--            margin: 0 auto;--%>
<%--            padding: 20px;--%>
<%--        }--%>
<%--        form {--%>
<%--            display: flex;--%>
<%--            flex-direction: column;--%>
<%--            gap: 15px;--%>
<%--        }--%>
<%--        input, button {--%>
<%--            padding: 10px;--%>
<%--            font-size: 16px;--%>
<%--        }--%>
<%--        button {--%>
<%--            background-color: #4CAF50;--%>
<%--            color: white;--%>
<%--            border: none;--%>
<%--            cursor: pointer;--%>
<%--        }--%>
<%--        button:hover {--%>
<%--            background-color: #FFA500;--%>
<%--        }--%>
<%--        .players-link {--%>
<%--            display: block;--%>
<%--            margin-top: 20px;--%>
<%--            text-align: center;--%>
<%--        }--%>
<%--    </style>--%>
<%--</head>--%>
<%--<body>--%>
<%--<h1>Создать новый матч</h1>--%>
<%--<form action="new-match" method="post">--%>
<%--    <input type="text" name="player1" placeholder="Игрок 1" required>--%>
<%--    <input type="text" name="player2" placeholder="Игрок 2" required>--%>
<%--    <button type="submit">Начать матч</button>--%>
<%--</form>--%>

<%--<a href="players" class="players-link">Посмотреть всех игроков</a>--%>
<%--</body>--%>
<%--</html>--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Новый матч</title>
    <style>
        body {
            background-color: #121212;
            color: #e0e0e0;
            font-family: Arial, sans-serif;
            padding: 40px;
        }

        h1 {
            color: #ffffff;
        }

        form {
            background-color: #1e1e1e;
            padding: 20px;
            border-radius: 10px;
            max-width: 400px;
        }

        label {
            display: block;
            margin-bottom: 15px;
        }

        input[type="text"] {
            width: 100%;
            padding: 10px;
            background-color: #2c2c2c;
            border: 1px solid #444;
            color: #fff;
            border-radius: 5px;
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
<h1>Создать матч</h1>
<form method="post" action="new-match">
    <label>Игрок 1:
        <input type="text" name="player1" required>
    </label>
    <label>Игрок 2:
        <input type="text" name="player2" required>
    </label>
    <button type="submit">Добавить игроков</button>
</form>
</body>
</html>
