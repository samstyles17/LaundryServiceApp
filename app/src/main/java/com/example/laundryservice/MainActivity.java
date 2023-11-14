package com.example.laundryservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity
{
    FloatingActionButton OpenBottomSheet;
    Button login_button, signup_button;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("MySessionPrefs", Context.MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            Intent i = new Intent(MainActivity.this, UserHome.class);
            startActivity(i);
            finish(); // Prevent going back to MainActivity without login
        }

        OpenBottomSheet = findViewById(R.id.fab);
        login_button = findViewById(R.id.login_button);
        signup_button = findViewById(R.id.signup_button);


        OpenBottomSheet.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this,R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.modal_bottom_sheet,(LinearLayout)findViewById(R.id.modalBottomSheetContainer));

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });

        login_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(MainActivity.this,LoginPage.class);
                startActivity(i);

            }
        });

        signup_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(MainActivity.this,Signup.class);
                startActivity(i);

            }
        });



    }
}