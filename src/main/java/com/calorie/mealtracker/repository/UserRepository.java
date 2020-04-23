package com.calorie.mealtracker.repository;

import com.calorie.mealtracker.error.UsernameDoesNotExistException;
import com.calorie.mealtracker.model.MealtrackerUser;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    public MealtrackerUser getUserWithUsername(String username) throws UsernameDoesNotExistException {
        return new MealtrackerUser(0, "", "", "", null);
    }

    public MealtrackerUser createUser(String username, String password, String fullName) {
        return new MealtrackerUser(0, username, password, fullName, MealtrackerUser.Role.ADMIN);
    }
}
