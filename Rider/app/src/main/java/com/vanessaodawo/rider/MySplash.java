package com.vanessaodawo.rider;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class MySplash extends AppCompatActivity {

    ImageView wheel;

    private static int splash_timer = 3000;
    float mRotate = 360f * 2; //rotate twice

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_splash);

        wheel = findViewById(R.id.wheel);

        RotateAnimation rotateAnimation = new RotateAnimation(0.0f, mRotate,
                                            wheel.getWidth()/2.0f, wheel.getHeight()/2.0f);
        rotateAnimation.setDuration(5000); //rotate in 5 secs
        rotateAnimation.setInterpolator(this, android.R.interpolator.accelerate_decelerate);
        wheel.startAnimation(rotateAnimation);

        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.d("Rotation Activity", "onAnimationStart: Started");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.d("Rotation Activity", "onAnimationStart: Stopped");
                endRotate();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private void endRotate() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MySplash.this, MainActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, splash_timer);

    }
}
