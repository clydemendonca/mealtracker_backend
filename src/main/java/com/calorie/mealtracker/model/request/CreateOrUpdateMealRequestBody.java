package com.calorie.mealtracker.model.request;

import java.util.Date;

public class CreateOrUpdateMealRequestBody {

    private long timeInMilliseconds;
    private String mealName;
    private float calories;

    public CreateOrUpdateMealRequestBody(long timeInMilliseconds, String mealName, float calories) {
        this.timeInMilliseconds = timeInMilliseconds;
        this.mealName = mealName;
        this.calories = calories;
    }

    public long getTimeInMilliseconds() {
        return timeInMilliseconds;
    }

    public void setTimeInMilliseconds(long timeInMilliseconds) {
        this.timeInMilliseconds = timeInMilliseconds;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public float getCalories() {
        return calories;
    }

    public void setCalories(float calories) {
        this.calories = calories;
    }

    public boolean isMealNameEmpty() {
        return mealName == null || mealName.isEmpty();
    }

    public boolean isTimeInMillisecondsNotProvided() {
        return timeInMilliseconds == 0;
    }

    public boolean isCaloriesNotProvided() {
        return calories == 0;
    }
}
