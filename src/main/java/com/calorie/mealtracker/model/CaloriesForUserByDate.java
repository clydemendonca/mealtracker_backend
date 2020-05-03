package com.calorie.mealtracker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

public class CaloriesForUserByDate {

    @Field("_id")
    private String dateString;
    private float total;

    public CaloriesForUserByDate(String dateString, float total) {
        this.dateString = dateString;
        this.total = total;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
