package com.gladkiei.tennisscoreboard.utils;

import com.gladkiei.tennisscoreboard.exceptions.BadRequestException;

public class NameValidationUtils {
    public void validate(String name) {
        if (isNullOrBlank(name)) {
            throw new BadRequestException("Missing parameter - player name");
        }
        if (isTooLarge(name)) {
            throw new BadRequestException("Name must be less than 30 letters");
        }
        if (!isValidNameFormat(name)) {
            throw new BadRequestException("Wrong format. Name must be latin letters only");
        }
    }

    public void validateNameEquality(String player1name, String player2name) {
        if (player1name.equals(player2name)) {
            throw new BadRequestException("Player names must not match");
        }
    }

    private boolean isNullOrBlank(String string) {
        return (string == null || string.isBlank());
    }

    private boolean isTooLarge(String string) {
        return (string.length() > 30);
    }

    private boolean isValidNameFormat(String string) {
        return (string.matches("^[A-Za-zА]+(?:[ -][A-Za-zА]+)*$"));
    }


}
