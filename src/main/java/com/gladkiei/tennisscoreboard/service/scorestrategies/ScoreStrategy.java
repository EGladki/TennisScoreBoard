package com.gladkiei.tennisscoreboard.service.scorestrategies;

import com.gladkiei.tennisscoreboard.models.entity.MatchScoreModel;

public interface ScoreStrategy {
    void execute(MatchScoreModel matchScoreModel, Long winnerId);
}
