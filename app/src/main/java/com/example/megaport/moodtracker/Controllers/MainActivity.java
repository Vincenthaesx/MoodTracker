package com.example.megaport.moodtracker.Controllers;


import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.example.megaport.moodtracker.Model.SwipeDirectionListener;
import com.example.megaport.moodtracker.R;

public class MainActivity extends AppCompatActivity {

    public static int mood;


    // Variable View
    ImageButton btnHistory;
    ImageButton btnAddMessage;
    RelativeLayout layout;

    // Variable sound
    MediaPlayer mediaPlayer;


    // Variable table
    public static final int tableImgSmiley[] = {R.drawable.smileysad, R.drawable.smileydisappointed, R.drawable.smileynormal, R.drawable.smileyhappy, R.drawable.smileysuperhappy};
    public static final int tableBackgroundColor[] = {R.color.color_sad, R.color.color_disappointed, R.color.color_normal, R.color.color_happy, R.color.color_superhappy};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // findViewById ------------------------------------------------------------
        btnAddMessage = findViewById(R.id.btn_add_message);
        btnHistory = findViewById(R.id.btn_history);

        swipe();





    }

    public void swipe() {
        layout = findViewById(R.id.main_activity_layout);

        layout.setOnTouchListener(new SwipeDirectionListener(MainActivity.this) {
            ImageView imgSmiley = findViewById(R.id.img_smiley);
            RelativeLayout layout = findViewById(R.id.main_activity_layout);


            public void onUpSwipe() {
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.up);
                if (mood != 4) {
                    mood++;
                    mediaPlayer.start();
                }
                imgSmiley.setImageResource(tableImgSmiley[mood]);
                layout.setBackgroundResource(tableBackgroundColor[mood]);

            }

            public void onDownSwipe() {
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.down);
                if (mood != 0) {
                    mood--;
                    mediaPlayer.start();
                }
                imgSmiley.setImageResource(tableImgSmiley[mood]);
                layout.setBackgroundResource(tableBackgroundColor[mood]);

            }

        });

    }

}
