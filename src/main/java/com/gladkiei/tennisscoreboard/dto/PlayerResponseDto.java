package com.gladkiei.tennisscoreboard.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class PlayerResponseDto {
    private Long id;
    private String name;
}
