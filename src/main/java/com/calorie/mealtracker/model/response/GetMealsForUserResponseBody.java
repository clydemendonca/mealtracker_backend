package com.calorie.mealtracker.model.response;

import com.calorie.mealtracker.model.Meal;
import org.apache.catalina.mapper.Mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GetMealsForUserResponseBody extends StandardResponseBody {

    public static final String MESSAGE = "Successfully retrieved meals.";

    private List<MealsToReturn> meals;

    public GetMealsForUserResponseBody(ArrayList<Meal> meals) {
        super(SUCCESS, MESSAGE);
        this.meals = meals.stream().map(new Function<Meal, MealsToReturn>() {

            @Override
            public MealsToReturn apply(Meal meal) {
                return new MealsToReturn(meal);
            }
        }).collect(Collectors.toList());
    }

    public List<MealsToReturn> getMeals() {
        return meals;
    }

    public void setMeals(List<MealsToReturn> meals) {
        this.meals = meals;
    }

    private class MealsToReturn {

        private String id;
        private String mealName;
        private long date;
        private float calories;

        public MealsToReturn(Meal meal) {
            id = meal.getId();
            mealName = meal.getMealName();
            date = meal.getDate().getTime();
            calories = meal.getCalories();
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMealName() {
            return mealName;
        }

        public void setMealName(String mealName) {
            this.mealName = mealName;
        }

        public long getDate() {
            return date;
        }

        public void setDate(long date) {
            this.date = date;
        }

        public float getCalories() {
            return calories;
        }

        public void setCalories(float calories) {
            this.calories = calories;
        }
    }
}
