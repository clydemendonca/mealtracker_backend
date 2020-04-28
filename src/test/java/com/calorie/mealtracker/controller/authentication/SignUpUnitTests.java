package com.calorie.mealtracker.controller.authentication;


import com.calorie.mealtracker.controller.AuthenticationController;
import com.calorie.mealtracker.error.FullNameNotProvidedException;
import com.calorie.mealtracker.error.PasswordNotProvidedException;
import com.calorie.mealtracker.error.UsernameAlreadyExistsException;
import com.calorie.mealtracker.error.UsernameNotProvidedException;
import com.calorie.mealtracker.model.MealtrackerUser;
import com.calorie.mealtracker.model.request.SignUpRequestBody;
import com.calorie.mealtracker.service.AuthenticationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.calorie.mealtracker.service.AuthenticationServiceUnitTests.*;
import static com.calorie.mealtracker.utils.JSONConverter.asJsonString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthenticationController.class)
public class SignUpUnitTests {

    private static final String ENDPOINT = "/auth/sign-up";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService authenticationService;

    @Test
    public void whenUsernameIsEmpty_errorShouldSaySo() throws Exception {

        SignUpRequestBody signUpRequestBody = new SignUpRequestBody("", PASSWORD_CORRECT, FULL_NAME);

        mockMvc.perform(
                post(ENDPOINT)
                        .content(asJsonString(signUpRequestBody))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(UsernameNotProvidedException.MESSAGE));

        verify(authenticationService, times(0)).signUp(signUpRequestBody);

    }

    @Test
    public void whenUsernameIsNull_errorShouldSaySo() throws Exception {

        SignUpRequestBody signUpRequestBody = new SignUpRequestBody(null, PASSWORD_CORRECT, FULL_NAME);

        mockMvc.perform(
                post(ENDPOINT)
                        .content(asJsonString(signUpRequestBody))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(UsernameNotProvidedException.MESSAGE));

        verify(authenticationService, times(0)).signUp(signUpRequestBody);

    }

    @Test
    public void whenPasswordIsEmpty_errorShouldSaySo() throws Exception {

        SignUpRequestBody signUpRequestBody = new SignUpRequestBody(USERNAME, "", FULL_NAME);

        mockMvc.perform(
                post(ENDPOINT)
                        .content(asJsonString(signUpRequestBody))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(PasswordNotProvidedException.MESSAGE));

        verify(authenticationService, times(0)).signUp(signUpRequestBody);

    }

    @Test
    public void whenPasswordIsNull_errorShouldSaySo() throws Exception {

        SignUpRequestBody signUpRequestBody = new SignUpRequestBody(USERNAME, null, FULL_NAME);

        mockMvc.perform(
                post(ENDPOINT)
                        .content(asJsonString(signUpRequestBody))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(PasswordNotProvidedException.MESSAGE));

        verify(authenticationService, times(0)).signUp(signUpRequestBody);

    }

    @Test
    public void whenFullNameIsEmpty_errorShouldSaySo() throws Exception {

        SignUpRequestBody signUpRequestBody = new SignUpRequestBody(USERNAME, PASSWORD_CORRECT, "");

        mockMvc.perform(
                post(ENDPOINT)
                        .content(asJsonString(signUpRequestBody))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(FullNameNotProvidedException.MESSAGE));

        verify(authenticationService, times(0)).signUp(signUpRequestBody);

    }

    @Test
    public void whenFullNameIsNull_errorShouldSaySo() throws Exception {

        SignUpRequestBody signUpRequestBody = new SignUpRequestBody(USERNAME, PASSWORD_CORRECT, null);

        mockMvc.perform(
                post(ENDPOINT)
                        .content(asJsonString(signUpRequestBody))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(FullNameNotProvidedException.MESSAGE));

        verify(authenticationService, times(0)).signUp(signUpRequestBody);

    }

    @Test
    public void whenUsernameAlreadyExists_errorShouldSaySo() throws Exception {

        SignUpRequestBody signUpRequestBody = new SignUpRequestBody(USERNAME, PASSWORD_CORRECT, FULL_NAME);

        when(authenticationService.signUp(any(SignUpRequestBody.class))).thenThrow(new UsernameAlreadyExistsException());

        mockMvc.perform(
                post(ENDPOINT)
                        .content(asJsonString(signUpRequestBody))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(UsernameAlreadyExistsException.MESSAGE));

        verify(authenticationService, times(1)).signUp(any(SignUpRequestBody.class));

    }

}
