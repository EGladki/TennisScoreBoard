package com.gladkiei.tennisscoreboard.service;

import com.gladkiei.tennisscoreboard.dao.MatchScoreModelDao;
import com.gladkiei.tennisscoreboard.dao.PlayerDao;
import com.gladkiei.tennisscoreboard.dto.PlayerRequestDto;
import com.gladkiei.tennisscoreboard.dto.PlayerResponseDto;
import com.gladkiei.tennisscoreboard.models.MatchScoreModel;
import com.gladkiei.tennisscoreboard.models.Player;
import com.gladkiei.tennisscoreboard.models.PlayerScoreModel;

import java.util.Optional;
import java.util.UUID;

import static com.gladkiei.tennisscoreboard.enums.MatchState.IN_PROGRESS;
import static com.gladkiei.tennisscoreboard.enums.WinnerStatus.NOT_WINNER;
import static com.gladkiei.tennisscoreboard.utils.MappingUtils.convertToDto;
import static com.gladkiei.tennisscoreboard.utils.MappingUtils.convertToPlayer;

public class OngoingMatchService {

    static final int START_SCORE = 0;
    static final int START_GAME = 0;
    static final int START_SET = 0;
    private final PlayerDao playerDao = new PlayerDao();

    public MatchScoreModel startMatch(PlayerRequestDto player1RequestDto, PlayerRequestDto player2RequestDto) {
        PlayerResponseDto player1ResponseDto = getPlayerResponseDto(player1RequestDto);
        PlayerResponseDto player2ResponseDto = getPlayerResponseDto(player2RequestDto);
        MatchScoreModel matchScoreModel = createOngoingMatch(player1ResponseDto, player2ResponseDto);
        addToStorage(matchScoreModel);
        return matchScoreModel;
    }

    private PlayerResponseDto getPlayerResponseDto(PlayerRequestDto playerRequestDto) {
        if (isPlayerAlreadyExist(playerRequestDto)) {
            return getPlayerResponseDtoFromDao(playerRequestDto);
        } else {
            playerDao.save(convertToPlayer(playerRequestDto));
            return getPlayerResponseDtoFromDao(playerRequestDto);
        }
    }

    private PlayerResponseDto getPlayerResponseDtoFromDao(PlayerRequestDto playerRequestDto) {
        Optional<Player> player = playerDao.findByName(playerRequestDto.getName());
        return convertToDto(player.get());
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

    private boolean isPlayerAlreadyExist(PlayerRequestDto playerRequestDto) {
        Player player = convertToPlayer(playerRequestDto);
        Optional<Player> optional = playerDao.findByName(player.getName());
        return optional.isPresent();
    }
}
