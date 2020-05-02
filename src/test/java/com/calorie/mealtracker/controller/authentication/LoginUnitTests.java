package com.calorie.mealtracker.controller.authentication;

import com.calorie.mealtracker.controller.AuthenticationController;
import com.calorie.mealtracker.error.IncorrectPasswordException;
import com.calorie.mealtracker.error.PasswordNotProvidedException;
import com.calorie.mealtracker.error.UsernameDoesNotExistException;
import com.calorie.mealtracker.error.UsernameNotProvidedException;
import com.calorie.mealtracker.model.MealtrackerUser;
import com.calorie.mealtracker.model.request.LoginRequestBody;
import com.calorie.mealtracker.model.response.LoginResponseBody;
import com.calorie.mealtracker.service.AuthenticationService;
import com.calorie.mealtracker.service.AuthenticationServiceUnitTests;
import com.calorie.mealtracker.service.JwtUtilService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.calorie.mealtracker.service.AuthenticationServiceUnitTests.*;
import static com.calorie.mealtracker.utils.JSONConverter.asJsonString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthenticationController.class)
public class LoginUnitTests {

    private final static String ENDPOINT = "/auth/login";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService authenticationService;

    @Test
    public void whenUsernameIsEmpty_errorShouldSaySo() throws Exception {

        LoginRequestBody requestBodyWithEmptyUsername = new LoginRequestBody("", "");

        mockMvc.perform(
                post(ENDPOINT)
                        .content(asJsonString(requestBodyWithEmptyUsername))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(UsernameNotProvidedException.MESSAGE));

        verify(authenticationService, times(0)).login(requestBodyWithEmptyUsername.getUsername(), requestBodyWithEmptyUsername.getPassword());

    }

    @Test
    public void whenUsernameIsNull_errorShouldSaySo() throws Exception {

        LoginRequestBody requestBodyWithNullUsername = new LoginRequestBody(null, "");

        mockMvc.perform(
                post(ENDPOINT)
                        .content(asJsonString(requestBodyWithNullUsername))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(UsernameNotProvidedException.MESSAGE));

        verify(authenticationService, times(0)).login(requestBodyWithNullUsername.getUsername(), requestBodyWithNullUsername.getPassword());

    }

    @Test
    public void whenPasswordIsEmpty_errorShouldSaySo() throws Exception {

        LoginRequestBody requestBodyWithEmptyPassword = new LoginRequestBody(USERNAME, "");

        mockMvc.perform(
                post(ENDPOINT)
                        .content(asJsonString(requestBodyWithEmptyPassword))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(PasswordNotProvidedException.MESSAGE));

        verify(authenticationService, times(0)).login(requestBodyWithEmptyPassword.getUsername(), requestBodyWithEmptyPassword.getPassword());

    }

    @Test
    public void whenPasswordIsNull_errorShouldSaySo() throws Exception {

        LoginRequestBody requestBodyWithNullPassword = new LoginRequestBody(USERNAME, null);

        mockMvc.perform(
                post(ENDPOINT)
                        .content(asJsonString(requestBodyWithNullPassword))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(PasswordNotProvidedException.MESSAGE));

        verify(authenticationService, times(0)).login(requestBodyWithNullPassword.getUsername(), requestBodyWithNullPassword.getPassword());

    }

    @Test
    public void whenUsernameDoesNotExist_errorShouldBe404() throws Exception {

        LoginRequestBody requestBody = new LoginRequestBody(USERNAME, PASSWORD_CORRECT);
        when(authenticationService.login(requestBody.getUsername(), requestBody.getPassword())).thenThrow(UsernameDoesNotExistException.class);

        mockMvc.perform(
                post(ENDPOINT)
                        .content(asJsonString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(UsernameDoesNotExistException.MESSAGE));

        verify(authenticationService, times(1)).login(requestBody.getUsername(), requestBody.getPassword());

    }

    @Test
    public void whenUsernameCorrectAndPasswordIsIncorrect_errorShouldBe404() throws Exception {

        LoginRequestBody requestBody = new LoginRequestBody(USERNAME, PASSWORD_INCORRECT);

        MealtrackerUser user = new MealtrackerUser(ID, USERNAME, PASSWORD_CORRECT, FULL_NAME, ROLE);

        when(authenticationService.login(requestBody.getUsername(), requestBody.getPassword())).thenThrow(IncorrectPasswordException.class);

        mockMvc.perform(
                post(ENDPOINT)
                        .content(asJsonString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(IncorrectPasswordException.MESSAGE));

        verify(authenticationService, times(1)).login(requestBody.getUsername(), requestBody.getPassword());

    }

    @Test
    public void whenUsernameAndPasswordAreCorrect_UserInformationShouldBeReturned() throws Exception {

        LoginRequestBody requestBody = new LoginRequestBody(USERNAME, PASSWORD_CORRECT);
        MealtrackerUser user = new MealtrackerUser(ID, USERNAME, PASSWORD_CORRECT, FULL_NAME, ROLE);
        String token = new JwtUtilService().generateToken(user);

        LoginResponseBody loginResponseBody = new LoginResponseBody(user);
        loginResponseBody.setToken(token);

        when(authenticationService.login(requestBody.getUsername(), requestBody.getPassword())).thenReturn(loginResponseBody);

        mockMvc.perform(
                post(ENDPOINT)
                        .content(asJsonString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(LoginResponseBody.MESSAGE))
                .andExpect(jsonPath("$.user.token").value(token))
                .andExpect(jsonPath("$.user.fullName").value(user.getFullName()))
                .andExpect(jsonPath("$.user.role").value(user.getRole().toString()))
                .andExpect(jsonPath("$.user.username").value(user.getUsername()));

        verify(authenticationService, times(1)).login(requestBody.getUsername(), requestBody.getPassword());

    }

}
