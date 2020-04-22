package com.calorie.mealtracker.error;

public class UsernameAlreadyExistsException extends Exception {

    public static final String MESSAGE = "Username already exists";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
