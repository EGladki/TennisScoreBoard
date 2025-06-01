package com.gladkiei.tennisscoreboard.service;

import com.gladkiei.tennisscoreboard.dao.MatchScoreModelDao;
import com.gladkiei.tennisscoreboard.models.MatchScoreModel;
import com.gladkiei.tennisscoreboard.models.PlayerScoreModel;

import java.util.UUID;

import static com.gladkiei.tennisscoreboard.enums.MatchState.IN_PROGRESS;
import static com.gladkiei.tennisscoreboard.service.MatchScoreCalculationService.MORE;
import static com.gladkiei.tennisscoreboard.service.MatchScoreCalculationService.LESS;

public class DeuceScoreStrategy implements ScoreStrategy {
    private final MatchScoreModelDao matchScoreModelDao = new MatchScoreModelDao();
    private final MatchScoreCalculationService calcService;

    public DeuceScoreStrategy(MatchScoreCalculationService calcService) {
        this.calcService = calcService;
    }

    @Override
    public void execute(UUID uuid, Long winnerId) {
        MatchScoreModel matchScoreModel = matchScoreModelDao.getModel(uuid);
        PlayerScoreModel winner = calcService.getWinner(winnerId, matchScoreModel);
        PlayerScoreModel loser = calcService.getLoser(winnerId, matchScoreModel);

        winner.setPlayerScore(winner.getPlayerScore() + MORE);
        loser.setPlayerScore(loser.getPlayerScore() + LESS);

        if (winner.getPlayerScore() > MORE) {
            matchScoreModel.setState(IN_PROGRESS);
            calcService.updateGame(matchScoreModel, winnerId);
            if (calcService.isGameFinished(matchScoreModel, winner)) {
                calcService.updateSet(matchScoreModel, winnerId);
            }
        }
    }
}
