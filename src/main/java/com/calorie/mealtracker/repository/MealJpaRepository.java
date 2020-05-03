package com.calorie.mealtracker.repository;

import com.calorie.mealtracker.model.Meal;
import com.calorie.mealtracker.model.MealsForUserAggregation;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface MealJpaRepository extends MongoRepository<Meal, String> {

    @Aggregation({"{$match: {" +
            "  userId: ObjectId(\"?0\")," +
            "  $and: [{" +
            "    date : { $lte: ?1 }," +
            "    date : { $gte: ?2 }" +
            "  }]" +
            "}}", "{$group: {" +
            "  _id: {  $dateToString: { format: \"%Y-%m-%d\", date: \"$date\" } }," +
            "  total: { $sum: \"$calories\" }" +
            "}}"})
    List<MealsForUserAggregation> getMealsForUserAggregation(String userId, Date fromDate, Date toDate);

}
