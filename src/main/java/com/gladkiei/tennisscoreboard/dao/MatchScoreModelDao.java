package com.gladkiei.tennisscoreboard.dao;

import com.gladkiei.tennisscoreboard.models.MatchScoreModel;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MatchScoreModelDao {
    private static MatchScoreModelDao instance;

    public static MatchScoreModelDao getInstance() {
        if (instance == null) {
            return new MatchScoreModelDao();
        }
        return instance;
    }

    private final static ConcurrentHashMap<UUID, MatchScoreModel> ongoingMatches = new ConcurrentHashMap<>();

    public void put(UUID uuid, MatchScoreModel model) {
        ongoingMatches.put(uuid, model);
    }

    public MatchScoreModel getModel(UUID uuid) {
        return ongoingMatches.get(uuid);
    }

    public void removeModel(UUID uuid) {
        ongoingMatches.remove(uuid);
    }

}