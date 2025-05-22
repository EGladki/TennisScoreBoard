package com.gladkiei.tennisscoreboard.service;

import com.gladkiei.tennisscoreboard.dao.OngoingMatchDao;
import com.gladkiei.tennisscoreboard.models.OngoingMatch;

import java.util.UUID;

import static com.gladkiei.tennisscoreboard.service.OngoingMatchService.START_GAME;
import static com.gladkiei.tennisscoreboard.service.OngoingMatchService.START_SCORE;

public class MatchScoreCalculationService {
    private final OngoingMatchDao ongoingMatchDao = new OngoingMatchDao();

    public void updateScore(UUID uuid, Long playerId) {
        OngoingMatch match = OngoingMatchDao.getInstance().getMatch(uuid);
        Long player1Id = match.getPlayer1Id();

        if (player1Id.equals(playerId)) {
            OngoingMatch updated = new OngoingMatch(uuid, match.getPlayer1Id(), match.getPlayer2Id(), match.getPlayer1Score() + 1, match.getPlayer2Score(), match.getPlayer1Game(), match.getPlayer2Game(), match.getPlayer1Set(), match.getPlayer2Set());
            ongoingMatchDao.refresh(uuid, updated);

            if (checkScore(updated.getPlayer1Score())) {
                updateGame(uuid, playerId);
                if (checkGames(updated.getPlayer1Game())) {
                    updateSet(uuid, playerId);
                }
            }
        }
        Long player2Id = match.getPlayer2Id();

        if (player2Id.equals(playerId)) {
            OngoingMatch updated = new OngoingMatch(uuid, match.getPlayer1Id(), match.getPlayer2Id(), match.getPlayer1Score(), match.getPlayer2Score() + 1, match.getPlayer1Game(), match.getPlayer2Game(), match.getPlayer1Set(), match.getPlayer2Set());
            ongoingMatchDao.refresh(uuid, updated);

            if (checkScore(updated.getPlayer1Score())) {
                updateGame(uuid, playerId);
                if (checkGames(updated.getPlayer1Game())) {
                    updateSet(uuid, playerId);
                }
            }
        }

    }

    public void updateGame(UUID uuid, Long playerId) {
        OngoingMatch match = OngoingMatchDao.getInstance().getMatch(uuid);
        Long player1Id = match.getPlayer1Id();
        Long player2Id = match.getPlayer2Id();

        if (player1Id.equals(playerId)) {
            OngoingMatch updated = new OngoingMatch(uuid,
                    match.getPlayer1Id(),
                    match.getPlayer2Id(),
                    START_SCORE,
                    START_SCORE,
                    match.getPlayer1Game() + 1,
                    match.getPlayer2Game(),
                    match.getPlayer1Set(),
                    match.getPlayer2Set());

            OngoingMatchDao.getInstance().remove(uuid);
            OngoingMatchDao.getInstance().put(uuid, updated);
        }
        if (player2Id.equals(playerId)) {
            OngoingMatch updated = new OngoingMatch(uuid,
                    match.getPlayer1Id(),
                    match.getPlayer2Id(),
                    START_SCORE,
                    START_SCORE,
                    match.getPlayer1Game(),
                    match.getPlayer2Game() + 1,
                    match.getPlayer1Set(),
                    match.getPlayer2Set());

            OngoingMatchDao.getInstance().remove(uuid);
            OngoingMatchDao.getInstance().put(uuid, updated);
        }
    }

    public void updateSet(UUID uuid, Long playerId) {
        OngoingMatch match = OngoingMatchDao.getInstance().getMatch(uuid);
        Long player1Id = match.getPlayer1Id();
        Long player2Id = match.getPlayer2Id();

        if (player1Id.equals(playerId)) {
            OngoingMatch updated = new OngoingMatch(uuid,
                    match.getPlayer1Id(),
                    match.getPlayer2Id(),
                    START_SCORE,
                    START_SCORE,
                    START_GAME,
                    START_GAME,
                    match.getPlayer1Set() + 1,
                    match.getPlayer2Set());

            OngoingMatchDao.getInstance().remove(uuid);
            OngoingMatchDao.getInstance().put(uuid, updated);
        }
        if (player2Id.equals(playerId)) {
            OngoingMatch updated = new OngoingMatch(uuid,
                    match.getPlayer1Id(),
                    match.getPlayer2Id(),
                    START_SCORE,
                    START_SCORE,
                    START_GAME,
                    START_GAME,
                    match.getPlayer1Set(),
                    match.getPlayer2Set() + 1);

            OngoingMatchDao.getInstance().remove(uuid);
            OngoingMatchDao.getInstance().put(uuid, updated);
        }
    }

    private boolean checkScore(int score) {
        return score >= 4;
    }

    private boolean checkGames(int games) {
        return games >= 6;
    }

}