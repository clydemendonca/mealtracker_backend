package com.calorie.mealtracker.model.response;

public class CreateMealResponseBody extends StandardResponseBody {

    public static final String MESSAGE = "Meal successfully added";

    public CreateMealResponseBody() {
        super(SUCCESS, MESSAGE);
    }
}
