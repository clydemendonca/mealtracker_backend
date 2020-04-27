package com.calorie.mealtracker.repository;

import com.calorie.mealtracker.error.UsernameDoesNotExistException;
import com.calorie.mealtracker.model.MealtrackerUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

public interface MealtrackerUserJpaRepository extends MongoRepository<MealtrackerUser, Long> {

    MealtrackerUser findByUsername(String username);

}
