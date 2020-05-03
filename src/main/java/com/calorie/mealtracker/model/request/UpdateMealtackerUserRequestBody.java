package com.calorie.mealtracker.model.request;

public class UpdateMealtackerUserRequestBody {

    private String fullName;
    private float dailyCalorieIntake;

    public UpdateMealtackerUserRequestBody(String fullName, float dailyCalorieIntake) {
        this.fullName = fullName;
        this.dailyCalorieIntake = dailyCalorieIntake;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public float getDailyCalorieIntake() {
        return dailyCalorieIntake;
    }

    public void setDailyCalorieIntake(float dailyCalorieIntake) {
        this.dailyCalorieIntake = dailyCalorieIntake;
    }
}
