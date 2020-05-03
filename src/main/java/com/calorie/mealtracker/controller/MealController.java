package com.calorie.mealtracker.controller;

import com.calorie.mealtracker.error.CaloriesNotProvidedException;
import com.calorie.mealtracker.error.TimeInMillisecondsNotProvidedException;
import com.calorie.mealtracker.error.MealNameNotProvidedException;
import com.calorie.mealtracker.model.request.CreateMealRequestBody;
import com.calorie.mealtracker.model.request.GetMealsForUserRequestBody;
import com.calorie.mealtracker.model.response.CreateMealResponseBody;
import com.calorie.mealtracker.model.response.StandardErrorResponseBody;
import com.calorie.mealtracker.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/meals")
public class MealController {

    @Autowired
    private MealService mealService;

    @PostMapping
    public ResponseEntity createMealForUser(@RequestBody CreateMealRequestBody requestBody) {

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

    @GetMapping
    public ResponseEntity getMealsForUser(@RequestParam("from") long fromTimeInMilliseconds, @RequestParam("to") long toTimeInMilliseconds) {

        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Object obj = mealService.getMealsForUserAggregation(principal.getUsername(), fromTimeInMilliseconds, toTimeInMilliseconds);

        return ResponseEntity.ok("Hi");

    }

}
