package com.calorie.mealtracker.model.response;

public class SignUpResponseBody extends StandardResponseBody {

    public static final String MESSAGE = "Successfully signed up user";

    public SignUpResponseBody() {
        super(SUCCESS, MESSAGE);
    }
}
