package com.calorie.mealtracker.error;

public class NoTokenFoundException extends Exception {

    public static final String MESSAGE = "Token not found";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
