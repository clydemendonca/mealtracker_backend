package com.calorie.mealtracker.model.response;

import com.calorie.mealtracker.model.MealtrackerUser;

public class GetUserInfoResponseBody {

    private String username;
    private String fullName;
    private float dailyCalorieIntake;

    public GetUserInfoResponseBody(MealtrackerUser user) {
        username = user.getUsername();
        fullName = user.getFullName();
        dailyCalorieIntake = user.getDailyCalorieIntake();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
