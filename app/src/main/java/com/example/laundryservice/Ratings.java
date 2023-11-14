package com.example.laundryservice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

public class Ratings extends AppCompatActivity
{
    ImageView washImage,ironImage,dryImage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings);
    }

    public void showRatingDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.rating_dialog);

        RatingBar ratingBar = dialog.findViewById(R.id.ratingBar);
        Button submitButton = dialog.findViewById(R.id.submitRatingButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the rating from the RatingBar
                float ratingValue = ratingBar.getRating();

                // Display the rating using a Toast
                String message = "You rated: " + ratingValue;
                Toast.makeText(Ratings.this, message, Toast.LENGTH_SHORT).show();

                dialog.dismiss(); // Close the dialog
            }
        });

        dialog.show();
    }


    public void washRating(View view)
    {
        showRatingDialog();
    }

    public void ironRating(View view)
    {
        showRatingDialog();
    }

    public void dryRating(View view)
    {
        showRatingDialog();
    }
}