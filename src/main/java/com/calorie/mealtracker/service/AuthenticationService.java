package com.calorie.mealtracker.service;

import com.calorie.mealtracker.error.IncorrectPasswordException;
import com.calorie.mealtracker.error.UsernameAlreadyExistsException;
import com.calorie.mealtracker.error.UsernameDoesNotExistException;
import com.calorie.mealtracker.model.MealtrackerUser;
import com.calorie.mealtracker.model.request.SignUpRequestBody;
import com.calorie.mealtracker.model.response.LoginResponseBody;
import com.calorie.mealtracker.model.response.SignUpResponseBody;
import com.calorie.mealtracker.model.response.StandardResponseBody;
import com.calorie.mealtracker.repository.MealtrackerUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private MealtrackerUserRepository mealtrackerUserRepository;

    @Autowired
    private JwtUtilService jwtUtilService;

    public LoginResponseBody login(String username, String password) throws UsernameDoesNotExistException, IncorrectPasswordException {
        MealtrackerUser user = mealtrackerUserRepository.getUserWithUsername(username);
        if (!password.equals(user.getEncryptedPassword())) {
            throw new IncorrectPasswordException();
        }
        LoginResponseBody loginResponseBody = new LoginResponseBody(user);
        loginResponseBody.setToken(jwtUtilService.generateToken(user));
        return loginResponseBody;
    }


    public StandardResponseBody signUp(SignUpRequestBody signUpRequestBody) throws UsernameAlreadyExistsException {
        try {
            mealtrackerUserRepository.getUserWithUsername(signUpRequestBody.getUsername());
            throw new UsernameAlreadyExistsException();
        } catch (UsernameDoesNotExistException e) {
            mealtrackerUserRepository.createUser(signUpRequestBody.getUsername(), signUpRequestBody.getPassword(), signUpRequestBody.getFullName());
            return new SignUpResponseBody();
        }
    }

    public void setMealtrackerUserRepository(MealtrackerUserRepository mealtrackerUserRepository) {
        this.mealtrackerUserRepository = mealtrackerUserRepository;
    }

    public void setJwtUtilService(JwtUtilService jwtUtilService) {
        this.jwtUtilService = jwtUtilService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MealtrackerUser mealtrackerUser = null;
        try {

            mealtrackerUser = mealtrackerUserRepository.getUserWithUsername(username);
            return new User(mealtrackerUser.getUsername(), mealtrackerUser.getEncryptedPassword(), new ArrayList<>());

        } catch (UsernameDoesNotExistException e) {
            e.printStackTrace();
            return null;
        }

    }


}
