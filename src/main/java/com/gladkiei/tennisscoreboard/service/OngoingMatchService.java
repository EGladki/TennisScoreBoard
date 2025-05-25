package com.gladkiei.tennisscoreboard.service;

import com.gladkiei.tennisscoreboard.dao.MatchScoreModelDao;
import com.gladkiei.tennisscoreboard.models.MatchScoreModel;
import com.gladkiei.tennisscoreboard.models.Player;
import com.gladkiei.tennisscoreboard.models.PlayerScoreModel;

import java.util.UUID;

public class OngoingMatchService {
    static final int START_SCORE = 0;
    static final int START_GAME = 0;
    static final int START_SET = 0;
    static final boolean NOT_COMPLETED = false;
    static final boolean COMPLETED = true;

    public MatchScoreModel startMatch(Player player1, Player player2) {
        MatchScoreModel matchScoreModel = createOngoingMatch(player1, player2);
        addToStorage(matchScoreModel);
        return matchScoreModel;
    }

    private MatchScoreModel createOngoingMatch(Player player1, Player player2) {
        PlayerScoreModel model1 = new PlayerScoreModel(player1.getId(), START_SCORE, START_GAME, START_SET);
        PlayerScoreModel model2 = new PlayerScoreModel(player2.getId(), START_SCORE, START_GAME, START_SET);
        return new MatchScoreModel(model1, model2, NOT_COMPLETED);
    }

    private void addToStorage(MatchScoreModel matchScoreModel) {
        UUID uuid = UUID.randomUUID();
        matchScoreModel.setUuid(uuid);
        MatchScoreModelDao.getInstance().put(uuid, matchScoreModel);
    }


}
