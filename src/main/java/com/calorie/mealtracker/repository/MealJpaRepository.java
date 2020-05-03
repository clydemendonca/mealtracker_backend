package com.calorie.mealtracker.repository;

import com.calorie.mealtracker.model.Meal;
import com.calorie.mealtracker.model.CaloriesForUserByDate;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface MealJpaRepository extends MongoRepository<Meal, String> {

    @Aggregation({"{$match: {" +
            "  userId: ObjectId(\"?0\")," +
            "  $and: [{" +
            "    date : { $gte: ?1 }," +
            "    date : { $lte: ?2 }" +
            "  }]" +
            "}}", "{$group: {" +
            "  _id: {  $dateToString: { format: \"%Y-%m-%d\", date: \"$date\" } }," +
            "  total: { $sum: \"$calories\" }" +
            "}}"})
    List<CaloriesForUserByDate> getDaywiseCalorieIntakeForUser(String userId, Date fromDate, Date toDate);

}
