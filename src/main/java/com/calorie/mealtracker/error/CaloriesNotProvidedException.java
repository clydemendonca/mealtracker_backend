package com.calorie.mealtracker.error;

public class CaloriesNotProvidedException extends Exception {

    public static final String MESSAGE = "Calories is a required field and must be greater than 0";

    @Override
    public String getMessage() {
        return MESSAGE;
    }

}
