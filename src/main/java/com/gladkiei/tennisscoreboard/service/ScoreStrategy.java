package com.gladkiei.tennisscoreboard.service;

import com.gladkiei.tennisscoreboard.models.MatchScoreModel;

import java.util.UUID;

public interface ScoreStrategy {
    void execute(MatchScoreModel matchScoreModel, Long winnerId);
}
