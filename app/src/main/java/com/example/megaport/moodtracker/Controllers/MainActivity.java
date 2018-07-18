package com.example.megaport.moodtracker.Controllers;


import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.megaport.moodtracker.Model.MoodData;
import com.example.megaport.moodtracker.Model.MyBroadcastReceiver;
import com.example.megaport.moodtracker.Model.SharedPreferencesUtils;
import com.example.megaport.moodtracker.Model.SwipeDirectionListener;
import com.example.megaport.moodtracker.R;

import java.util.ArrayList;
import java.util.Calendar;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    public static int mood;

    // Variable View
    ImageButton btnAddMessage;
    RelativeLayout layout;

    // Variable sound
    MediaPlayer mediaPlayer;

    // ArrayList
    public static ArrayList<MoodData> moodData;

    static {
        moodData = new ArrayList<>();
    }

    // Variable table
    public static final int tableImgSmiley[] = {R.drawable.smileysad, R.drawable.smileydisappointed, R.drawable.smileynormal, R.drawable.smileyhappy, R.drawable.smileysuperhappy};
    public static final int tableBackgroundColor[] = {R.color.color_sad, R.color.color_disappointed, R.color.color_normal, R.color.color_happy, R.color.color_superhappy};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

// findViewById ------------------------------------------------------------
        btnAddMessage = findViewById(R.id.btn_add_message);
        ImageButton btnHistory = findViewById(R.id.btn_history);

// findViewById ------------------------------------------------------------

        if (SharedPreferencesUtils.containsMood(this)) {
            mood = SharedPreferencesUtils.getMood(this);
        } else {
            mood = 3;
        }

        initializeData();

        btnAddMessage.setOnClickListener(this);
        btnHistory.setOnClickListener(this);

        SharedPreferencesUtils.saveMood(MainActivity.this, mood);

        swipe();

        AlarmMidnight(this);
    }


    // When we swipe, this method (up = mood++ or down = mood--) plays a sound and change imgSmiley and BackgroundColor depending on mood value.

    @SuppressLint("ClickableViewAccessibility")
    public void swipe() {
        layout = findViewById(R.id.main_activity_layout);

        layout.setOnTouchListener(new SwipeDirectionListener(MainActivity.this) {
            ImageView imgSmiley = findViewById(R.id.img_smiley);
            RelativeLayout layout = findViewById(R.id.main_activity_layout);


            public void onUpSwipe() {
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.up);
                if (mood != 4) {
                    mood++;
                    SharedPreferencesUtils.saveMood(MainActivity.this, mood);
                    mediaPlayer.start();
                }
                imgSmiley.setImageResource(tableImgSmiley[mood]);
                layout.setBackgroundResource(tableBackgroundColor[mood]);

            }

            public void onDownSwipe() {
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.down);
                if (mood != 0) {
                    mood--;
                    SharedPreferencesUtils.saveMood(MainActivity.this, mood);
                    mediaPlayer.start();
                }
                imgSmiley.setImageResource(tableImgSmiley[mood]);
                layout.setBackgroundResource(tableBackgroundColor[mood]);

            }

        });


    }

    // This method opens a popup to write a note
    public void showDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        @SuppressLint("InflateParams")

        final View dialogView = inflater.inflate(R.layout.popup_add_message, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextMessage = dialogView.findViewById(R.id.edittext_message);

        dialogBuilder.setTitle("Ajouter une note");
        dialogBuilder.setPositiveButton("valider", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                SharedPreferencesUtils.saveMessage(MainActivity.this, editTextMessage.getText().toString());
            }
        });
        dialogBuilder.setNegativeButton("annuler", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    // This method listens if you click on one of the two buttons
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_message:
                showDialog();
                break;
            case R.id.btn_history:
                Intent myIntent = new Intent(MainActivity.this, History.class);
                startActivity(myIntent);
                break;
        }
    }

    /*
    This method initializes the mood value if a backup exists otherwise mood = 3
    also initializes DisplayMetrics, my variable myMetrics take a width of screen and save the width in SharedPreferences
    */
    private void initializeData() {

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float myMetrics = metrics.widthPixels;
        SharedPreferencesUtils.saveWidth(MainActivity.this, myMetrics);



    }

    //This method executes the code in MyBroadcastReceiver at midnight
    private void AlarmMidnight(Context context) {

        AlarmManager alarmManager;
        PendingIntent pendingIntent;

        Calendar calendar = Calendar.getInstance();
        calendar.set(2018, 6, 18, 23, 59);

        if (Calendar.getInstance().after(calendar)) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, MyBroadcastReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

    }
}
