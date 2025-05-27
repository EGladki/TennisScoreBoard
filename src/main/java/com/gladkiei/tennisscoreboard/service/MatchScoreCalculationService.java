package com.gladkiei.tennisscoreboard.service;

import com.gladkiei.tennisscoreboard.dao.MatchScoreModelDao;
import com.gladkiei.tennisscoreboard.models.MatchScoreModel;
import com.gladkiei.tennisscoreboard.models.PlayerScoreModel;

import java.util.UUID;

import static com.gladkiei.tennisscoreboard.service.OngoingMatchService.*;

public class MatchScoreCalculationService {
    private final static int MAX_SCORE = 40;
    private final static int MAX_GAMES = 6;
    private final static int MAX_SETS = 1; // todo fix -> 2
    private final static int ZERO = 0;
    private final static int ONE_POINT = 1;
    private final static int MORE = 1;
    private final static int LESS = -1;
    private final static int FIRST_ADDING_SCORE = 15;
    private final static int SECOND_ADDING_SCORE = 15;
    private final static int FINAL_ADDING_SCORE = 10;
    private final MatchScoreModelDao matchScoreModelDao = new MatchScoreModelDao();

    public void updateScore(UUID uuid, Long winnerId) {
        MatchScoreModel matchScoreModel = matchScoreModelDao.getModel(uuid);
        if (isDeuce(uuid)) {

            matchScoreModel.setDeuce(DEUCE);
            matchScoreModel.getPlayer1ScoreModel().setPlayerScore(ZERO);
            matchScoreModel.getPlayer2ScoreModel().setPlayerScore(ZERO);
            givePlayerScoreDeuceRules(uuid, winnerId);
        } else {
            givePlayerScore(uuid, winnerId);
        }
    }

    private boolean isDeuce(UUID uuid) {
        MatchScoreModel matchScoreModel = matchScoreModelDao.getModel(uuid);
        int playerScore1 = matchScoreModel.getPlayer1ScoreModel().getPlayerScore();
        int playerScore2 = matchScoreModel.getPlayer2ScoreModel().getPlayerScore();
        return (playerScore1 == MAX_SCORE && playerScore2 == MAX_SCORE);
    }

    private void givePlayerScoreDeuceRules(UUID uuid, Long winnerId) {
        MatchScoreModel matchScoreModel = matchScoreModelDao.getModel(uuid);
        PlayerScoreModel winner = getWinner(winnerId, matchScoreModel);
        PlayerScoreModel loser = getLoser(winnerId, matchScoreModel);

        // FIX логика неправильная. Сделать счет 0-0, а затем добавлять
        winner.setPlayerScore(MORE);
        loser.setPlayerScore(LESS);

        if (winner.getPlayerScore() > MORE) {
            matchScoreModel.setDeuce(NOT_DEUCE);
            updateGame(uuid,winnerId);
        }
    }

    private void givePlayerScore(UUID uuid, Long winnerId) {
        MatchScoreModel matchScoreModel = matchScoreModelDao.getModel(uuid);
        PlayerScoreModel winner = getWinner(winnerId, matchScoreModel);

        int score = calculateAddingScore(winner.getPlayerScore());
        winner.setPlayerScore(winner.getPlayerScore() + score);

        if (isMatchFinished(winner.getPlayerScore())) {
            updateGame(uuid, winnerId);
            if (isGameFinished(winner.getPlayerGame())) {
                updateSet(uuid, winnerId);

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

    public void updateGame(UUID uuid, Long winnerId) {
        MatchScoreModel match = MatchScoreModelDao.getInstance().getModel(uuid);
        PlayerScoreModel winner = getWinner(winnerId, match);
        PlayerScoreModel loser = getLoser(winnerId, match);

        winner.setPlayerScore(START_SCORE);
        winner.setPlayerGame(winner.getPlayerGame() + ONE_POINT);

        loser.setPlayerScore(START_SCORE);
    }

    public void updateSet(UUID uuid, Long winnerId) {
        MatchScoreModel match = MatchScoreModelDao.getInstance().getModel(uuid);

        PlayerScoreModel winner = getWinner(winnerId, match);
        PlayerScoreModel loser = getLoser(winnerId, match);

        winner.setPlayerScore(START_SCORE);
        winner.setPlayerGame(START_GAME);
        winner.setPlayerSet(winner.getPlayerSet() + ONE_POINT);

        if (isSetFinished(winner.getPlayerSet())) {
            winner.setWinner(WINNER);
            match.setState(COMPLETED);
        }
        loser.setPlayerScore(START_SCORE);
        loser.setPlayerGame(START_GAME);
    }

    private PlayerScoreModel getWinner(Long winnerId, MatchScoreModel matchScoreModel) {
        if (winnerId.equals(matchScoreModel.getPlayer1ScoreModel().getPlayerId())) {
            return matchScoreModel.getPlayer1ScoreModel();
        } else {
            return matchScoreModel.getPlayer2ScoreModel();
        }
    }

    private PlayerScoreModel getLoser(Long winnerId, MatchScoreModel matchScoreModel) {
        if (!winnerId.equals(matchScoreModel.getPlayer1ScoreModel().getPlayerId())) {
            return matchScoreModel.getPlayer1ScoreModel();
        } else {
            return matchScoreModel.getPlayer2ScoreModel();
        }
    }

    private boolean isMatchFinished(int score) {
        return score > MAX_SCORE;
    }

    private boolean isGameFinished(int games) {
        return games >= MAX_GAMES;
    }

    private boolean isSetFinished(int sets) {
        return sets == MAX_SETS;
    }

}