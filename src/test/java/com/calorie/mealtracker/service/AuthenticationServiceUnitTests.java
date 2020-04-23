package com.calorie.mealtracker.service;

import com.calorie.mealtracker.error.IncorrectPasswordException;
import com.calorie.mealtracker.error.UsernameAlreadyExistsException;
import com.calorie.mealtracker.error.UsernameDoesNotExistException;
import com.calorie.mealtracker.model.MealtrackerUser;
import com.calorie.mealtracker.model.request.SignUpRequestBody;
import com.calorie.mealtracker.model.response.LoginResponseBody;
import com.calorie.mealtracker.model.response.SignUpResponseBody;
import com.calorie.mealtracker.model.response.StandardResponseBody;
import com.calorie.mealtracker.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static com.calorie.mealtracker.service.JwtUtilService.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationServiceUnitTests {

    public static final String USERNAME = "something";
    public static final String PASSWORD_CORRECT = "something";
    public static final String PASSWORD_INCORRECT = "somethingElse";
    public static final String FULL_NAME = "John Doe";
    public static final long ID = 100;
    public static final MealtrackerUser.Role ROLE = MealtrackerUser.Role.USER;
    private static String CORRECT_JWT_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoxLCJpZCI6MTAwLCJ1c2VybmFtZSI6InNvbWV0aGluZyIsInN1YiI6InNvbWV0aGluZyIsImlhdCI6MTU4NzU0ODc5MSwiZXhwIjoxNTg3NTg0NzkxfQ.BzXHDJz5mOvQw77YIdl3wMt8ECkofEaX9dxzUZ79HOY";

    private AuthenticationService authenticationService;

    @Mock
    private UserRepository repository;

    @Mock
    private JwtUtilService jwtUtilService;

    @Before
    public void setup() {
        authenticationService = new AuthenticationService();
        authenticationService.setUserRepository(repository);
        authenticationService.setJwtUtilService(jwtUtilService);
    }

    @Test(expected = UsernameDoesNotExistException.class)
    public void whenUsernameIsIncorrect_throwAnException() throws UsernameDoesNotExistException, IncorrectPasswordException {

        when(repository.getUserWithUsername(USERNAME)).thenThrow(UsernameDoesNotExistException.class);
        authenticationService.login(USERNAME, "");
        verify(repository, times(1)).getUserWithUsername(USERNAME);

    }

    @Test(expected = IncorrectPasswordException.class)
    public void whenUsernameCorrectAndPasswordIsIncorrect_throwAnException() throws UsernameDoesNotExistException, IncorrectPasswordException {

        MealtrackerUser user = new MealtrackerUser(ID, USERNAME, PASSWORD_CORRECT, FULL_NAME, ROLE);
        when(repository.getUserWithUsername(USERNAME)).thenReturn(user);

        authenticationService.login(USERNAME, PASSWORD_INCORRECT);

        verify(repository, times(1)).getUserWithUsername(USERNAME);

    }


    @Test(expected = ExpiredJwtException.class)
    public void whenUsernameAndPasswordAreCorrect_returnLoginResponseBody() throws UsernameDoesNotExistException, IncorrectPasswordException {

        MealtrackerUser expectedUser = new MealtrackerUser(ID, USERNAME, PASSWORD_CORRECT, FULL_NAME, ROLE);

        when(repository.getUserWithUsername(USERNAME)).thenReturn(expectedUser);
        when(jwtUtilService.generateToken(eq(expectedUser))).thenReturn(CORRECT_JWT_TOKEN);

        LoginResponseBody actualLoginResponseBody = authenticationService.login(USERNAME, PASSWORD_CORRECT);
        LoginResponseBody.MealtrackerUserToReturn actualUser = actualLoginResponseBody.getUser();
        Claims actualClaims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(actualUser.getToken()).getBody();

        verify(repository, times(1)).getUserWithUsername(USERNAME);
        verify(jwtUtilService, times(1)).generateToken(expectedUser);

        assertEquals(expectedUser.getFullName(), actualUser.getFullName());
        assertEquals(expectedUser.getUsername(), actualUser.getUsername());

        assertEquals(expectedUser.getUsername(), actualClaims.get(KEY_USERNAME));
        assertEquals((int) expectedUser.getId(), actualClaims.get(KEY_USER_ID));
        assertEquals(expectedUser.getRole().getNumValue(), actualClaims.get(KEY_ROLE));

    }

    @Test(expected = UsernameAlreadyExistsException.class)
    public void whenUsernameAlreadyExists_throwAnException() throws UsernameDoesNotExistException, UsernameAlreadyExistsException {

        SignUpRequestBody signUpRequestBody = new SignUpRequestBody(USERNAME, "anything", "anything");
        MealtrackerUser expectedUser = new MealtrackerUser(ID, USERNAME, PASSWORD_CORRECT, FULL_NAME, ROLE);

        when(repository.getUserWithUsername(USERNAME)).thenReturn(expectedUser);

        authenticationService.signUp(signUpRequestBody);

        verify(repository, times(1)).getUserWithUsername(USERNAME);

    }

    @Test
    public void whenUsernameDoesNotExist_returnSignUpResponseBody() throws UsernameDoesNotExistException, UsernameAlreadyExistsException {

        SignUpRequestBody signUpRequestBody = new SignUpRequestBody(USERNAME, PASSWORD_CORRECT, FULL_NAME);
        MealtrackerUser user = new MealtrackerUser(ID, USERNAME, PASSWORD_CORRECT, FULL_NAME, MealtrackerUser.Role.ADMIN);

        when(repository.getUserWithUsername(USERNAME)).thenThrow(UsernameDoesNotExistException.class);
        when(repository.createUser(signUpRequestBody.getUsername(), signUpRequestBody.getPassword(), signUpRequestBody.getFullName())).thenReturn(user);

        StandardResponseBody standardResponseBody = authenticationService.signUp(signUpRequestBody);

        verify(repository, times(1)).getUserWithUsername(USERNAME);
        verify(repository, times(1)).createUser(signUpRequestBody.getUsername(), signUpRequestBody.getPassword(), signUpRequestBody.getFullName());
        assertEquals(SignUpResponseBody.MESSAGE, standardResponseBody.getMessage());

    }

}
