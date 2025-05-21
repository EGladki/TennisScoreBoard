package com.gladkiei.tennisscoreboard.servlet;

import com.gladkiei.tennisscoreboard.dao.MatchDao;
import com.gladkiei.tennisscoreboard.dao.OngoingMatchDao;
import com.gladkiei.tennisscoreboard.dao.PlayerDao;
import com.gladkiei.tennisscoreboard.models.Match;
import com.gladkiei.tennisscoreboard.models.OngoingMatch;
import com.gladkiei.tennisscoreboard.models.Player;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

@WebServlet("/match-score")
public class MatchScoreServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID uuid = UUID.fromString(req.getParameter("uuid"));
        OngoingMatch match = OngoingMatchDao.getInstance().getMatch(uuid);

        PlayerDao playerDao = new PlayerDao();
        Player player1 = playerDao.findById(match.getPlayer1Id());
        Player player2 = playerDao.findById(match.getPlayer2Id());

        req.setAttribute("uuid", uuid.toString());
        req.setAttribute("player1", player1);
        req.setAttribute("player2", player2);
        req.setAttribute("player1Score", match.getPlayer1Score());
        req.setAttribute("player2Score", match.getPlayer2Score());

        System.out.println(match);
        req.getRequestDispatcher("/match-score.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // обновление счета по player id
        String playerId = req.getParameter("playerId");
        long id = Long.parseLong(playerId);

        UUID uuid = UUID.fromString(req.getParameter("uuid"));
        OngoingMatch match = OngoingMatchDao.getInstance().getMatch(uuid);

        OngoingMatch updated = new OngoingMatch(uuid, match.getPlayer1Id(), match.getPlayer2Id(), match.getPlayer1Score() + 1, match.getPlayer2Score());
        OngoingMatchDao.getInstance().remove(uuid);
        OngoingMatchDao.getInstance().put(uuid,updated);

        resp.sendRedirect("match-score" + "?uuid=" + uuid );
    }
}
