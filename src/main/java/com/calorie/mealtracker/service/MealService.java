package com.calorie.mealtracker.service;

import com.calorie.mealtracker.model.Meal;
import com.calorie.mealtracker.model.CaloriesForUserByDate;
import com.calorie.mealtracker.model.MealtrackerUser;
import com.calorie.mealtracker.model.request.CreateMealRequestBody;
import com.calorie.mealtracker.repository.MealJpaRepository;
import com.calorie.mealtracker.repository.MealtrackerUserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MealService {

    @Autowired
    private MealJpaRepository mealJpaRepository;

    @Autowired
    private MealtrackerUserJpaRepository mealtrackerUserJpaRepository;

    public void createMealForUser(String username, CreateMealRequestBody createMealRequestBody) {

        MealtrackerUser user = mealtrackerUserJpaRepository.findByUsername(username);
        Date date = new Date(createMealRequestBody.getTimeInMilliseconds());

        mealJpaRepository.save(new Meal(user.getId(), date, createMealRequestBody.getMealName(), createMealRequestBody.getCalories()));

    }

    public List<CaloriesForUserByDate> getDaywiseCalorieIntakeForUser(String username, long fromTimeInMilliseconds, long toTimeInMilliseconds) {

        MealtrackerUser user = mealtrackerUserJpaRepository.findByUsername(username);
        Date fromDate = new Date(fromTimeInMilliseconds);
        Date toDate = new Date(toTimeInMilliseconds);

        List<CaloriesForUserByDate> mealsForUser = mealJpaRepository.getDaywiseCalorieIntakeForUser(user.getId(), fromDate, toDate);

        return mealsForUser;
    }
}
