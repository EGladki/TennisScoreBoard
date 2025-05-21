package com.gladkiei.tennisscoreboard.service;

import com.gladkiei.tennisscoreboard.models.OngoingMatch;
import com.gladkiei.tennisscoreboard.models.Player;
import com.gladkiei.tennisscoreboard.dao.OngoingMatchDao;

import java.util.UUID;

public class OngoingMatchService {
    private static final int START_SCORE = 0;

    public OngoingMatch startMatch(Player player1, Player player2) {
        OngoingMatch ongoingMatch = createOngoingMatch(player1, player2);
        addToStorage(ongoingMatch);
        return ongoingMatch;
    }

    private OngoingMatch createOngoingMatch(Player player1, Player player2) {
        return new OngoingMatch(player1.getId(), player2.getId(), START_SCORE, START_SCORE);
    }

    private void addToStorage(OngoingMatch ongoingMatch) {
        UUID uuid = UUID.randomUUID();
        ongoingMatch.setUuid(uuid);
        OngoingMatchDao.getInstance().put(uuid, ongoingMatch);
    }

    private void updateScore(Player player) {

    }

    // Хранит текущие матчи и позволяет их записывать/читать
    // getMatch
}
