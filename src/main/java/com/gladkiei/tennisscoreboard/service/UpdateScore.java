package com.gladkiei.tennisscoreboard.service;

import java.util.UUID;

public interface UpdateScore {
    void execute(UUID uuid, Long winnerId);
}
