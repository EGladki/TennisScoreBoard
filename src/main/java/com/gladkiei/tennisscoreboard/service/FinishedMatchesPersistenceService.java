package com.gladkiei.tennisscoreboard.service;

import com.gladkiei.tennisscoreboard.dao.MatchDao;
import com.gladkiei.tennisscoreboard.models.Match;

public class FinishedMatchesPersistenceService {
    private final MatchDao matchDao = new MatchDao();

    protected void save(Match match) {
        matchDao.save(match);
    }

    public Long getFinishedMatchId(Match match) {
        return match.getId();
    }
    // инкапсуляция готовых матчей в БД
}
