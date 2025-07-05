package com.gladkiei.tennisscoreboard.dto;

import com.gladkiei.tennisscoreboard.enums.MatchState;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MatchScoreModel {
    private UUID uuid;
    private PlayerScoreModel player1ScoreModel;
    private PlayerScoreModel player2ScoreModel;
    private MatchState state;

    public MatchScoreModel(PlayerScoreModel player1ScoreModel, PlayerScoreModel player2ScoreModel, MatchState state) {
        this.player1ScoreModel = player1ScoreModel;
        this.player2ScoreModel = player2ScoreModel;
        this.state = state;
    }
}
