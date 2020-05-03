package com.calorie.mealtracker.service;

import com.calorie.mealtracker.error.UsernameDoesNotExistException;
import com.calorie.mealtracker.model.Meal;
import com.calorie.mealtracker.model.MealtrackerUser;
import com.calorie.mealtracker.model.request.UpdateMealtackerUserRequestBody;
import com.calorie.mealtracker.repository.MealtrackerUserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private MealtrackerUserJpaRepository mealtrackerUserRepository;

    public UserDetails loadUserByUsername(String username) {
        MealtrackerUser mealtrackerUser = mealtrackerUserRepository.findByUsername(username);
        if (mealtrackerUser == null) throw new UsernameNotFoundException("Username does not exist.");
        return new User(mealtrackerUser.getUsername(), mealtrackerUser.getEncryptedPassword(), new ArrayList<>());
    }

    public MealtrackerUser loadMealtrackerUserByUsername(String username) {
        MealtrackerUser mealtrackerUser = mealtrackerUserRepository.findByUsername(username);
        if (mealtrackerUser == null) throw new UsernameNotFoundException("Username does not exist.");
        return mealtrackerUser;
    }

    public void updateUserInfo(String username, UpdateMealtackerUserRequestBody requestBody) {
        MealtrackerUser mealtrackerUser = mealtrackerUserRepository.findByUsername(username);
        mealtrackerUser.setFullName(requestBody.getFullName());
        mealtrackerUser.setDailyCalorieIntake(requestBody.getDailyCalorieIntake());
        mealtrackerUserRepository.save(mealtrackerUser);
    }

}
