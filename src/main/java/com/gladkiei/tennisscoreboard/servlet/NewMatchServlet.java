package com.gladkiei.tennisscoreboard.servlet;

import com.gladkiei.tennisscoreboard.dao.PlayerDao;
import com.gladkiei.tennisscoreboard.dto.PlayerRequestDto;
import com.gladkiei.tennisscoreboard.models.MatchScoreModel;
import com.gladkiei.tennisscoreboard.models.Player;
import com.gladkiei.tennisscoreboard.service.OngoingMatchService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/new-match")
public class NewMatchServlet extends HttpServlet {

    private final PlayerDao playerDao = new PlayerDao();
    private final OngoingMatchService ongoingMatchService = new OngoingMatchService();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String firstPlayerName = req.getParameter("player1");
        String secondPlayerName = req.getParameter("player2");

        Player player1 = playerDao.getPlayer(firstPlayerName);
        Player player2 = playerDao.getPlayer(secondPlayerName);

        MatchScoreModel matchScoreModel = ongoingMatchService.startMatch(player1, player2);
        UUID uuid = matchScoreModel.getUuid();

        resp.sendRedirect("match-score" + "?uuid=" + uuid );
//        req.getRequestDispatcher("/match-score.jsp").forward(req, resp);

    }

}



