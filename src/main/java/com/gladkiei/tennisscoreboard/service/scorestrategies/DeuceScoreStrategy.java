package com.gladkiei.tennisscoreboard.service.scorestrategies;

import com.gladkiei.tennisscoreboard.models.entity.MatchScoreModel;
import com.gladkiei.tennisscoreboard.models.entity.PlayerScoreModel;

import static com.gladkiei.tennisscoreboard.enums.MatchState.IN_PROGRESS;
import static com.gladkiei.tennisscoreboard.service.scorestrategies.MatchScoreCalculationService.LESS;
import static com.gladkiei.tennisscoreboard.service.scorestrategies.MatchScoreCalculationService.MORE;

public class DeuceScoreStrategy implements ScoreStrategy {
    private final MatchScoreCalculationService matchScoreCalculationService;

    public DeuceScoreStrategy(MatchScoreCalculationService matchScoreCalculationService) {
        this.matchScoreCalculationService = matchScoreCalculationService;
    }

    @Override
    public void execute(MatchScoreModel matchScoreModel, Long winnerId) {
        PlayerScoreModel winner = matchScoreCalculationService.getWinner(winnerId, matchScoreModel);
        PlayerScoreModel loser = matchScoreCalculationService.getLoser(winnerId, matchScoreModel);

        winner.setPlayerScore(winner.getPlayerScore() + MORE);
        loser.setPlayerScore(loser.getPlayerScore() + LESS);

        if (winner.getPlayerScore() > MORE) {
            matchScoreModel.setState(IN_PROGRESS);
            matchScoreCalculationService.updateGame(matchScoreModel, winnerId);
            if (matchScoreCalculationService.isGameFinished(matchScoreModel, winner)) {
                matchScoreCalculationService.updateSet(matchScoreModel, winnerId);
            }
        }
    }
}
