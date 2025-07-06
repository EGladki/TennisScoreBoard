package com.gladkiei.tennisscoreboard.service;

import com.gladkiei.tennisscoreboard.dao.MatchScoreModelDao;
import com.gladkiei.tennisscoreboard.dto.PlayerRequestDto;
import com.gladkiei.tennisscoreboard.dto.PlayerResponseDto;
import com.gladkiei.tennisscoreboard.dto.MatchScoreModel;
import com.gladkiei.tennisscoreboard.dto.PlayerScoreModel;

import java.util.UUID;

import static com.gladkiei.tennisscoreboard.enums.MatchState.IN_PROGRESS;
import static com.gladkiei.tennisscoreboard.enums.WinnerStatus.NOT_WINNER;

public class OngoingMatchService {

    public static final int START_SCORE = 0;
    public static final int START_GAME = 0;
    public static final int START_SET = 0;
    private final PlayerService playerService = new PlayerService();

    public MatchScoreModel startAndReturnMatch(PlayerRequestDto player1RequestDto, PlayerRequestDto player2RequestDto) {
        PlayerResponseDto player1ResponseDto = playerService.getPlayerResponseDto(player1RequestDto);
        PlayerResponseDto player2ResponseDto = playerService.getPlayerResponseDto(player2RequestDto);
        MatchScoreModel matchScoreModel = createOngoingMatch(player1ResponseDto, player2ResponseDto);
        addToStorage(matchScoreModel);
        return matchScoreModel;
    }

    private MatchScoreModel createOngoingMatch(PlayerResponseDto player1ResponseDto, PlayerResponseDto player2ResponseDto) {
        PlayerScoreModel model1 = new PlayerScoreModel(player1ResponseDto.getId(), START_SCORE, START_GAME, START_SET, NOT_WINNER);
        PlayerScoreModel model2 = new PlayerScoreModel(player2ResponseDto.getId(), START_SCORE, START_GAME, START_SET, NOT_WINNER);
        return new MatchScoreModel(model1, model2, IN_PROGRESS);
    }

    private void addToStorage(MatchScoreModel matchScoreModel) {
        UUID uuid = UUID.randomUUID();
        matchScoreModel.setUuid(uuid);
        MatchScoreModelDao.getInstance().put(uuid, matchScoreModel);
    }
}
