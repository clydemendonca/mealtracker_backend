package com.calorie.mealtracker.model.response;

public class DeleteMealResponseBody extends StandardResponseBody {

    public static final String MESSAGE = "Deleted meal successfully.";

    public DeleteMealResponseBody() {
        super(SUCCESS, MESSAGE);
    }
}
