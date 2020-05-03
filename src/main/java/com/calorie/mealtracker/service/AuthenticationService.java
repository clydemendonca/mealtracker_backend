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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class AuthenticationService {

    @Autowired
    private MealtrackerUserJpaRepository mealtrackerUserRepository;

    @Autowired
    private JwtUtilService jwtUtilService;

    public LoginResponseBody login(String username, String password) throws UsernameDoesNotExistException, IncorrectPasswordException {
        MealtrackerUser user = mealtrackerUserRepository.findByUsername(username);

        if (user == null) throw new UsernameDoesNotExistException();

        if (!password.equals(user.getEncryptedPassword())) throw new IncorrectPasswordException();

        LoginResponseBody loginResponseBody = new LoginResponseBody(user);
        loginResponseBody.setToken(jwtUtilService.generateToken(user));
        return loginResponseBody;
    }


    public StandardResponseBody signUp(SignUpRequestBody signUpRequestBody) throws UsernameAlreadyExistsException {

        MealtrackerUser user = mealtrackerUserRepository.findByUsername(signUpRequestBody.getUsername());

        if (user != null) throw new UsernameAlreadyExistsException();

        MealtrackerUser newUser = new MealtrackerUser(signUpRequestBody.getUsername(), signUpRequestBody.getPassword(), signUpRequestBody.getFullName(), MealtrackerUser.Role.USER);
        mealtrackerUserRepository.save(newUser);
        return new SignUpResponseBody();

    }

    public void setMealtrackerUserRepository(MealtrackerUserJpaRepository mealtrackerUserRepository) {
        this.mealtrackerUserRepository = mealtrackerUserRepository;
    }

    public void setJwtUtilService(JwtUtilService jwtUtilService) {
        this.jwtUtilService = jwtUtilService;
    }

}
