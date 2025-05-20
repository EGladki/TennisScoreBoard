package com.gladkiei.tennisscoreboard.servlet;

import com.gladkiei.tennisscoreboard.dao.PlayerDao;
import com.gladkiei.tennisscoreboard.dto.PlayerRequestDto;
import com.gladkiei.tennisscoreboard.models.Player;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/new-match")
public class NewMatchServlet extends HttpServlet {

    private final PlayerDao playerDao = new PlayerDao();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String first = req.getParameter("player1");
        String second = req.getParameter("player2");

        Player player1 = playerDao.save(new PlayerRequestDto(first));
        Player player2 = playerDao.save(new PlayerRequestDto(second));

        resp.sendRedirect("players");

//        req.getRequestDispatcher("/players").forward(req, resp);

//        resp.sendRedirect(req.getContextPath() + "/match-page.jsp");

    }
}

