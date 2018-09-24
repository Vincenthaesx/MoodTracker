package com.example.megaport.moodtracker.Controllers;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.example.megaport.moodtracker.Model.MoodsSave;
import com.example.megaport.moodtracker.R;
import com.google.gson.Gson;
import java.util.Calendar;
import java.util.Date;

//created by vincent, 26/07/2018

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    //variable


    private static final String TAG = "activity_main";

    private RelativeLayout mBackground;
    private ImageView mImage;

    public ImageButton mnoteIcone;

    private GestureDetector gDetector;

    private int moodNumber = 3;
    private String noteUser = "";

    private SharedPreferences mPreferences;
    private MoodsSave userMoodSave = new MoodsSave();

    //variable tab

    private int[] tabBackgroundColor = {R.color.faded_red, R.color.warm_grey, R.color.cornflower_blue_65, R.color.light_sage, R.color.banana_yellow};
    private int[] image = {R.drawable.smileysad, R.drawable.smileydisappointed, R.drawable.smileynormal, R.drawable.smileyhappy, R.drawable.smileysuperhappy};
    private int[] tabSound = {R.raw.soundsad, R.raw.sounddisappointed, R.raw.soundnormal, R.raw.soundhappy, R.raw.soundsuperhappy};


    @SuppressLint("StaticFieldLeak")
    private static MainActivity mMainActivity;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate: version android " + Build.VERSION.SDK_INT);
        if (savedInstanceState != null) {
            moodNumber = savedInstanceState.getInt("user_mood");
        }




        gDetector = new GestureDetector(this);
        mBackground = findViewById(R.id.background);
        mImage = findViewById(R.id.imageSmailly);
        ImageButton mhistory = findViewById( R.id.history );
        mnoteIcone =findViewById(R.id.note);
        mnoteIcone.setOnClickListener(mnoteClick);
        mhistory.setOnClickListener(mhistoryClick);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mMainActivity = MainActivity.this;


        AlarmAtMidnight();

        String preferencesTest = mPreferences.getString("PrefMoodUserSave", null);

        if (preferencesTest != null || userMoodSave.getMoodsNumber() == -1) loadMood();

        mBackground.setBackgroundColor(getResources().getColor(tabBackgroundColor[moodNumber]));
        mImage.setImageResource(image[moodNumber]);

    }

    //Alarm to record at midnight in the history, use AlarmMoods.class

    public void AlarmAtMidnight() {

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Date dat = new Date();
        Calendar calendar = Calendar.getInstance();
        Calendar cal_now = Calendar.getInstance();
        cal_now.setTime(dat);

        calendar.setTime(dat);
        calendar.set(Calendar.HOUR_OF_DAY,14);
        calendar.set(Calendar.MINUTE,50);

        if(calendar.before(cal_now)){
            calendar.add(Calendar.DATE,1);
        }

        Intent myIntent = new Intent(MainActivity.this, AlarmMoods.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast( this, 0, myIntent, 0 );

        if (Build.VERSION.SDK_INT > 19) {

            assert manager != null;
            manager.setRepeating(AlarmManager.RTC_WAKEUP, new Date().getTime(), AlarmManager.INTERVAL_DAY, pendingIntent);
            Log.i(TAG, "startAlarm: 1 ");
        }

        else {

            assert manager != null;
            manager.setRepeating(AlarmManager.RTC_WAKEUP, new Date().getTime(), AlarmManager.INTERVAL_DAY, pendingIntent);
            Log.i(TAG, "startAlarm: 2");
        }
    }

    public static MainActivity getInstance(){

        return mMainActivity;
    }


    //save moodNumber for rotation

    @Override
    protected void onSaveInstanceState(Bundle stateSaved) {
        super.onSaveInstanceState(stateSaved);
        stateSaved.putInt("user_mood", moodNumber);
        Log.i(TAG, "onSaveInstanceState: ");

    }

    //Restore moodNumber for rotation

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // restore saved values
        moodNumber = savedInstanceState.getInt("user_mood");
        Log.i(TAG, "onRestoreInstanceState: ");

    }

    //save object Mood at preferences

    protected void saveMood(){

        SharedPreferences.Editor prefsEditor = mPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(userMoodSave);
        prefsEditor.putString("PrefMoodUserSave", json);
        prefsEditor.apply();

        Log.i(TAG, "PrefMoodUserSave: ");

    }

    //load object Mood at preferences

    protected void loadMood(){

        Gson gson = new Gson();
        String json = mPreferences.getString("PrefMoodUserSave", "");
        MoodsSave moodsSaveLoad = gson.fromJson(json, MoodsSave.class);

        moodNumber = moodsSaveLoad.getMoodsNumber();
        userMoodSave.setComment(moodsSaveLoad.getComment());

        Log.i(TAG, "PrefMoodUserLoad: " + userMoodSave.getComment() + " " + moodNumber);

    }

    //change background color and change moods picture

    protected void setMoodsScreen(){

        mBackground.setBackgroundColor(getResources().getColor(tabBackgroundColor[moodNumber]));
        mImage.setImageResource(image[moodNumber]);
        userMoodSave.setMoodsNumber(moodNumber);
    }


    //detect click on the button note

    private View.OnClickListener mnoteClick = new View.OnClickListener() {
        public void onClick(View v) {

            userMoodSave.setMoodsNumber(moodNumber);

            saveMood();


            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Commentaire");


            //create area text input

            final EditText input = new EditText(MainActivity.this);
            input.setInputType(InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE);
            builder.setView(input);

            //save comment user

            builder.setPositiveButton("VALIDER", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.i(TAG, "onClick: VALIDER");
                    noteUser= input.getText().toString();

                    if (noteUser.length() > 0){

                        userMoodSave.setComment(noteUser);
                        Log.i(TAG, "save comment on userMoodSave => " + userMoodSave.getComment());
                        saveMood();
                    }

                    else {
                        userMoodSave.setComment(null);
                    }
                }
            });

            //cancel, no saving comment

            builder.setNegativeButton("ANNULER", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.i(TAG, "onClick: ANNULER");
                    dialog.cancel();

                    userMoodSave.setMoodsNumber(moodNumber);
                }
            });

            builder.show();
        }
    };


    //detect click on the button history

    private View.OnClickListener mhistoryClick = new View.OnClickListener() {
        public void onClick(View v) {

            Log.i(TAG, "onClick: mhistoryClick");

            Intent historyActivity = new Intent(MainActivity.this, HistoryActivity.class);
            saveMood();


            Log.i(TAG, "PrefMoodUserSave: ");



            startActivity(historyActivity);

        }
    };

    //GestureDetector
    //detects the interaction between user and screen

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return gDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }


    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }


    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    //detects user fling on screen

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {

        //if user go up
        if (motionEvent.getY() < motionEvent1.getY())
        {

            //if there are others moods show next moods
            if (moodNumber < 4)
            {
                moodNumber++;
                setMoodsScreen();
                saveMood();
            }
        }

        //if user go down

        else if(motionEvent.getY() > motionEvent1.getY()){

            //if there are others moods show next moods
            if(moodNumber > 0)
            {
                moodNumber--;
                setMoodsScreen();
                saveMood();
            }
        }

        //play sound Moods by MediaPlayer

        MediaPlayer playMoods;

        playMoods = MediaPlayer.create(this, tabSound[moodNumber]);
        playMoods.start();


        Log.i(TAG, "var mood number " + moodNumber );
        return false;

    }


}
