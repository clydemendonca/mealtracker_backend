package com.calorie.mealtracker.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

@Document
public class Meal {

    @MongoId
    private String id;

    private ObjectId userId;

    private Date date;
    private String mealName;
    private float calories;

    public Meal() {
    }

    public Meal(String userId, Date date, String mealName, float calories) {
        this.userId = new ObjectId(userId);
        this.date = date;
        this.mealName = mealName;
        this.calories = calories;
    }

    public Meal(String id, String userId, Date date, String mealName, float calories) {
        this.id = id;
        this.userId = new ObjectId(userId);
        this.date = date;
        this.mealName = mealName;
        this.calories = calories;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId.toHexString();
    }

    public void setUserId(String userId) {
        this.userId = new ObjectId(userId);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public float getCalories() {
        return calories;
    }

    public void setCalories(float calories) {
        this.calories = calories;
    }
}
