package com.example.megaport.moodtracker.Model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.example.megaport.moodtracker.Controllers.MainActivity;
import com.example.megaport.moodtracker.R;
import static com.example.megaport.moodtracker.Controllers.MainActivity.tableBackgroundColor;

public class MyBroadcastReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        float tableWidthMood[] = {SharedPreferencesUtils.getWidth(context) * 0.25f, SharedPreferencesUtils.getWidth(context) * 0.37f, SharedPreferencesUtils.getWidth(context) * 0.50f, SharedPreferencesUtils.getWidth(context) * 0.75f, SharedPreferencesUtils.getWidth(context)};

        if (SharedPreferencesUtils.containsMood(context)) {
            MainActivity.moodData.add(new MoodData(" ", SharedPreferencesUtils.getMessage(context), tableBackgroundColor[SharedPreferencesUtils.getMood(context)], tableWidthMood[SharedPreferencesUtils.getMood(context)]));
        } else {

            MainActivity.moodData.add(new MoodData(" ", " ", R.color.color_happy, SharedPreferencesUtils.getWidth(context)));
        }

        SharedPreferencesUtils.saveArrayList(context);
        resetMood(context);
    }

    // resets mood and message in sharedPreferences
    public void resetMood(Context context) {
        SharedPreferencesUtils.removeMood(context, SharedPreferencesUtils.MY_FILE, SharedPreferencesUtils.KEY_MOOD);
        SharedPreferencesUtils.removeMood(context, SharedPreferencesUtils.MY_FILE, SharedPreferencesUtils.KEY_MESSAGE);
    }

}