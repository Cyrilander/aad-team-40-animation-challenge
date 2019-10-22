package com.cyril.animationalc;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class IntroActivity extends AppCompatActivity implements Animator.AnimatorListener {

    private int width;
    private ImageView mImage;
    private AnimatorSet rotateTranslateSet, scaleSet, player, scaleFade;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        mImage = findViewById(R.id.imageAnimate);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        rotateTranslateSet = new AnimatorSet();
        scaleSet = new AnimatorSet();
        player = new AnimatorSet();
        scaleFade = new AnimatorSet();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                animateView();
            }
        }, 1000);


    }

    private void animateView() {
        ObjectAnimator translateAnimator = ObjectAnimator.ofFloat(mImage, "translationX", 0, width / 2 + (mImage.getWidth() / 2));
        int twoSeconds = 2000;
        translateAnimator.setDuration(twoSeconds);
        ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(mImage, "rotationY", 0, 1440);
        rotateAnimator.setDuration(twoSeconds);

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(mImage, "scaleX", 1, 3f);
        int halfSecond = 500;
        scaleX.setDuration(halfSecond).setInterpolator(new OvershootInterpolator());
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(mImage, "scaleY", 1, 3f);
        scaleY.setDuration(halfSecond).setInterpolator(new OvershootInterpolator());

        ObjectAnimator fade = ObjectAnimator.ofFloat(mImage, "alpha", 1, 0);
        int oneSecond = 1000;
        fade.setDuration(oneSecond);

        ObjectAnimator endScaleX = ObjectAnimator.ofFloat(mImage, "scaleX", 3, 5.5f);
        endScaleX.setDuration(oneSecond);
        ObjectAnimator endScaleY = ObjectAnimator.ofFloat(mImage, "scaleY", 3, 5.5f);
        endScaleY.setDuration(oneSecond);

        rotateTranslateSet.playTogether(translateAnimator, rotateAnimator);
        scaleSet.playTogether(scaleX, scaleY);

        scaleFade.playTogether(endScaleX, endScaleY, fade);
        scaleFade.setStartDelay(1800);

        player.playSequentially(rotateTranslateSet, scaleSet, scaleFade);
        player.addListener(IntroActivity.this);
        player.start();
    }


    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}
