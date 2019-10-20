package com.cyril.animationalc;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class IntroActivity extends AppCompatActivity {

    private int width;
    private ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        mImage = findViewById(R.id.imageAnimate);


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator translateAnimator = ObjectAnimator.ofFloat(mImage, "translationX", 0, width / 2 + 70);
                translateAnimator.setDuration(2000).start();
            }
        }, 1000);


    }


}
