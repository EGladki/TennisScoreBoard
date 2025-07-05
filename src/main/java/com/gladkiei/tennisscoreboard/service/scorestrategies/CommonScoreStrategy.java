package com.gladkiei.tennisscoreboard.service.scorestrategies;

import com.gladkiei.tennisscoreboard.dto.MatchScoreModel;
import com.gladkiei.tennisscoreboard.dto.PlayerScoreModel;

public class CommonScoreStrategy implements ScoreStrategy {

    private final MatchScoreCalculationService matchScoreCalculationService;

    public CommonScoreStrategy(MatchScoreCalculationService matchScoreCalculationService) {
        this.matchScoreCalculationService = matchScoreCalculationService;
    }

    @Override
    public void execute(MatchScoreModel matchScoreModel, Long winnerId) {
        PlayerScoreModel winner = matchScoreCalculationService.getWinner(winnerId, matchScoreModel);

        matchScoreCalculationService.incrementScore(winner);
        matchScoreCalculationService.ifDeuceUpdateStateAndResetScore(matchScoreModel);

        if (matchScoreCalculationService.isMatchFinished(winner)) {
            matchScoreCalculationService.updateGame(matchScoreModel, winnerId);
            if (matchScoreCalculationService.isGameFinished(matchScoreModel, winner)) {
                matchScoreCalculationService.updateSet(matchScoreModel, winnerId);
            }
        }
    }
}
