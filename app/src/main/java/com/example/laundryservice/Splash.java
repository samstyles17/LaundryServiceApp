package com.example.laundryservice;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

public class Splash extends AppCompatActivity
{
    ImageView backgroundImageView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        backgroundImageView = findViewById(R.id.imageView1);


        AlphaAnimation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(5000); // Set the desired duration for the fade-in animation
        fadeIn.setFillAfter(true); // Maintain the final state of the animation

        AlphaAnimation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setDuration(5000); // Set the desired duration for the fade-out animation
        fadeOut.setFillAfter(true); // Maintain the final state of the animation

        // Add animation listeners to start the next activity after the fade-out animation
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                // Start the main activity here
                Intent intent = new Intent(Splash.this, MainActivity.class);
                startActivity(intent);

                //Apply Cross-fade animation
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                finish(); // Optional: Call finish() to prevent the user from going back to the splash screen
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        // Set the animations on the ImageView
        backgroundImageView.startAnimation(fadeIn);
        backgroundImageView.startAnimation(fadeOut); // Fade-out will automatically start after fade-in due to the setFillAfter(true)
    }
}