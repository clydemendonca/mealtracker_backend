package com.calorie.mealtracker.model.request;

import com.calorie.mealtracker.model.CaloriesForUserByDate;

import java.util.List;

public class DaywiseCalorieIntakeResponseBody {

    private List<CaloriesForUserByDate> calorieIntakeForUser;
    private float dailyCalorieIntake;

    public DaywiseCalorieIntakeResponseBody(float dailyCalorieIntake, List<CaloriesForUserByDate> calorieIntakeForUser) {
        this.dailyCalorieIntake = dailyCalorieIntake;
        this.calorieIntakeForUser = calorieIntakeForUser;
    }

    public float getDailyCalorieIntake() {
        return dailyCalorieIntake;
    }

    public void setDailyCalorieIntake(float dailyCalorieIntake) {
        this.dailyCalorieIntake = dailyCalorieIntake;
    }

    public List<CaloriesForUserByDate> getCalorieIntakeForUser() {
        return calorieIntakeForUser;
    }

    public void setCalorieIntakeForUser(List<CaloriesForUserByDate> calorieIntakeForUser) {
        this.calorieIntakeForUser = calorieIntakeForUser;
    }
}
