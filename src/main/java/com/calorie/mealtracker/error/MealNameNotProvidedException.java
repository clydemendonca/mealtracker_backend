package com.calorie.mealtracker.error;

public class MealNameNotProvidedException extends Exception {

    public final static String MESSAGE = "Meal name is a required field.";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
