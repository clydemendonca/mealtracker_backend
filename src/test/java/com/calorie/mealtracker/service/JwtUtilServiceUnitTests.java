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
    private static final long ID = 100;
    private static final MealtrackerUser.Role ROLE = MealtrackerUser.Role.USER;

    private JwtUtilService jwtUtilService;

    @Before
    public void setup() {
        jwtUtilService = new JwtUtilService();
    }

    @Test
    public void whenGenerateFunctionIsCalled_hashMapShouldBeReturned() {

        MealtrackerUser user = new MealtrackerUser(ID, USERNAME, PASSWORD_CORRECT, FULL_NAME, ROLE);

        String jwtToken = jwtUtilService.generateToken(user);

        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwtToken).getBody();

        Assertions.assertEquals(claims.get(KEY_USERNAME), user.getUsername());
        Assertions.assertEquals((long) claims.get(KEY_USER_ID), user.getId());
        Assertions.assertEquals(claims.get(KEY_ROLE), user.getRole());

    }

}
