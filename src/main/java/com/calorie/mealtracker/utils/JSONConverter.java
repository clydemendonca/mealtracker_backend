package com.calorie.mealtracker.utils;

import com.calorie.mealtracker.model.MealtrackerUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.util.Map;

public class JSONConverter {

    public static String asJsonString(final Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

}
