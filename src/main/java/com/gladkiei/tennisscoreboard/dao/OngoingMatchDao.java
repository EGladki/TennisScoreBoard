package com.gladkiei.tennisscoreboard.dao;

import com.gladkiei.tennisscoreboard.models.OngoingMatch;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OngoingMatchDao {
    private static OngoingMatchDao instance;

    public static OngoingMatchDao getInstance() {
        if (instance == null) {
            return new OngoingMatchDao();
        }
        return instance;
    }

    private final static ConcurrentHashMap<UUID, OngoingMatch> matches = new ConcurrentHashMap<>();

    public void refresh(UUID uuid, OngoingMatch match) {
        OngoingMatchDao.getInstance().remove(uuid);
        OngoingMatchDao.getInstance().put(uuid, match);
    }

    public void put(UUID uuid, OngoingMatch match) {
        matches.put(uuid, match);
    }

    public OngoingMatch getMatch(UUID uuid) {
        return matches.get(uuid);
    }

    public void remove(UUID uuid) {
        matches.remove(uuid);
    }

}