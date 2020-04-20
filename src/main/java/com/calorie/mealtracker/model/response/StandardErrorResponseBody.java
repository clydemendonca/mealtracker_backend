package com.calorie.mealtracker.model.response;

public class StandardErrorResponseBody extends StandardResponseBody {

    private static final String STATUS_ERROR = "Error";

    public StandardErrorResponseBody(String message) {
        super(STATUS_ERROR, message);
    }

}
