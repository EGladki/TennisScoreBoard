package com.gladkiei.tennisscoreboard.servlet;

import com.gladkiei.tennisscoreboard.dao.PlayerDao;
import com.gladkiei.tennisscoreboard.models.Player;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/getPlayers")
public class GetPlayersServlet extends HttpServlet {

    private final PlayerDao playerDao = new PlayerDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Player> all = playerDao.getAll();
        System.out.println(all);
    }
}
