package com.gladkiei.tennisscoreboard.service;

import java.util.UUID;

public interface ScoreStrategy {
    void execute(UUID uuid, Long winnerId);
}
