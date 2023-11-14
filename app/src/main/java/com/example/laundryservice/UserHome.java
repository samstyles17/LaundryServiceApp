package com.example.laundryservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class UserHome extends AppCompatActivity
{

    SessionManager sessionManager;
    ToggleButton themeChange;
//    private boolean isDarkTheme = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);


        sessionManager = new SessionManager(this);

        if (!sessionManager.isLoggedIn())
        {
            Log.d("Session", "User not logged in");
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(UserHome.this, LoginPage.class);
            startActivity(intent);
            finish(); // Prevent going back to UserHome without login
        }
        else
        {
            Log.d("Session", "User is logged in");
            Toast.makeText(this, "User is logged in", Toast.LENGTH_SHORT).show();
        }


//   themeChange = findViewById(R.id.light_dark);

//        themeChange.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                isDarkTheme = isChecked;
//                applyTheme();
//            }
//        });
     }

//    protected void onResume() {
//        super.onResume();
//        // Apply theme when the activity resumes
//        applyTheme();
//    }

//    private void applyTheme() {
//        if (isDarkTheme) {
//            // Apply dark theme
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//        } else {
//            // Apply light theme
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//        }
//        // Recreate the activity to apply the new theme
//        recreate();
//    }

    public void gotoHome(View view)
    {
        Intent i = new Intent(UserHome.this,LoginPage.class);
        startActivity(i);
    }

    public void newOrder(View view)
    {
        Intent i = new Intent(UserHome.this,NewOrder.class);
        startActivity(i);
    }

    public void rateUs(View view)
    {
        Intent i = new Intent(UserHome.this,Ratings.class);
        startActivity(i);
    }
}