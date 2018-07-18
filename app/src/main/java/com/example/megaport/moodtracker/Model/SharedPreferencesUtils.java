package com.example.megaport.moodtracker.Model;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.example.megaport.moodtracker.Controllers.MainActivity.moodData;


public class SharedPreferencesUtils {

    static final String MY_FILE = "MySharedPreference.xml";
    static final String KEY_MESSAGE = "KEY_MESSAGE";
    static final String KEY_MOOD = "KEY_MOOD";
    private static final String KEY_WIDTH = "KEY_WIDTH";
    private static final String KEY_LIST = "KEY_LIST";

    public static void saveMessage(Context context, String message) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_MESSAGE, message);
        editor.apply();
    }

    static String getMessage(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_FILE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_MESSAGE, "");
    }


    public static void saveMood(Context context, int mood) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_MOOD, mood);
        editor.apply();
    }

    public static int getMood(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_FILE, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_MOOD, 0);
    }

    static void removeMood(Context context, String prefsName, String key) {
        SharedPreferences preferences = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        editor.apply();
    }

    public static boolean containsMood(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_FILE, Context.MODE_PRIVATE);
        return sharedPreferences.contains(KEY_MOOD);
    }

    public static void saveWidth(Context context, float width) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(KEY_WIDTH, width);
        editor.apply();
    }

    public static float getWidth(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_FILE, Context.MODE_PRIVATE);
        return sharedPreferences.getFloat(KEY_WIDTH, 0.0f);
    }

    static void saveArrayList(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(moodData);
        editor.putString(KEY_LIST, json);
        editor.apply();
    }

    public static void getArrayList(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_FILE, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(KEY_LIST, null);
        Type type = new TypeToken<ArrayList<MoodData>>() {
        }.getType();
        moodData = gson.fromJson(json, type);
    }

    public static boolean containsArrayList(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_FILE, Context.MODE_PRIVATE);
        return sharedPreferences.contains(KEY_LIST);
    }


}