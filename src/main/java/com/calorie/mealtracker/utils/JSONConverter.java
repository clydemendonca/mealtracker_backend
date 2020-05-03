package com.calorie.mealtracker.utils;

import com.calorie.mealtracker.model.MealtrackerUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.Map;

public class JSONConverter {

    public static String asJsonString(final Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    public static JsonObject fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, JsonObject.class);
    }

}
