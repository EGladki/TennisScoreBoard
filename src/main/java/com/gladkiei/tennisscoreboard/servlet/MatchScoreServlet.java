package com.gladkiei.tennisscoreboard.servlet;

import com.gladkiei.tennisscoreboard.dao.OngoingMatchDao;
import com.gladkiei.tennisscoreboard.dao.PlayerDao;
import com.gladkiei.tennisscoreboard.models.OngoingMatch;
import com.gladkiei.tennisscoreboard.models.Player;
import com.gladkiei.tennisscoreboard.service.MatchScoreCalculationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/match-score")
public class MatchScoreServlet extends HttpServlet {
    private final PlayerDao playerDao = new PlayerDao();
    private final MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID uuid = UUID.fromString(req.getParameter("uuid"));
        OngoingMatch match = OngoingMatchDao.getInstance().getMatch(uuid);

        Player player1 = playerDao.findById(match.getPlayer1Id());
        Player player2 = playerDao.findById(match.getPlayer2Id());

        req.setAttribute("uuid", uuid.toString());
        req.setAttribute("player1", player1);
        req.setAttribute("player2", player2);
        req.setAttribute("player1Score", match.getPlayer1Score());
        req.setAttribute("player2Score", match.getPlayer2Score());
        req.setAttribute("player1Game", match.getPlayer1Game());
        req.setAttribute("player2Game", match.getPlayer2Game());
        req.setAttribute("player1Set", match.getPlayer1Set());
        req.setAttribute("player2Set", match.getPlayer2Set());

        System.out.println(match);
        req.getRequestDispatcher("/match-score.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String playerId = req.getParameter("playerId");
        long id = Long.parseLong(playerId);

        UUID uuid = UUID.fromString(req.getParameter("uuid"));
        OngoingMatch match = OngoingMatchDao.getInstance().getMatch(uuid);

        matchScoreCalculationService.updateScore(uuid, id);

        resp.sendRedirect("match-score" + "?uuid=" + uuid );

    }
}
