package com.calorie.mealtracker.service;

import com.calorie.mealtracker.model.Meal;
import com.calorie.mealtracker.model.CaloriesForUserByDate;
import com.calorie.mealtracker.model.MealtrackerUser;
import com.calorie.mealtracker.model.request.CreateOrUpdateMealRequestBody;
import com.calorie.mealtracker.repository.MealJpaRepository;
import com.calorie.mealtracker.repository.MealtrackerUserJpaRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MealService {

    @Autowired
    private MealJpaRepository mealJpaRepository;

    @Autowired
    private MealtrackerUserJpaRepository mealtrackerUserJpaRepository;

    public void createMealForUser(String username, CreateOrUpdateMealRequestBody createOrUpdateMealRequestBody) {

        MealtrackerUser user = mealtrackerUserJpaRepository.findByUsername(username);
        Date date = new Date(createOrUpdateMealRequestBody.getTimeInMilliseconds());

        mealJpaRepository.save(new Meal(user.getId(), date, createOrUpdateMealRequestBody.getMealName(), createOrUpdateMealRequestBody.getCalories()));

    }

    public List<CaloriesForUserByDate> getDaywiseCalorieIntakeForUser(String username, long fromTimeInMilliseconds, long toTimeInMilliseconds) {

        MealtrackerUser user = mealtrackerUserJpaRepository.findByUsername(username);
        Date fromDate = new Date(fromTimeInMilliseconds);
        Date toDate = new Date(toTimeInMilliseconds);

        List<CaloriesForUserByDate> mealsForUser = mealJpaRepository.getDaywiseCalorieIntakeForUser(user.getId(), fromDate, toDate);

        return mealsForUser;
    }

    public ArrayList<Meal> getMealsForUser(String username, long fromTimeInMilliseconds, long toTimeInMilliseconds) {

        MealtrackerUser user = mealtrackerUserJpaRepository.findByUsername(username);
        Date fromDate = new Date(fromTimeInMilliseconds);
        Date toDate = new Date(toTimeInMilliseconds);

        ArrayList<Meal> meals = mealJpaRepository.findByUserIdAndDateBetweenOrderByDateAsc(new ObjectId(user.getId()), fromDate, toDate);
        return meals;

    }

    public void updateMealForUser(String username, String mealId, CreateOrUpdateMealRequestBody updateMealrequestBody) {
        MealtrackerUser user = mealtrackerUserJpaRepository.findByUsername(username);
        Date date = new Date(updateMealrequestBody.getTimeInMilliseconds());

        mealJpaRepository.save(new Meal(mealId, user.getId(), date, updateMealrequestBody.getMealName(), updateMealrequestBody.getCalories()));

    }
}
