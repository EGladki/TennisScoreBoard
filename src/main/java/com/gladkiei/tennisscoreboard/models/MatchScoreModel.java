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
    private Long player1Id;
    private Long player2Id;
    private int player1Score;
    private int player2Score;
    private int player1Game;
    private int player2Game;
    private int player1Set;
    private int player2Set;
    private boolean state;

    public MatchScoreModel(Long player1Id, Long player2Id, int player1Score, int player2Score, int player1Game, int player2Game, int player1Set, int player2Set, boolean isCompleted) {
        this.player1Id = player1Id;
        this.player2Id = player2Id;
        this.player1Score = player1Score;
        this.player2Score = player2Score;
        this.player1Game = player1Game;
        this.player2Game = player2Game;
        this.player1Set = player1Set;
        this.player2Set = player2Set;
        this.state = isCompleted;
    }
}
