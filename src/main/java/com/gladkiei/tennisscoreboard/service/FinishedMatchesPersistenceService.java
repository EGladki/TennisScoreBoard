package com.gladkiei.tennisscoreboard.service;

import com.gladkiei.tennisscoreboard.dao.MatchDao;
import com.gladkiei.tennisscoreboard.dao.MatchScoreModelDao;
import com.gladkiei.tennisscoreboard.dao.PlayerDao;
import com.gladkiei.tennisscoreboard.models.common.Match;
import com.gladkiei.tennisscoreboard.models.entity.MatchScoreModel;
import com.gladkiei.tennisscoreboard.models.common.Player;

import java.util.UUID;

import static com.gladkiei.tennisscoreboard.enums.WinnerStatus.WINNER;

public class FinishedMatchesPersistenceService {
    private final MatchDao matchDao = new MatchDao();
    private final PlayerDao playerDao = new PlayerDao();
    private final MatchScoreModelDao matchScoreModelDao = new MatchScoreModelDao();

    public Match getSavedMatch(UUID uuid) {
        MatchScoreModel matchScoreModel = matchScoreModelDao.getModel(uuid);
        Long winnerId = getWinnerId(matchScoreModel);

        Match finishedMatch = createCompletedMatch(uuid, winnerId);
        matchDao.save(finishedMatch);
        matchScoreModelDao.remove(uuid);
        return finishedMatch;
    }

    private Match createCompletedMatch(UUID uuid, Long winnerId) {
        MatchScoreModel model = MatchScoreModelDao.getInstance().getModel(uuid);
        Player player1 = playerDao.getById(model.getPlayer1ScoreModel().getPlayerId());
        Player player2 = playerDao.getById(model.getPlayer2ScoreModel().getPlayerId());
        Player winner = playerDao.getById(winnerId);
        return new Match(player1, player2, winner);
    }

    private Long getWinnerId(MatchScoreModel matchScoreModel) {
        if (matchScoreModel.getPlayer1ScoreModel().getWinnerStatus() == WINNER) {
            return matchScoreModel.getPlayer1ScoreModel().getPlayerId();
        } else {
            return matchScoreModel.getPlayer2ScoreModel().getPlayerId();
        }
    }
}
