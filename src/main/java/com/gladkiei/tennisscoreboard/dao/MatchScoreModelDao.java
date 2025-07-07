package com.gladkiei.tennisscoreboard.dao;

import com.gladkiei.tennisscoreboard.models.entity.MatchScoreModel;
import com.gladkiei.tennisscoreboard.exceptions.NotFoundException;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MatchScoreModelDao {
    private static final ConcurrentHashMap<UUID, MatchScoreModel> ongoingMatches = new ConcurrentHashMap<>();
    private static MatchScoreModelDao instance;

    public static MatchScoreModelDao getInstance() {
        if (instance == null) {
            instance = new MatchScoreModelDao();
        }
        return instance;
    }

    public MatchScoreModel getModel(UUID uuid) {
        if (ongoingMatches.get(uuid) == null) {
            throw new NotFoundException("Match not found");
        }
        return ongoingMatches.get(uuid);
    }

    public void put(UUID uuid, MatchScoreModel model) {
        ongoingMatches.put(uuid, model);
    }

    public void remove(UUID uuid) {
        ongoingMatches.remove(uuid);
    }

}