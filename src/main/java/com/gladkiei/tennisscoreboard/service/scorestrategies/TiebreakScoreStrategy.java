package com.gladkiei.tennisscoreboard.service.scorestrategies;

import com.gladkiei.tennisscoreboard.models.entity.MatchScoreModel;
import com.gladkiei.tennisscoreboard.models.entity.PlayerScoreModel;

import static com.gladkiei.tennisscoreboard.enums.MatchState.IN_PROGRESS;
import static com.gladkiei.tennisscoreboard.service.scorestrategies.MatchScoreCalculationService.ONE_POINT;

public class TiebreakScoreStrategy implements ScoreStrategy {
    private final MatchScoreCalculationService matchScoreCalculationService;

    public TiebreakScoreStrategy(MatchScoreCalculationService matchScoreCalculationService) {
        this.matchScoreCalculationService = matchScoreCalculationService;

    }

    @Override
    public void execute(MatchScoreModel matchScoreModel, Long winnerId) {
        PlayerScoreModel winner = matchScoreCalculationService.getWinner(winnerId, matchScoreModel);
        winner.setPlayerScore(winner.getPlayerScore() + ONE_POINT);

        if (matchScoreCalculationService.isTiebreakFinished(matchScoreModel)) {
            matchScoreModel.setState(IN_PROGRESS);
            matchScoreCalculationService.updateGame(matchScoreModel, winnerId);
            if (matchScoreCalculationService.isGameFinished(matchScoreModel, winner)) {
                matchScoreCalculationService.updateSet(matchScoreModel, winnerId);
            }
        }
    }
}
