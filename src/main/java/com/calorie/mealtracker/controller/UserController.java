package com.calorie.mealtracker.controller;

import com.calorie.mealtracker.model.MealtrackerUser;
import com.calorie.mealtracker.model.request.UpdateMealtackerUserRequestBody;
import com.calorie.mealtracker.model.response.GetUserInfoResponseBody;
import com.calorie.mealtracker.model.response.UpdateMealtackerUserResponseBody;
import com.calorie.mealtracker.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @GetMapping
    public ResponseEntity getUserInfo() {

        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        MealtrackerUser user = myUserDetailsService.loadMealtrackerUserByUsername(principal.getUsername());

        return ResponseEntity.ok(new GetUserInfoResponseBody(user));

    }

    @PutMapping
    public ResponseEntity updateUserInfo(@RequestBody UpdateMealtackerUserRequestBody requestBody) {

        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        myUserDetailsService.updateUserInfo(principal.getUsername(), requestBody);

        return ResponseEntity.ok(new UpdateMealtackerUserResponseBody());

    }

}
