package com.example.test.utils.other;

import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;

/**
 * Created by guizhen on 2016/11/21.
 */

public class JsonUtils {

    private static Gson gson = new Gson();

    public static int getInt(String json, String key) {
        int result = -1;
        try {
            JSONObject jsonObject = new JSONObject(json);
            result = jsonObject.getInt(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static <T> T fromJson(String json, Class<T> tClass) {
        T result = null;
        try {
            result = gson.fromJson(json, tClass);
        } catch (Exception e) {
        }
        return result;
    }

    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    public static JSONObject toJsonObject(Object object) {
        try {
            return new JSONObject(toJson(object));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONArray toJsonArray(List list) {
        try {
            return new JSONArray(toJson(list));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
