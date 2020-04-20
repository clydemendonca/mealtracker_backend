package com.calorie.mealtracker.model.response;

public class StandardResponseBody {

    public static final String SUCCESS = "Success";

    private String status = SUCCESS;
    private String message;

    public StandardResponseBody(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
