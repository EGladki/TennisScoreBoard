package com.gladkiei.tennisscoreboard.service;

import com.gladkiei.tennisscoreboard.dao.MatchScoreModelDao;
import com.gladkiei.tennisscoreboard.enums.MatchState;
import com.gladkiei.tennisscoreboard.models.MatchScoreModel;
import com.gladkiei.tennisscoreboard.models.PlayerScoreModel;

import java.util.Map;
import java.util.UUID;

import static com.gladkiei.tennisscoreboard.enums.MatchState.*;
import static com.gladkiei.tennisscoreboard.enums.WinnerStatus.WINNER;
import static com.gladkiei.tennisscoreboard.service.OngoingMatchService.START_GAME;
import static com.gladkiei.tennisscoreboard.service.OngoingMatchService.START_SCORE;

public class MatchScoreCalculationService {
    private final static int ZERO = 0;
    final static int ONE_POINT = 1;
    private final static int FIFTEEN = 15;
    private final static int TEN = 10;
    private final static int MAX_SCORE = 40;
    private final static int MAX_GAMES = 6;
    private final static int MAX_SETS = 2;
    final static int MORE = 1;
    final static int LESS = -1;
    private final static int TIEBREAK_SCORE_TO_WIN = 7;
    private final static int DIFFERENCE_IN_SCORES_TO_WIN_TIEBREAK = 2;
    private final MatchScoreModelDao matchScoreModelDao = new MatchScoreModelDao();
    private final Map<MatchState, ScoreStrategy> strategyMap = Map.of(
            IN_PROGRESS, new CommonScoreStrategy(this),
            DEUCE, new DeuceScoreStrategy(this),
            TIEBREAK, new TiebreakScoreStrategy(this));


    public void updateScore(UUID uuid, Long winnerId) {
        MatchScoreModel matchScoreModel = matchScoreModelDao.getModel(uuid);
        ScoreStrategy scoreStrategy = strategyMap.get(matchScoreModel.getState());
        scoreStrategy.execute(uuid, winnerId);
    }

    void incrementScore(PlayerScoreModel winner) {
        int score = chooseAddingScore(winner.getPlayerScore());
        winner.setPlayerScore(winner.getPlayerScore() + score);
    }

    private int chooseAddingScore(int score) {
        switch (score) {
            case ZERO, FIFTEEN -> {
                return FIFTEEN;
            }
            default -> {
                return TEN;
            }
        }
    }

    void updateGame(MatchScoreModel matchScoreModel, Long winnerId) {
        PlayerScoreModel winner = getWinner(winnerId, matchScoreModel);

        winner.setPlayerGame(winner.getPlayerGame() + ONE_POINT);
        ifTiebreakUpdateStateAndResetScore(matchScoreModel);
        resetScore(matchScoreModel);
    }

    void updateSet(MatchScoreModel matchScoreModel, Long winnerId) {
        PlayerScoreModel winner = getWinner(winnerId, matchScoreModel);
        winner.setPlayerSet(winner.getPlayerSet() + ONE_POINT);

        if (!hasNeededExtraMatch(matchScoreModel) && isSetFinished(winner)) {
            winner.setWinnerStatus(WINNER);
            matchScoreModel.setState(COMPLETED);
        }
        resetScore(matchScoreModel);
        resetGames(matchScoreModel);
    }

    protected PlayerScoreModel getWinner(Long winnerId, MatchScoreModel matchScoreModel) {
        if (winnerId.equals(matchScoreModel.getPlayer1ScoreModel().getPlayerId())) {
            return matchScoreModel.getPlayer1ScoreModel();
        } else {
            return matchScoreModel.getPlayer2ScoreModel();
        }
    }

    PlayerScoreModel getLoser(Long winnerId, MatchScoreModel matchScoreModel) {
        if (!winnerId.equals(matchScoreModel.getPlayer1ScoreModel().getPlayerId())) {
            return matchScoreModel.getPlayer1ScoreModel();
        } else {
            return matchScoreModel.getPlayer2ScoreModel();
        }
    }

    private void resetScore(MatchScoreModel matchScoreModel) {
        matchScoreModel.getPlayer1ScoreModel().setPlayerScore(START_SCORE);
        matchScoreModel.getPlayer2ScoreModel().setPlayerScore(START_SCORE);
    }

    private void resetGames(MatchScoreModel matchScoreModel) {
        matchScoreModel.getPlayer1ScoreModel().setPlayerGame(START_GAME);
        matchScoreModel.getPlayer2ScoreModel().setPlayerGame(START_GAME);
    }

    void ifDeuceUpdateStateAndResetScore(MatchScoreModel matchScoreModel) {
        int playerScore1 = matchScoreModel.getPlayer1ScoreModel().getPlayerScore();
        int playerScore2 = matchScoreModel.getPlayer2ScoreModel().getPlayerScore();
        if (playerScore1 == MAX_SCORE && playerScore2 == MAX_SCORE) {
            matchScoreModel.setState(DEUCE);
            resetScore(matchScoreModel);
        }
    }

    private void ifTiebreakUpdateStateAndResetScore(MatchScoreModel matchScoreModel) {
        int player1Game = matchScoreModel.getPlayer1ScoreModel().getPlayerGame();
        int player2Game = matchScoreModel.getPlayer2ScoreModel().getPlayerGame();
        if (player1Game == MAX_GAMES && player2Game == MAX_GAMES) {
            matchScoreModel.setState(TIEBREAK);
            resetScore(matchScoreModel);
        }
    }

    boolean isMatchFinished(PlayerScoreModel winner) {
        return winner.getPlayerScore() > MAX_SCORE;
    }

    public boolean isGameFinished(MatchScoreModel matchScoreModel, PlayerScoreModel winner) {
        return winner.getPlayerGame() >= MAX_GAMES && !hasNeededExtraMatch(matchScoreModel) && matchScoreModel.getState() != TIEBREAK;
    }

    private boolean isSetFinished(PlayerScoreModel winner) {
        return winner.getPlayerSet() == MAX_SETS;
    }

    boolean isTiebreakFinished(MatchScoreModel matchScoreModel) {
        int player1Score = matchScoreModel.getPlayer1ScoreModel().getPlayerScore();
        int player2Score = matchScoreModel.getPlayer2ScoreModel().getPlayerScore();

        return (player1Score >= TIEBREAK_SCORE_TO_WIN && (player1Score - player2Score) >= DIFFERENCE_IN_SCORES_TO_WIN_TIEBREAK) ||
               (player2Score >= TIEBREAK_SCORE_TO_WIN && (player2Score - player1Score) >= DIFFERENCE_IN_SCORES_TO_WIN_TIEBREAK);
    }

    private boolean hasNeededExtraMatch(MatchScoreModel matchScoreModel) {
        int player1Game = matchScoreModel.getPlayer1ScoreModel().getPlayerGame();
        int player2Game = matchScoreModel.getPlayer2ScoreModel().getPlayerGame();
        return ((player1Game == 5 && player2Game == 6) || (player1Game == 6 && player2Game == 5));
    }

}