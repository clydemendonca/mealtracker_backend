package com.calorie.mealtracker.service;

import com.calorie.mealtracker.error.IncorrectPasswordException;
import com.calorie.mealtracker.error.UsernameDoesNotExistException;
import com.calorie.mealtracker.model.MealtrackerUser;
import com.calorie.mealtracker.model.response.LoginResponseBody;
import com.calorie.mealtracker.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationServiceUnitTests {

    public static final String USERNAME = "something";
    public static final String PASSWORD_CORRECT = "something";
    public static final String PASSWORD_INCORRECT = "somethingElse";
    public static final String FULL_NAME = "John Doe";

    private AuthenticationService authenticationService;

    @Mock
    private UserRepository repository;

    @Before
    public void setup() {
        authenticationService = new AuthenticationService();
        authenticationService.setUserRepository(repository);
    }

    @Test(expected = UsernameDoesNotExistException.class)
    public void whenUsernameIsIncorrect_throwAnException() throws UsernameDoesNotExistException, IncorrectPasswordException {

        when(repository.getUserWithUsername(USERNAME)).thenThrow(UsernameDoesNotExistException.class);
        authenticationService.login(USERNAME, "");
        verify(repository, times(1)).getUserWithUsername(USERNAME);

    }

    @Test(expected = IncorrectPasswordException.class)
    public void whenUsernameCorrectAndPasswordIsIncorrect_throwAnException() throws UsernameDoesNotExistException, IncorrectPasswordException {

        MealtrackerUser user = new MealtrackerUser(USERNAME, PASSWORD_CORRECT, FULL_NAME);
        when(repository.getUserWithUsername(USERNAME)).thenReturn(user);

        authenticationService.login(USERNAME, PASSWORD_INCORRECT);

        verify(repository, times(1)).getUserWithUsername(USERNAME);

    }

    @Test
    public void whenUsernameAndPasswordAreCorrect_returnLoginResponseBody() throws UsernameDoesNotExistException, IncorrectPasswordException {

        MealtrackerUser expectedUser = new MealtrackerUser(USERNAME, PASSWORD_CORRECT, FULL_NAME);
        when(repository.getUserWithUsername(USERNAME)).thenReturn(expectedUser);

        LoginResponseBody actualLoginResponseBody = authenticationService.login(USERNAME, PASSWORD_CORRECT);
        LoginResponseBody.MealtrackerUserToReturn actualUser = actualLoginResponseBody.getUser();

        verify(repository, times(1)).getUserWithUsername(USERNAME);

        assertEquals(expectedUser.getFullName(), actualUser.getFullName());
        assertEquals(expectedUser.getUsername(), actualUser.getUsername());

    }

}
