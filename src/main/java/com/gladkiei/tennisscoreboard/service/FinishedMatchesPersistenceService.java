package com.gladkiei.tennisscoreboard.service;

import com.gladkiei.tennisscoreboard.dao.MatchDao;
import com.gladkiei.tennisscoreboard.dao.MatchScoreModelDao;
import com.gladkiei.tennisscoreboard.dao.PlayerDao;
import com.gladkiei.tennisscoreboard.models.Match;
import com.gladkiei.tennisscoreboard.models.MatchScoreModel;
import com.gladkiei.tennisscoreboard.models.Player;

import java.util.UUID;

import static com.gladkiei.tennisscoreboard.service.OngoingMatchService.COMPLETED;

public class FinishedMatchesPersistenceService {
    private final MatchDao matchDao = new MatchDao();
    private final PlayerDao playerDao = new PlayerDao();
    private final MatchScoreModelDao matchScoreModelDao = new MatchScoreModelDao();

    public void getFinishedMatch(UUID uuid) {
        MatchScoreModel matchScoreModel = matchScoreModelDao.getModel(uuid);

        // invoke updateScore() from calcService and when match COMPLETED -> return new Match

        if (isCompleted(matchScoreModel)) {
            Match finishedMatch = createCompletedMatch(uuid, winnerId);
            matchDao.save(finishedMatch);
            matchScoreModelDao.remove(uuid);
        }
    }

    public Long getFinishedMatchId(Match match) {
        return match.getId();
    }

    private boolean isCompleted(MatchScoreModel model) {
        return model.isState() == COMPLETED;
    }

    private Match createCompletedMatch(UUID uuid, Long winnerId) {
        MatchScoreModel model = MatchScoreModelDao.getInstance().getModel(uuid);
        Player player1 = playerDao.findById(model.getPlayer1ScoreModel().getPlayerId());
        Player player2 = playerDao.findById(model.getPlayer2ScoreModel().getPlayerId());
        Player winner = playerDao.findById(winnerId);
        return new Match(player1, player2, winner);
    }

    // инкапсуляция готовых матчей в БД
}
