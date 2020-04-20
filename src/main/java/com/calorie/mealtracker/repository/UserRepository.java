package com.calorie.mealtracker.repository;

import com.calorie.mealtracker.error.UsernameDoesNotExistException;
import com.calorie.mealtracker.model.MealtrackerUser;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    public MealtrackerUser getUserWithUsername(String username) throws UsernameDoesNotExistException {
        return new MealtrackerUser("", "", "");
    }

}
