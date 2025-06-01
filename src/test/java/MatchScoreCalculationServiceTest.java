import com.gladkiei.tennisscoreboard.dao.MatchScoreModelDao;
import com.gladkiei.tennisscoreboard.enums.MatchState;
import com.gladkiei.tennisscoreboard.models.MatchScoreModel;
import com.gladkiei.tennisscoreboard.models.PlayerScoreModel;
import com.gladkiei.tennisscoreboard.service.MatchScoreCalculationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.gladkiei.tennisscoreboard.enums.MatchState.DEUCE;
import static com.gladkiei.tennisscoreboard.enums.MatchState.TIEBREAK;
import static com.gladkiei.tennisscoreboard.enums.WinnerStatus.NOT_WINNER;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatchScoreCalculationServiceTest {

    MatchScoreCalculationService service;
    MatchScoreModelDao dao;
    UUID matchID;
    PlayerScoreModel player1;
    PlayerScoreModel player2;
    MatchScoreModel model;

    @BeforeEach
    void setUp() {
        service = new MatchScoreCalculationService();
        dao = MatchScoreModelDao.getInstance();
        matchID = UUID.randomUUID();
        player1 = new PlayerScoreModel(1L, 0, 0, 0, NOT_WINNER);
        player2 = new PlayerScoreModel(2L, 0, 0, 0, NOT_WINNER);
        model = new MatchScoreModel(matchID, player1, player2, MatchState.IN_PROGRESS);
        dao.put(matchID, model);
    }

    @AfterEach
    void tearDown() {
        dao.remove(matchID);
    }

    @Test
    void playerScoresIncrementToGameWinAfterFourPoints() {
        for (int i = 0; i < 4; i++) {
            service.updateScore(matchID, player1.getPlayerId());
        }

        MatchScoreModel updated = dao.getModel(matchID);

        assertEquals(1, updated.getPlayer1ScoreModel().getPlayerGame());
        assertEquals(0, updated.getPlayer1ScoreModel().getPlayerScore());
        assertEquals(0, updated.getPlayer1ScoreModel().getPlayerSet());
        assertEquals(NOT_WINNER, updated.getPlayer1ScoreModel().getWinnerStatus());

        assertEquals(0, updated.getPlayer2ScoreModel().getPlayerGame());
        assertEquals(0, updated.getPlayer2ScoreModel().getPlayerScore());
        assertEquals(0, updated.getPlayer2ScoreModel().getPlayerSet());
        assertEquals(NOT_WINNER, updated.getPlayer2ScoreModel().getWinnerStatus());
    }

    @Test
    void playersScoreShouldBeFortyThirtyAfterIncrement() {
        service.updateScore(matchID, player1.getPlayerId());
        service.updateScore(matchID, player2.getPlayerId());
        MatchScoreModel updated = dao.getModel(matchID);
        assertEquals(15, updated.getPlayer1ScoreModel().getPlayerScore());
        assertEquals(15, updated.getPlayer2ScoreModel().getPlayerScore());

        service.updateScore(matchID, player1.getPlayerId());
        service.updateScore(matchID, player2.getPlayerId());
        updated = dao.getModel(matchID);

        assertEquals(30, updated.getPlayer1ScoreModel().getPlayerScore());
        assertEquals(30, updated.getPlayer2ScoreModel().getPlayerScore());

        service.updateScore(matchID, player1.getPlayerId());
        updated = dao.getModel(matchID);

        assertEquals(40, updated.getPlayer1ScoreModel().getPlayerScore());
        assertEquals(30, updated.getPlayer2ScoreModel().getPlayerScore());

    }

    @Test
    void playersScoreShouldBeZeroZeroIncrement() {
        service.updateScore(matchID, player1.getPlayerId());
        service.updateScore(matchID, player2.getPlayerId());
        MatchScoreModel updated = dao.getModel(matchID);
        assertEquals(15, updated.getPlayer1ScoreModel().getPlayerScore());
        assertEquals(15, updated.getPlayer2ScoreModel().getPlayerScore());

        service.updateScore(matchID, player1.getPlayerId());
        service.updateScore(matchID, player2.getPlayerId());
        updated = dao.getModel(matchID);

        assertEquals(30, updated.getPlayer1ScoreModel().getPlayerScore());
        assertEquals(30, updated.getPlayer2ScoreModel().getPlayerScore());

        service.updateScore(matchID, player1.getPlayerId());
        service.updateScore(matchID, player2.getPlayerId());

        updated = dao.getModel(matchID);

        assertEquals(0, updated.getPlayer1ScoreModel().getPlayerScore());
        assertEquals(0, updated.getPlayer2ScoreModel().getPlayerScore());
    }

    @Test
    void matchStateShouldBeDeuce() {
        for (int i = 0; i < 3; i++) {
            service.updateScore(matchID, player1.getPlayerId());
            service.updateScore(matchID, player2.getPlayerId());
        }
        MatchScoreModel updated = dao.getModel(matchID);
        assertEquals(DEUCE, updated.getState());

    }

//    @Test
//    void matchStateShouldBeTiebreak() {
//        for (int i = 0; i < 6; i++) {
//            service.updateGame(model, player1.getPlayerId());
//            service.updateGame(model, player2.getPlayerId());
//        }
//        MatchScoreModel updated = dao.getModel(matchID);
//        assertEquals(TIEBREAK, updated.getState());
//    }
//
//    @Test
//    void afterIncrementShouldUpdateSet() {
//            service.updateSet(model, player1.getPlayerId());
//            service.updateSet(model, player2.getPlayerId());
//
//        MatchScoreModel updated = dao.getModel(matchID);
//        assertEquals(TIEBREAK, updated.getState());
//    }

//    @Test
//    void shouldIncrementGameAndResetScoreWhenPlayerWinsFourPoints() {
//        service.updateScore(matchID, player1.getPlayerId());
//        service.updateGame(model, player1.getPlayerId());
//
//        MatchScoreModel updated = dao.getModel(matchID);
//
//        assertEquals(0, updated.getPlayer1ScoreModel().getPlayerScore());
//        assertEquals(1, updated.getPlayer1ScoreModel().getPlayerGame());
//        assertEquals(0, updated.getPlayer1ScoreModel().getPlayerSet());
//        assertEquals(NOT_WINNER, updated.getPlayer1ScoreModel().getWinnerStatus());
//
//        assertEquals(0, updated.getPlayer2ScoreModel().getPlayerGame());
//        assertEquals(0, updated.getPlayer2ScoreModel().getPlayerScore());
//        assertEquals(0, updated.getPlayer2ScoreModel().getPlayerSet());
//        assertEquals(NOT_WINNER, updated.getPlayer2ScoreModel().getWinnerStatus());
//    }

    @Test
    void test() {

    }

}
