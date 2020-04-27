package com.calorie.mealtracker.service;

import com.calorie.mealtracker.error.IncorrectPasswordException;
import com.calorie.mealtracker.error.UsernameAlreadyExistsException;
import com.calorie.mealtracker.error.UsernameDoesNotExistException;
import com.calorie.mealtracker.model.MealtrackerUser;
import com.calorie.mealtracker.model.request.SignUpRequestBody;
import com.calorie.mealtracker.model.response.LoginResponseBody;
import com.calorie.mealtracker.model.response.SignUpResponseBody;
import com.calorie.mealtracker.model.response.StandardResponseBody;
import com.calorie.mealtracker.repository.MealtrackerUserJpaRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    private MealtrackerUserJpaRepository repository;

    @Mock
    private JwtUtilService jwtUtilService;

    @Before
    public void setup() {
        authenticationService = new AuthenticationService();
        authenticationService.setMealtrackerUserRepository(repository);
        authenticationService.setJwtUtilService(jwtUtilService);
    }

    @Test
    public void whenUsernameIsIncorrect_throwAnException() throws UsernameDoesNotExistException, IncorrectPasswordException {

        when(repository.findByUsername(USERNAME)).thenReturn(null);

        try {
            authenticationService.login(USERNAME, "");
        } catch (UsernameDoesNotExistException e) {

        }

        verify(repository, times(1)).findByUsername(USERNAME);

    }

    @Test
    public void whenUsernameCorrectAndPasswordIsIncorrect_throwAnException() throws UsernameDoesNotExistException, IncorrectPasswordException {

        MealtrackerUser user = new MealtrackerUser(ID, USERNAME, PASSWORD_CORRECT, FULL_NAME, ROLE);
        when(repository.findByUsername(USERNAME)).thenReturn(user);

        try {
            authenticationService.login(USERNAME, PASSWORD_INCORRECT);
        } catch (IncorrectPasswordException e) {

        }

        verify(repository, times(1)).findByUsername(USERNAME);

    }


    @Test
    public void whenUsernameAndPasswordAreCorrect_returnLoginResponseBody() throws UsernameDoesNotExistException, IncorrectPasswordException {

        MealtrackerUser expectedUser = new MealtrackerUser(ID, USERNAME, PASSWORD_CORRECT, FULL_NAME, ROLE);
        CORRECT_JWT_TOKEN = createJwtForTest(expectedUser);

        when(repository.findByUsername(USERNAME)).thenReturn(expectedUser);
        when(jwtUtilService.generateToken(eq(expectedUser))).thenReturn(CORRECT_JWT_TOKEN);

        LoginResponseBody actualLoginResponseBody = authenticationService.login(USERNAME, PASSWORD_CORRECT);
        LoginResponseBody.MealtrackerUserToReturn actualUser = actualLoginResponseBody.getUser();
        Claims actualClaims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(actualUser.getToken()).getBody();

        verify(repository, times(1)).findByUsername(USERNAME);
        verify(jwtUtilService, times(1)).generateToken(expectedUser);

        assertEquals(expectedUser.getFullName(), actualUser.getFullName());
        assertEquals(expectedUser.getUsername(), actualUser.getUsername());

        assertEquals(expectedUser.getUsername(), actualClaims.get(KEY_USERNAME));
        assertEquals((int) expectedUser.getId(), actualClaims.get(KEY_USER_ID));
        assertEquals(expectedUser.getRole().getNumValue(), actualClaims.get(KEY_ROLE));

    }

    private String createJwtForTest(MealtrackerUser mealtrackerUser) {

        Map<String, Object> claims = new HashMap<>();
        claims.put(KEY_USERNAME, mealtrackerUser.getUsername());
        claims.put(KEY_ROLE, mealtrackerUser.getRole().getNumValue());
        claims.put(KEY_USER_ID, mealtrackerUser.getId());

        return Jwts.builder().setClaims(claims).setSubject(mealtrackerUser.getUsername()).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();

    }

    @Test
    public void whenUsernameAlreadyExists_throwAnException() throws UsernameDoesNotExistException, UsernameAlreadyExistsException {

        SignUpRequestBody signUpRequestBody = new SignUpRequestBody(USERNAME, "anything", "anything");
        MealtrackerUser expectedUser = new MealtrackerUser(ID, USERNAME, PASSWORD_CORRECT, FULL_NAME, ROLE);

        when(repository.findByUsername(USERNAME)).thenReturn(expectedUser);

        try {
            authenticationService.signUp(signUpRequestBody);
        } catch (UsernameAlreadyExistsException e) {

        }

        verify(repository, times(1)).findByUsername(USERNAME);

    }

    @Test
    public void whenUsernameDoesNotExist_returnSignUpResponseBody() throws UsernameDoesNotExistException, UsernameAlreadyExistsException {

        SignUpRequestBody signUpRequestBody = new SignUpRequestBody(USERNAME, PASSWORD_CORRECT, FULL_NAME);
        MealtrackerUser user = new MealtrackerUser(ID, USERNAME, PASSWORD_CORRECT, FULL_NAME, MealtrackerUser.Role.ADMIN);

        when(repository.findByUsername(USERNAME)).thenReturn(null);
//        when(repository.save(eq(user))).thenReturn(user);

        StandardResponseBody standardResponseBody = authenticationService.signUp(signUpRequestBody);

        verify(repository, times(1)).findByUsername(USERNAME);
        verify(repository, times(1)).save(argThat(new MealtrackerUserArgumentMatchers(user)));
        assertEquals(SignUpResponseBody.MESSAGE, standardResponseBody.getMessage());

    }

    class MealtrackerUserArgumentMatchers implements ArgumentMatcher<MealtrackerUser> {

        private MealtrackerUser mealtrackerUser;

        public MealtrackerUserArgumentMatchers(MealtrackerUser mealtrackerUser) {
            this.mealtrackerUser = mealtrackerUser;
        }

        @Override
        public boolean matches(MealtrackerUser argument) {
            return mealtrackerUser.getUsername() == argument.getUsername() &&
                    mealtrackerUser.getEncryptedPassword() == argument.getEncryptedPassword() &&
                    mealtrackerUser.getRole().getNumValue() == argument.getRole().getNumValue();
        }

    }

}
