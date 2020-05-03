package com.calorie.mealtracker.model.response;

public class UpdateMealtackerUserResponseBody extends StandardResponseBody {

    public static final String MESSAGE = "Successfully updated user details.";

    public UpdateMealtackerUserResponseBody() {
        super(SUCCESS, MESSAGE);
    }
}
