package com.calorie.mealtracker.service;

import com.calorie.mealtracker.error.IncorrectPasswordException;
import com.calorie.mealtracker.error.UsernameDoesNotExistException;
import com.calorie.mealtracker.model.MealtrackerUser;
import com.calorie.mealtracker.model.response.LoginResponseBody;
import com.calorie.mealtracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    public LoginResponseBody login(String username, String password) throws UsernameDoesNotExistException, IncorrectPasswordException {
        MealtrackerUser user = userRepository.getUserWithUsername(username);
        if (!password.equals(user.getEncryptedPassword())) {
            throw new IncorrectPasswordException();
        }
        return new LoginResponseBody(user);
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
