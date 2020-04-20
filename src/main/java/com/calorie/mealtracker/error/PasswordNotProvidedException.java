package com.calorie.mealtracker.error;

public class PasswordNotProvidedException extends Exception {

    public static String MESSAGE;

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
