package com.calorie.mealtracker.error;

public class UsernameDoesNotExistException extends Exception {

    public static final String MESSAGE = "Username does not exist.";

    @Override
    public String getMessage() {
        return MESSAGE;
    }

}
