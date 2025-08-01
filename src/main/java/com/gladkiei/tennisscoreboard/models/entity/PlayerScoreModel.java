package com.gladkiei.tennisscoreboard.models.entity;

import com.gladkiei.tennisscoreboard.enums.WinnerStatus;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PlayerScoreModel {
    private Long playerId;
    private int playerScore;
    private int playerGame;
    private int playerSet;
    private WinnerStatus winnerStatus;
}
