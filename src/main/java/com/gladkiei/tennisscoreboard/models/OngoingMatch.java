package com.gladkiei.tennisscoreboard.models;

import lombok.*;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class OngoingMatch {

    private UUID uuid;
    //    private Match match;
    private Long player1Id;
    private Long player2Id;
    private int player1Score;
    private int player2Score;

    public OngoingMatch(Long player1Id, Long player2Id, int player1Score, int player2Score) {
        this.player1Id = player1Id;
        this.player2Id = player2Id;
        this.player1Score = player1Score;
        this.player2Score = player2Score;
    }
}
