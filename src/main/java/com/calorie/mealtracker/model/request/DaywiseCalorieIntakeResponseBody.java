package com.calorie.mealtracker.model.request;

import com.calorie.mealtracker.model.CaloriesForUserByDate;

import java.util.List;

public class DaywiseCalorieIntakeResponseBody {

    private List<CaloriesForUserByDate> calorieIntakeForUser;

    public DaywiseCalorieIntakeResponseBody(List<CaloriesForUserByDate> calorieIntakeForUser) {
        this.calorieIntakeForUser = calorieIntakeForUser;
    }

    public List<CaloriesForUserByDate> getCalorieIntakeForUser() {
        return calorieIntakeForUser;
    }

    public void setCalorieIntakeForUser(List<CaloriesForUserByDate> calorieIntakeForUser) {
        this.calorieIntakeForUser = calorieIntakeForUser;
    }
}
