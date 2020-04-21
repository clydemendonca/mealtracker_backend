package com.calorie.mealtracker.controller.authentication;


import com.calorie.mealtracker.controller.AuthenticationController;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthenticationController.class)
public class SignUpUnitTests {

    @Test
    public void test() {
        Assertions.assertEquals(0, 0);
    }

}
