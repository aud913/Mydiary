package com.example.shin.mydiary;

import android.os.Bundle;
import android.os.Handler;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    ImageView mRocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mRocket = (ImageView) findViewById(R.id.main_img);

        Handler hd = new Handler();
        hd.postDelayed(new Runnable() {

            @Override
            public void run() {
                finish();
            }
        }, 2000);
    }
    @Override
    public void onResume() {
        super.onResume();

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        startRocketTweenAnimation();
    }

    private void startRocketTweenAnimation() {
        Animation rocket_anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        mRocket.startAnimation(rocket_anim);
    }

}
