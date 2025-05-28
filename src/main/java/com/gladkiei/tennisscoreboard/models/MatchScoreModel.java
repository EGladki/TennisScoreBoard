package com.gladkiei.tennisscoreboard.models;

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
    private boolean deuce;
    private boolean tieBreak;
    private boolean state;

    public MatchScoreModel(PlayerScoreModel player1ScoreModel, PlayerScoreModel player2ScoreModel, boolean deuce, boolean tieBreak, boolean state) {
        this.player1ScoreModel = player1ScoreModel;
        this.player2ScoreModel = player2ScoreModel;
        this.deuce = deuce;
        this.tieBreak = tieBreak;
        this.state = state;
    }
}
