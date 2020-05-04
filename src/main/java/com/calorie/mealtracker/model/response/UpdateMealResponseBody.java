package com.calorie.mealtracker.model.response;

public class UpdateMealResponseBody extends StandardResponseBody {

    public static final String MESSAGE = "Updated meal successfully";

    public UpdateMealResponseBody() {
        super(SUCCESS, MESSAGE);
    }
}
