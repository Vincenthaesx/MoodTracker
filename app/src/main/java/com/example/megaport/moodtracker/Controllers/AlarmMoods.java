package com.example.megaport.moodtracker.Controllers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import com.example.megaport.moodtracker.Model.History;
import com.example.megaport.moodtracker.Model.MoodsSave;
import com.google.gson.Gson;
import static android.content.ContentValues.TAG;


public class AlarmMoods extends BroadcastReceiver {


    //mood save in the history and reset mood for MainActivity

    @Override
    public void onReceive(Context context, Intent intent) {

        History HistoryList = loadHistory(context);
        MoodsSave userMoodSave = loadMood(context);

        if(HistoryList == null){

            HistoryList = new History();

        }

        HistoryList.addList(userMoodSave);


        if (HistoryList.getSizeList() > 7){

            HistoryList.removeList(0);

        }

        saveHistory(context, HistoryList);
        resetMood(context);

        //restart MainActivity

        if (MainActivity.getInstance() != null) {
            MainActivity.getInstance().onCreate(null);
        }


    }



    protected void saveHistory(Context context, History HistoryList){

        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        Gson gson = new Gson();

        SharedPreferences.Editor prefsEditor = mPreferences.edit();
        String SaveHistoryJson = gson.toJson(HistoryList);
        prefsEditor.putString("HistoryList", SaveHistoryJson);
        prefsEditor.apply();
        Log.i(TAG, "onReceive: AlarmeMoodsClock" + HistoryList.toString());

    }

    protected History loadHistory(Context context){

        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        Gson gson = new Gson();

        String LoadHistoryJson = mPreferences.getString("HistoryList", "");

        return gson.fromJson(LoadHistoryJson, History.class);
    }

    protected MoodsSave loadMood(Context context){

        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String jsonMood = mPreferences.getString("PrefMoodUserSave", "");

        return gson.fromJson(jsonMood, MoodsSave.class);
    }

    protected void resetMood(Context context){

        MoodsSave userMoodSave = new MoodsSave();

        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        SharedPreferences.Editor prefsEditor = mPreferences.edit();
        String SaveHistoryJson = gson.toJson(userMoodSave);
        prefsEditor.putString("PrefMoodUserSave", SaveHistoryJson);
        prefsEditor.apply();
    }



}