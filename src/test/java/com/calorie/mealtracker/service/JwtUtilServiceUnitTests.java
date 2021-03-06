package com.calorie.mealtracker.service;

import com.calorie.mealtracker.model.MealtrackerUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static com.calorie.mealtracker.service.JwtUtilService.*;

@RunWith(MockitoJUnitRunner.class)
public class JwtUtilServiceUnitTests {

    public static final String USERNAME = "something";
    public static final String PASSWORD_CORRECT = "something";
    public static final String PASSWORD_INCORRECT = "somethingElse";
    public static final String FULL_NAME = "John Doe";
    private static final String ID = "";
    private static final MealtrackerUser.Role ROLE = MealtrackerUser.Role.USER;

    private JwtUtilService jwtUtilService;

    @Before
    public void setup() {
        jwtUtilService = new JwtUtilService();
    }

    @Test
    public void whenGenerateFunctionIsCalled_hashMapShouldBeReturned() {

        MealtrackerUser expectedUser = new MealtrackerUser(ID, USERNAME, PASSWORD_CORRECT, FULL_NAME, ROLE);

        String jwtToken = jwtUtilService.generateToken(expectedUser);

        Claims actualClaims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwtToken).getBody();

        Assertions.assertEquals(expectedUser.getUsername(), actualClaims.get(KEY_USERNAME));
        Assertions.assertEquals(expectedUser.getId(), actualClaims.get(KEY_USER_ID));
        Assertions.assertEquals(expectedUser.getRole().name(), actualClaims.get(KEY_ROLE));

    }

}
