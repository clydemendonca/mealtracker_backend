package com.calorie.mealtracker.error;

public class FullNameNotProvidedException extends Exception {

    public static final String MESSAGE = "Full name is a required field";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
