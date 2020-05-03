package com.calorie.mealtracker.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class MealsForUserAggregation {

    @JsonProperty("_id")
    private Date dateString;
    private float total;

    public MealsForUserAggregation(Date dateString, float total) {
        this.dateString = dateString;
        this.total = total;
    }

    public Date getDateString() {
        return dateString;
    }

    public void setDateString(Date dateString) {
        this.dateString = dateString;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
