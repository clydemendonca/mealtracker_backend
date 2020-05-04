package com.calorie.mealtracker.controller;

import com.calorie.mealtracker.error.CaloriesNotProvidedException;
import com.calorie.mealtracker.error.TimeInMillisecondsNotProvidedException;
import com.calorie.mealtracker.error.MealNameNotProvidedException;
import com.calorie.mealtracker.model.CaloriesForUserByDate;
import com.calorie.mealtracker.model.Meal;
import com.calorie.mealtracker.model.MealtrackerUser;
import com.calorie.mealtracker.model.request.CreateOrUpdateMealRequestBody;
import com.calorie.mealtracker.model.response.*;
import com.calorie.mealtracker.service.MealService;
import com.calorie.mealtracker.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/meals")
public class MealController {

    @Autowired
    private MealService mealService;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @PostMapping
    public ResponseEntity createMealForUser(@RequestBody CreateOrUpdateMealRequestBody requestBody) {

        try {
            UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (requestBody.isMealNameEmpty()) throw new MealNameNotProvidedException();
            if (requestBody.isTimeInMillisecondsNotProvided()) throw new TimeInMillisecondsNotProvidedException();
            if (requestBody.isCaloriesNotProvided()) throw new CaloriesNotProvidedException();

            mealService.createMealForUser(principal.getUsername(), requestBody);

            return ResponseEntity.ok(new CreateMealResponseBody());

        } catch (MealNameNotProvidedException | TimeInMillisecondsNotProvidedException | CaloriesNotProvidedException e) {
            return new ResponseEntity(new StandardErrorResponseBody(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/daywise")
    public ResponseEntity getDaywiseCalorieIntakeForUser(@RequestParam("from") long fromTimeInMilliseconds, @RequestParam("to") long toTimeInMilliseconds) {

        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        MealtrackerUser user = myUserDetailsService.loadMealtrackerUserByUsername(principal.getUsername());
        List<CaloriesForUserByDate> calorieIntakeForUser = mealService.getDaywiseCalorieIntakeForUser(principal.getUsername(), fromTimeInMilliseconds, toTimeInMilliseconds);

        return ResponseEntity.ok(new DaywiseCalorieIntakeResponseBody(user.getDailyCalorieIntake(),calorieIntakeForUser));

    }

    @GetMapping
    public ResponseEntity getMealsForUser(@RequestParam("from") long fromTimeInMilliseconds, @RequestParam("to") long toTimeInMilliseconds) {

        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        MealtrackerUser user = myUserDetailsService.loadMealtrackerUserByUsername(principal.getUsername());
        ArrayList<Meal> meals = mealService.getMealsForUser(principal.getUsername(), fromTimeInMilliseconds, toTimeInMilliseconds);

        return ResponseEntity.ok(new GetMealsForUserResponseBody(meals));
    }

    @PutMapping("/{id}")
    public ResponseEntity updateMealForUser(@PathVariable("id") String mealId, @RequestBody CreateOrUpdateMealRequestBody requestBody) {

        try {
            UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (requestBody.isMealNameEmpty()) throw new MealNameNotProvidedException();
            if (requestBody.isTimeInMillisecondsNotProvided()) throw new TimeInMillisecondsNotProvidedException();
            if (requestBody.isCaloriesNotProvided()) throw new CaloriesNotProvidedException();

            mealService.updateMealForUser(principal.getUsername(), mealId,requestBody);

            return ResponseEntity.ok(new UpdateMealResponseBody());

        } catch (MealNameNotProvidedException | TimeInMillisecondsNotProvidedException | CaloriesNotProvidedException e) {
            return new ResponseEntity(new StandardErrorResponseBody(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity updateMealForUser(@PathVariable("id") String mealId) {

            mealService.deleteMeal(mealId);
            return ResponseEntity.ok(new DeleteMealResponseBody());

    }

}
