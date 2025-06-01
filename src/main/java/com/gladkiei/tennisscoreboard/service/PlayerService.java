package com.gladkiei.tennisscoreboard.service;

import com.gladkiei.tennisscoreboard.dao.PlayerDao;
import com.gladkiei.tennisscoreboard.dto.PlayerRequestDto;
import com.gladkiei.tennisscoreboard.dto.PlayerResponseDto;
import com.gladkiei.tennisscoreboard.models.Player;

import java.util.Optional;

import static com.gladkiei.tennisscoreboard.utils.MappingUtils.convertToDto;
import static com.gladkiei.tennisscoreboard.utils.MappingUtils.convertToPlayer;

public class PlayerService {
    private final PlayerDao playerDao = new PlayerDao();

    PlayerResponseDto getPlayerResponseDto(PlayerRequestDto playerRequestDto) {
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

    private boolean isPlayerAlreadyExist(PlayerRequestDto playerRequestDto) {
        Player player = convertToPlayer(playerRequestDto);
        Optional<Player> optional = playerDao.findByName(player.getName());
        return optional.isPresent();
    }
}
