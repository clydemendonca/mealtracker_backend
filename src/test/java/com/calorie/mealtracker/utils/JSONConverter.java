package com.calorie.mealtracker.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class JSONConverter {

    public static String asJsonString(final Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

}
