package com.gladkiei.tennisscoreboard.service;

import com.gladkiei.tennisscoreboard.dao.MatchScoreModelDao;
import com.gladkiei.tennisscoreboard.dao.PlayerDao;
import com.gladkiei.tennisscoreboard.models.Match;
import com.gladkiei.tennisscoreboard.models.MatchScoreModel;
import com.gladkiei.tennisscoreboard.models.Player;

import java.util.UUID;

import static com.gladkiei.tennisscoreboard.service.OngoingMatchService.START_GAME;
import static com.gladkiei.tennisscoreboard.service.OngoingMatchService.START_SCORE;

public class MatchScoreCalculationService {
    private final static int MAX_SCORE = 40;
    private final static int MAX_GAMES = 6;
    private final static int MAX_SETS = 2;
    private final static int ZERO = 0;
    private final static int FIRST_ADDING_SCORE = 15;
    private final static int SECOND_ADDING_SCORE = 15;
    private final static int FINAL_ADDING_SCORE = 10;
    private final MatchScoreModelDao matchScoreModelDao = new MatchScoreModelDao();
    private final PlayerDao playerDao = new PlayerDao();
    private final FinishedMatchesPersistenceService finishedMatchesPersistenceService = new FinishedMatchesPersistenceService();

    public void updateScore(UUID uuid, Long playerId) {
        MatchScoreModel match = MatchScoreModelDao.getInstance().getModel(uuid);
        Long player1Id = match.getPlayer1Id();

        if (player1Id.equals(playerId)) {
            givePlayer1Score(player1Id, uuid, match);
        }

        Long player2Id = match.getPlayer2Id();

        if (player2Id.equals(playerId)) {
            givePlayer2Score(player2Id, uuid, match);
        }

    }

    private void givePlayer1Score(Long playerId, UUID uuid, MatchScoreModel match) {
        int score = calculateAddingScore(match.getPlayer1Score());
        MatchScoreModel updated = new MatchScoreModel(uuid, match.getPlayer1Id(), match.getPlayer2Id(), match.getPlayer1Score() + score, match.getPlayer2Score(), match.getPlayer1Game(), match.getPlayer2Game(), match.getPlayer1Set(), match.getPlayer2Set());
        matchScoreModelDao.refresh(uuid, updated);

        if (isMatchFinished(updated.getPlayer1Score())) {
            updateGame(uuid, playerId);
            if (isGameFinished(updated.getPlayer1Game())) {
                updateSet(uuid, playerId);
                if (isSetFinished(updated.getPlayer1Set())) {
                    Match finishedMatch = createFinishedMatch(uuid, playerId);
                    finishedMatchesPersistenceService.save(finishedMatch);
                }
            }
        }
    }

    private void givePlayer2Score(Long playerId, UUID uuid, MatchScoreModel match) {
        int score = calculateAddingScore(match.getPlayer2Score());
        MatchScoreModel updated = new MatchScoreModel(uuid, match.getPlayer1Id(), match.getPlayer2Id(), match.getPlayer1Score(), match.getPlayer2Score() + score, match.getPlayer1Game(), match.getPlayer2Game(), match.getPlayer1Set(), match.getPlayer2Set());
        matchScoreModelDao.refresh(uuid, updated);

        if (isMatchFinished(updated.getPlayer2Score())) {
            updateGame(uuid, playerId);
            if (isGameFinished(updated.getPlayer2Game())) {
                updateSet(uuid, playerId);
                if (isSetFinished(updated.getPlayer2Set())) {
                    Match finishedMatch = createFinishedMatch(uuid, playerId);
                    finishedMatchesPersistenceService.save(finishedMatch);

                }
            }
        }
    }

    private int calculateAddingScore(int score) {
        switch (score) {
            case ZERO -> {
                return FIRST_ADDING_SCORE;
            }
            case FIRST_ADDING_SCORE -> {
                return SECOND_ADDING_SCORE;
            }
            default -> {
                return FINAL_ADDING_SCORE;
            }
        }
    }

    public void updateGame(UUID uuid, Long playerId) {
        MatchScoreModel match = MatchScoreModelDao.getInstance().getModel(uuid);
        Long player1Id = match.getPlayer1Id();

        if (player1Id.equals(playerId)) {
            MatchScoreModel updated = new MatchScoreModel(uuid,
                    match.getPlayer1Id(),
                    match.getPlayer2Id(),
                    START_SCORE,
                    START_SCORE,
                    match.getPlayer1Game() + 1,
                    match.getPlayer2Game(),
                    match.getPlayer1Set(),
                    match.getPlayer2Set());

            MatchScoreModelDao.getInstance().remove(uuid);
            MatchScoreModelDao.getInstance().put(uuid, updated);
        }

        Long player2Id = match.getPlayer2Id();

        if (player2Id.equals(playerId)) {
            MatchScoreModel updated = new MatchScoreModel(uuid,
                    match.getPlayer1Id(),
                    match.getPlayer2Id(),
                    START_SCORE,
                    START_SCORE,
                    match.getPlayer1Game(),
                    match.getPlayer2Game() + 1,
                    match.getPlayer1Set(),
                    match.getPlayer2Set());

            MatchScoreModelDao.getInstance().remove(uuid);
            MatchScoreModelDao.getInstance().put(uuid, updated);
        }
    }

    public void updateSet(UUID uuid, Long playerId) {
        MatchScoreModel match = MatchScoreModelDao.getInstance().getModel(uuid);
        Long player1Id = match.getPlayer1Id();
        Long player2Id = match.getPlayer2Id();

        if (player1Id.equals(playerId)) {
            MatchScoreModel updated = new MatchScoreModel(uuid,
                    match.getPlayer1Id(),
                    match.getPlayer2Id(),
                    START_SCORE,
                    START_SCORE,
                    START_GAME,
                    START_GAME,
                    match.getPlayer1Set() + 1,
                    match.getPlayer2Set());

            MatchScoreModelDao.getInstance().remove(uuid);
            MatchScoreModelDao.getInstance().put(uuid, updated);
        }
        if (player2Id.equals(playerId)) {
            MatchScoreModel updated = new MatchScoreModel(uuid,
                    match.getPlayer1Id(),
                    match.getPlayer2Id(),
                    START_SCORE,
                    START_SCORE,
                    START_GAME,
                    START_GAME,
                    match.getPlayer1Set(),
                    match.getPlayer2Set() + 1);

            MatchScoreModelDao.getInstance().remove(uuid);
            MatchScoreModelDao.getInstance().put(uuid, updated);
        }
    }

    private boolean isMatchFinished(int score) {
        return score > MAX_SCORE;
    }

    private boolean isGameFinished(int games) {
        return games >= MAX_GAMES;
    }

    private boolean isSetFinished(int games) {
        return games == MAX_SETS;
    }

    private Match createFinishedMatch(UUID uuid, Long winnerId) {
        MatchScoreModel model = MatchScoreModelDao.getInstance().getModel(uuid);
        Player player1 = playerDao.findById(model.getPlayer1Id());
        Player player2 = playerDao.findById(model.getPlayer2Id());
        Player winner = playerDao.findById(winnerId);
        return new Match(player1, player2, winner);
    }

}