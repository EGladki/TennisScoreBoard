package com.gladkiei.tennisscoreboard.service;

import com.gladkiei.tennisscoreboard.dao.MatchScoreModelDao;
import com.gladkiei.tennisscoreboard.models.MatchScoreModel;
import com.gladkiei.tennisscoreboard.models.PlayerScoreModel;

import java.util.UUID;

import static com.gladkiei.tennisscoreboard.enums.MatchState.IN_PROGRESS;
import static com.gladkiei.tennisscoreboard.service.MatchScoreCalculationService.ONE_POINT;

public class TiebreakScoreStrategy implements ScoreStrategy {
    private final MatchScoreModelDao matchScoreModelDao = new MatchScoreModelDao();
    private final MatchScoreCalculationService calcService;

    public TiebreakScoreStrategy(MatchScoreCalculationService calcService) {
        this.calcService = calcService;
    }

    @Override
    public void execute(UUID uuid, Long winnerId) {
        MatchScoreModel matchScoreModel = matchScoreModelDao.getModel(uuid);
        PlayerScoreModel winner = calcService.getWinner(winnerId, matchScoreModel);
        winner.setPlayerScore(winner.getPlayerScore() + ONE_POINT);

        if (calcService.isTiebreakFinished(matchScoreModel)) {
            matchScoreModel.setState(IN_PROGRESS);
            calcService.updateGame(matchScoreModel, winnerId);
            if (calcService.isGameFinished(matchScoreModel, winner)) {
                calcService.updateSet(matchScoreModel, winnerId);
            }
        }
    }
}
