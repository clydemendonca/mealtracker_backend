package com.calorie.mealtracker.error;

public class UsernameNotProvidedException extends Exception {

    public static String MESSAGE = "Username is a required field";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
