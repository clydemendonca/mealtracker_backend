package com.calorie.mealtracker.repository;

import com.calorie.mealtracker.model.MealtrackerUser;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MealtrackerUserJpaRepository extends MongoRepository<MealtrackerUser, String> {

    MealtrackerUser findByUsername(String username);

}
