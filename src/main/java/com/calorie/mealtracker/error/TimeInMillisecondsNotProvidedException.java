package com.calorie.mealtracker.error;

public class TimeInMillisecondsNotProvidedException extends Exception {

    public static final String MESSAGE = "Time In Milliseconds is a required field";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
