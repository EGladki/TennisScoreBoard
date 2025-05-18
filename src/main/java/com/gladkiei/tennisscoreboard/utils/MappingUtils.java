package com.gladkiei.tennisscoreboard.utils;

import com.gladkiei.tennisscoreboard.dto.PlayerRequestDto;
import com.gladkiei.tennisscoreboard.dto.PlayerResponseDto;
import com.gladkiei.tennisscoreboard.models.Player;
import org.modelmapper.ModelMapper;

public class MappingUtils {
    private static final ModelMapper MODEL_MAPPER = new ModelMapper();

    public static Player convertToPlayer(PlayerRequestDto dto) {
        return MODEL_MAPPER.map(dto, Player.class);
    }

    public static PlayerResponseDto convertToDto(Player player) {
        return MODEL_MAPPER.map(player, PlayerResponseDto.class);
    }

}
