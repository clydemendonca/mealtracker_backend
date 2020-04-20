package com.calorie.mealtracker.error;

public class IncorrectPasswordException extends Exception {

    public static final String MESSAGE = "Incorrect Password";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
