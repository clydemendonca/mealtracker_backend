package com.calorie.mealtracker.controller;

import com.calorie.mealtracker.error.IncorrectPasswordException;
import com.calorie.mealtracker.error.PasswordNotProvidedException;
import com.calorie.mealtracker.error.UsernameDoesNotExistException;
import com.calorie.mealtracker.error.UsernameNotProvidedException;
import com.calorie.mealtracker.model.request.LoginRequestBody;
import com.calorie.mealtracker.model.response.LoginResponseBody;
import com.calorie.mealtracker.model.response.StandardErrorResponseBody;
import com.calorie.mealtracker.model.response.StandardResponseBody;
import com.calorie.mealtracker.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody LoginRequestBody loginRequestBody) {
        try {

            if (loginRequestBody.isUsernameNotProvided()) throw new UsernameNotProvidedException();
            if (loginRequestBody.isPasswordNotProvided()) throw new PasswordNotProvidedException();

            LoginResponseBody responseBody = authenticationService.login(loginRequestBody.getUsername(), loginRequestBody.getPassword());

            return new ResponseEntity<>(responseBody, HttpStatus.OK);

        } catch (UsernameNotProvidedException | PasswordNotProvidedException | IncorrectPasswordException e) {
            return new ResponseEntity<StandardResponseBody>(new StandardErrorResponseBody(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (UsernameDoesNotExistException e) {
            return new ResponseEntity<StandardResponseBody>(new StandardErrorResponseBody(e.getMessage()), HttpStatus.NOT_FOUND);
        }

    }

}
