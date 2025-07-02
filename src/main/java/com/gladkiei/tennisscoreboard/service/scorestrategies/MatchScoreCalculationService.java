package com.gladkiei.tennisscoreboard.service.scorestrategies;

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
    public final static int FIRST_ADDING_SCORE = 15;
    public final static int SECOND_ADDING_SCORE = 15;
    public final static int THIRD_ADDING_SCORE = 10;
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
        synchronized (matchScoreModel) {
            strategyMap.get(matchScoreModel.getState()).execute(matchScoreModel, winnerId);
        }
    }

    void incrementScore(PlayerScoreModel winner) {
        int score = chooseAddingScore(winner.getPlayerScore());
        winner.setPlayerScore(winner.getPlayerScore() + score);
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
                return THIRD_ADDING_SCORE;
            }
        }
    }

    public void updateGame(MatchScoreModel matchScoreModel, Long winnerId) {
        PlayerScoreModel winner = getWinner(winnerId, matchScoreModel);

        winner.setPlayerGame(winner.getPlayerGame() + ONE_POINT);
        ifTiebreakUpdateStateAndResetScore(matchScoreModel);
        resetScore(matchScoreModel);
    }

    public void updateSet(MatchScoreModel matchScoreModel, Long winnerId) {
        PlayerScoreModel winner = getWinner(winnerId, matchScoreModel);
        winner.setPlayerSet(winner.getPlayerSet() + ONE_POINT);

        if (!needsExtraGameBeforeWin(matchScoreModel) && isSetFinished(winner)) {
            winner.setWinnerStatus(WINNER);
            matchScoreModel.setState(COMPLETED);
        }
        resetScore(matchScoreModel);
        resetGames(matchScoreModel);
    }

    PlayerScoreModel getWinner(Long winnerId, MatchScoreModel matchScoreModel) {
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

    boolean isGameFinished(MatchScoreModel matchScoreModel, PlayerScoreModel winner) {
        return winner.getPlayerGame() >= MAX_GAMES && !needsExtraGameBeforeWin(matchScoreModel) && matchScoreModel.getState() != TIEBREAK;
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

    private boolean needsExtraGameBeforeWin(MatchScoreModel matchScoreModel) {
        int player1Game = matchScoreModel.getPlayer1ScoreModel().getPlayerGame();
        int player2Game = matchScoreModel.getPlayer2ScoreModel().getPlayerGame();
        return ((player1Game == MAX_GAMES - 1 && player2Game == MAX_GAMES) || (player1Game == MAX_GAMES && player2Game == MAX_GAMES - 1));
    }

}