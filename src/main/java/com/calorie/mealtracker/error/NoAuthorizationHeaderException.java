package com.calorie.mealtracker.error;

public class NoAuthorizationHeaderException extends Exception {

    public static final String MESSAGE = "Authorization header is empty";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
