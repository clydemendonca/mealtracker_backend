package com.calorie.mealtracker.service;

import com.calorie.mealtracker.error.IncorrectPasswordException;
import com.calorie.mealtracker.error.UsernameDoesNotExistException;
import com.calorie.mealtracker.model.MealtrackerUser;
import com.calorie.mealtracker.model.response.LoginResponseBody;
import com.calorie.mealtracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtilService jwtUtilService;

    public LoginResponseBody login(String username, String password) throws UsernameDoesNotExistException, IncorrectPasswordException {
        MealtrackerUser user = userRepository.getUserWithUsername(username);
        if (!password.equals(user.getEncryptedPassword())) {
            throw new IncorrectPasswordException();
        }
        LoginResponseBody loginResponseBody = new LoginResponseBody(user);
        loginResponseBody.setToken(jwtUtilService.generateToken(user));
        return loginResponseBody;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MealtrackerUser mealtrackerUser = null;
        try {

            mealtrackerUser = userRepository.getUserWithUsername(username);
            return new User(mealtrackerUser.getUsername(), mealtrackerUser.getEncryptedPassword(), new ArrayList<>());

        } catch (UsernameDoesNotExistException e) {
            e.printStackTrace();
            return null;
        }

    }
}
