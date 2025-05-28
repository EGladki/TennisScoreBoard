package com.gladkiei.tennisscoreboard.service;

import com.gladkiei.tennisscoreboard.dao.MatchScoreModelDao;
import com.gladkiei.tennisscoreboard.models.MatchScoreModel;
import com.gladkiei.tennisscoreboard.models.PlayerScoreModel;

import java.util.UUID;

import static com.gladkiei.tennisscoreboard.service.OngoingMatchService.*;

public class MatchScoreCalculationService {
    private final static int MAX_SCORE = 40;
    private final static int MAX_GAMES = 6; // todo fix -> 6
    private final static int MAX_SETS = 2; // todo fix -> 2
    private final static int TIEBREAK_SCORE_TO_WIN = 7;
    private final static int DIFFERENCE_IN_SCORES_TO_WIN_TIEBREAK = 2;
    private final static int ZERO = 0;
    private final static int ONE_POINT = 1;
    private final static int MORE = 1;
    private final static int LESS = -1;
    private final static int FIRST_ADDING_SCORE = 15;
    private final static int SECOND_ADDING_SCORE = 15;
    private final static int FINAL_ADDING_SCORE = 10;
    private final MatchScoreModelDao matchScoreModelDao = new MatchScoreModelDao();


    public void updateScore(UUID uuid, Long winnerId) {
        if (isTieBreak(uuid)) {
            playTiebreak(uuid, winnerId);
        } else if (isDeuce(uuid)) {
            givePlayerScoreDeuceRules(uuid, winnerId);
        } else {
            givePlayerScore(uuid, winnerId);
        }
    }

    private void playTiebreak(UUID uuid, Long winnerId) {
        MatchScoreModel matchScoreModel = matchScoreModelDao.getModel(uuid);
        PlayerScoreModel winner = getWinner(winnerId, matchScoreModel);
        PlayerScoreModel loser = getLoser(winnerId, matchScoreModel);

        winner.setPlayerScore(winner.getPlayerScore() + ONE_POINT);

        if (isTiebreakFinished(uuid)) {
            matchScoreModel.setTieBreak(NOT_TIEBREAK);


            updateGame(uuid, winnerId);
            if (isGameFinished(uuid, winner.getPlayerGame())) {
                updateSet(uuid, winnerId);
            }
        }

    }

    private boolean isDeuce(UUID uuid) {
        MatchScoreModel matchScoreModel = matchScoreModelDao.getModel(uuid);
        if (matchScoreModel.isDeuce()) {
            return true;
        }
        int playerScore1 = matchScoreModel.getPlayer1ScoreModel().getPlayerScore();
        int playerScore2 = matchScoreModel.getPlayer2ScoreModel().getPlayerScore();
        if (playerScore1 == MAX_SCORE && playerScore2 == MAX_SCORE) {
            matchScoreModel.setDeuce(DEUCE);
            resetScore(uuid);
            return true;
        } else {
            return false;
        }
    }

    private void resetScore(UUID uuid) {
        MatchScoreModel matchScoreModel = matchScoreModelDao.getModel(uuid);
        matchScoreModel.getPlayer1ScoreModel().setPlayerScore(ZERO);
        matchScoreModel.getPlayer2ScoreModel().setPlayerScore(ZERO);
    }

    private void givePlayerScoreDeuceRules(UUID uuid, Long winnerId) {
        MatchScoreModel matchScoreModel = matchScoreModelDao.getModel(uuid);
        PlayerScoreModel winner = getWinner(winnerId, matchScoreModel);
        PlayerScoreModel loser = getLoser(winnerId, matchScoreModel);

        winner.setPlayerScore(winner.getPlayerScore() + MORE);
        loser.setPlayerScore(loser.getPlayerScore() + LESS);

        if (winner.getPlayerScore() > MORE) {
            matchScoreModel.setDeuce(NOT_DEUCE);
            updateGame(uuid, winnerId);
            if (isGameFinished(uuid, winner.getPlayerGame())) {
                updateSet(uuid, winnerId);
            }
        }
    }

    private void givePlayerScore(UUID uuid, Long winnerId) {
        MatchScoreModel matchScoreModel = matchScoreModelDao.getModel(uuid);
        PlayerScoreModel winner = getWinner(winnerId, matchScoreModel);

        int score = chooseAddingScore(winner.getPlayerScore());
        winner.setPlayerScore(winner.getPlayerScore() + score);

        if (isMatchFinished(winner.getPlayerScore())) {
            updateGame(uuid, winnerId);
            if (isGameFinished(uuid, winner.getPlayerGame())) {
                updateSet(uuid, winnerId);
            }
        }
    }

    private int chooseAddingScore(int score) {
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

        winner.setPlayerSet(winner.getPlayerSet() + ONE_POINT);

        if (!hasNeededExtraMatch(uuid) && isSetFinished(uuid, winner.getPlayerSet())) {
            winner.setWinner(WINNER);
            match.setState(COMPLETED);
        }

        winner.setPlayerScore(START_SCORE);
        winner.setPlayerGame(START_GAME);
        loser.setPlayerScore(START_SCORE);
        loser.setPlayerGame(START_GAME);
    }

    private boolean isTieBreak(UUID uuid) {
        MatchScoreModel matchScoreModel = matchScoreModelDao.getModel(uuid);
        if (matchScoreModel.isTieBreak()) {
            return true;
        }
        int player1Game = matchScoreModel.getPlayer1ScoreModel().getPlayerGame();
        int player2Game = matchScoreModel.getPlayer2ScoreModel().getPlayerGame();
        if (player1Game == MAX_GAMES && player2Game == MAX_GAMES) {
            matchScoreModel.setTieBreak(TIEBREAK);
            resetScore(uuid);
            return true;
        } else {
            return false;
        }
    }

    private boolean hasNeededExtraMatch(UUID uuid) {
        MatchScoreModel matchScoreModel = matchScoreModelDao.getModel(uuid);
        int player1Game = matchScoreModel.getPlayer1ScoreModel().getPlayerGame();
        int player2Game = matchScoreModel.getPlayer2ScoreModel().getPlayerGame();
        return ((player1Game == 5 && player2Game == 6) || (player1Game == 6 && player2Game == 5));
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

    private boolean isGameFinished(UUID uuid, int games) {
        return games >= MAX_GAMES && !hasNeededExtraMatch(uuid) && !isTieBreak(uuid);
    }

    private boolean isSetFinished(UUID uuid, int sets) {
        return sets == MAX_SETS;
    }

    private boolean isTiebreakFinished(UUID uuid) {
        MatchScoreModel matchScoreModel = matchScoreModelDao.getModel(uuid);
        int player1Score = matchScoreModel.getPlayer1ScoreModel().getPlayerScore();
        int player2Score = matchScoreModel.getPlayer2ScoreModel().getPlayerScore();

        return (player1Score >= TIEBREAK_SCORE_TO_WIN && (player1Score - player2Score) >= DIFFERENCE_IN_SCORES_TO_WIN_TIEBREAK) ||
               (player2Score >= TIEBREAK_SCORE_TO_WIN && (player2Score - player1Score) >= DIFFERENCE_IN_SCORES_TO_WIN_TIEBREAK);
    }

}