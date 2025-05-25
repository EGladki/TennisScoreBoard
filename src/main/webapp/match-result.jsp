<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Матч закончен</title>
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
<form method="get" action="new-match">

</form>
<a href="index.jsp">Начать новый матч</a>
<a href="matches">Список матчей</a>
<a href="players">Список игроков</a>
</body>
</html>
