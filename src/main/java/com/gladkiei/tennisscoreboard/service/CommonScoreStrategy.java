package com.gladkiei.tennisscoreboard.service;

import com.gladkiei.tennisscoreboard.dao.MatchScoreModelDao;
import com.gladkiei.tennisscoreboard.models.MatchScoreModel;
import com.gladkiei.tennisscoreboard.models.PlayerScoreModel;

import java.util.UUID;

public class CommonScoreStrategy implements ScoreStrategy {

    private final MatchScoreModelDao matchScoreModelDao = new MatchScoreModelDao();
    private final MatchScoreCalculationService calcService;

    public CommonScoreStrategy(MatchScoreCalculationService calcService) {
        this.calcService = calcService;
    }

    @Override
    public void execute(UUID uuid, Long winnerId) {
        MatchScoreModel matchScoreModel = matchScoreModelDao.getModel(uuid);
        PlayerScoreModel winner = calcService.getWinner(winnerId, matchScoreModel);

        calcService.incrementScore(winner);
        calcService.ifDeuceUpdateStateAndResetScore(matchScoreModel);

        if (calcService.isMatchFinished(winner)) {
            calcService.updateGame(matchScoreModel, winnerId);
            if (calcService.isGameFinished(matchScoreModel, winner)) {
                calcService.updateSet(matchScoreModel, winnerId);
            }
        }
    }
}
