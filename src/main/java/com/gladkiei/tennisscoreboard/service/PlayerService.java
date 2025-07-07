package com.gladkiei.tennisscoreboard.service;

import com.gladkiei.tennisscoreboard.dao.PlayerDao;
import com.gladkiei.tennisscoreboard.dto.PlayerRequestDto;
import com.gladkiei.tennisscoreboard.dto.PlayerResponseDto;
import com.gladkiei.tennisscoreboard.exceptions.NotFoundException;
import com.gladkiei.tennisscoreboard.models.common.Player;

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
        Optional<Player> player = playerDao.getByName(playerRequestDto.getName());
        if (player.isPresent()) {
            return convertToDto(player.get());
        } else {
            throw new NotFoundException("Player not found");
        }
    }

    private boolean isPlayerAlreadyExist(PlayerRequestDto playerRequestDto) {
        Player player = convertToPlayer(playerRequestDto);
        Optional<Player> optional = playerDao.getByName(player.getName());
        return optional.isPresent();
    }
}
