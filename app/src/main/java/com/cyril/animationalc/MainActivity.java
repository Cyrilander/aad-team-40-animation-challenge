package com.cyril.animationalc;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.animation.LinearInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements Animator.AnimatorListener {

    private ImageView earth;
    private String[] continents = {"Asia", "Africa", "North America", "South America", "Antartica", "Europe",
            "Australia"};
    private ListView continentsList;
    private int margin16;
    private int margin10;
    private int actionBarHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        earth = findViewById(R.id.earth);
        continentsList = findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, continents);
        continentsList.setAdapter(adapter);
        float density = getResources().getDisplayMetrics().density;
        margin16 = (int) (16 * density + 0.5f);
        margin10 = (int) (10 * density + 0.5f);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                earth.animate().setDuration(1000).alpha(1).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        animateEarth();
                    }
                });
            }
        }, 1000);

    }

    private void animateEarth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int halfWidth = displayMetrics.widthPixels / 2;
        int halfWindowHeight = displayMetrics.heightPixels / 2;
        TypedValue tv = new TypedValue();
        actionBarHeight = -1;
        if (getTheme().resolveAttribute(R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }
        int reduceActionBarHeight = actionBarHeight == -1 ? 200 : actionBarHeight;
        int halfSmallEarthHeight =  earth.getHeight() / 4;

        int requiredTranslationY = halfWindowHeight - (reduceActionBarHeight + halfSmallEarthHeight + margin10);

        Animator rotator = AnimatorInflater.loadAnimator(this, R.animator.rotate);
        AnimatorSet player = new AnimatorSet();
        AnimatorSet scaleSet = new AnimatorSet();

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(earth, "scaleX", 1, 0.5f)
                .setDuration(1000);

        ObjectAnimator scaleY = ObjectAnimator.ofFloat(earth, "scaleY", 1, 0.5f)
                .setDuration(1000);

        ObjectAnimator translation = ObjectAnimator.ofFloat(earth, "translationY", 0, -requiredTranslationY)
                .setDuration(1000);
        int halfSmallEarthWidth =  earth.getWidth() / 4;
        int requiredTranslationX = halfWidth - (halfSmallEarthWidth + margin16);

        ObjectAnimator translationX = ObjectAnimator.ofFloat(earth, "translationX", 0, -requiredTranslationX)
                .setDuration(1000);

        scaleSet.playTogether(translation, scaleX, scaleY);
        scaleSet.setStartDelay(2000);
        player.playSequentially(scaleSet, translationX);
        player.addListener(this);

        rotator.setTarget(earth);
        rotator.setInterpolator(new LinearInterpolator());
        rotator.start();
        player.start();
    }


    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        int smallEarthHeightAndMargin = (int) (earth.getHeight() * earth.getScaleY()) + margin16 * 2;
        if (actionBarHeight > 135) {
            smallEarthHeightAndMargin = smallEarthHeightAndMargin + 10;
            if (actionBarHeight > 175)
                smallEarthHeightAndMargin = smallEarthHeightAndMargin + 10;

        }
        findViewById(R.id.linearlayout).setPadding(0, smallEarthHeightAndMargin, 0, 0);

        TextView worldTxt = findViewById(R.id.world_txt);
        worldTxt.animate().setDuration(500).alpha(1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                continentsList.animate().setDuration(500).alpha(1).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        final TextView lorem = findViewById(R.id.lorem);
                        lorem.animate().setDuration(1000).alpha(1).setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                lorem.setSelected(true);
                            }
                        });
                    }
                });
            }
        });

    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}
