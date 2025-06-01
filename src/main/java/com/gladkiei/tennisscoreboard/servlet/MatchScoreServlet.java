package com.gladkiei.tennisscoreboard.servlet;

import com.gladkiei.tennisscoreboard.dao.MatchScoreModelDao;
import com.gladkiei.tennisscoreboard.dao.PlayerDao;
import com.gladkiei.tennisscoreboard.models.Match;
import com.gladkiei.tennisscoreboard.models.MatchScoreModel;
import com.gladkiei.tennisscoreboard.service.FinishedMatchesPersistenceService;
import com.gladkiei.tennisscoreboard.service.MatchScoreCalculationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

import static com.gladkiei.tennisscoreboard.enums.MatchState.COMPLETED;

@WebServlet("/match-score")
public class MatchScoreServlet extends HttpServlet {
    private final PlayerDao playerDao = new PlayerDao();
    private final MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();
    private final FinishedMatchesPersistenceService finishedMatchesPersistenceService = new FinishedMatchesPersistenceService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID uuid = UUID.fromString(req.getParameter("uuid"));
        MatchScoreModel matchScoreModel = MatchScoreModelDao.getInstance().getModel(uuid);

        if (isCompleted(matchScoreModel)) {
            Match match = finishedMatchesPersistenceService.getSavedMatch(uuid);
            setAllAttributesForMatch(req, match);
            req.getRequestDispatcher("/finished-match.jsp").forward(req, resp);
        } else {
            setAllAttributesForModel(req, uuid, matchScoreModel);
            req.getRequestDispatcher("/match-score.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String playerId = req.getParameter("playerId");
        long id = Long.parseLong(playerId);

        UUID uuid = UUID.fromString(req.getParameter("uuid"));
        matchScoreCalculationService.updateScore(uuid, id);

        resp.sendRedirect("match-score" + "?uuid=" + uuid);

    }

    private void setAllAttributesForModel(HttpServletRequest req, UUID uuid, MatchScoreModel matchScoreModel) {
        req.setAttribute("uuid", uuid.toString());
        req.setAttribute("player1", playerDao.findById(matchScoreModel.getPlayer1ScoreModel().getPlayerId()));
        req.setAttribute("player2", playerDao.findById(matchScoreModel.getPlayer2ScoreModel().getPlayerId()));
        req.setAttribute("player1Score", matchScoreModel.getPlayer1ScoreModel().getPlayerScore());
        req.setAttribute("player2Score", matchScoreModel.getPlayer2ScoreModel().getPlayerScore());
        req.setAttribute("player1Game", matchScoreModel.getPlayer1ScoreModel().getPlayerGame());
        req.setAttribute("player2Game", matchScoreModel.getPlayer2ScoreModel().getPlayerGame());
        req.setAttribute("player1Set", matchScoreModel.getPlayer1ScoreModel().getPlayerSet());
        req.setAttribute("player2Set", matchScoreModel.getPlayer2ScoreModel().getPlayerSet());
    }

    private void setAllAttributesForMatch(HttpServletRequest req, Match match) {
        req.setAttribute("player1", match.getPlayer1());
        req.setAttribute("player2", match.getPlayer2());
        req.setAttribute("winner", match.getWinner());
    }

    public boolean isCompleted(MatchScoreModel matchScoreModel) {
        return matchScoreModel.getState() == COMPLETED;
    }
}
