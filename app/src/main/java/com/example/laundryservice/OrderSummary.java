package com.example.laundryservice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class OrderSummary extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);
        String orderSummary = getIntent().getStringExtra("orderSummary");
        EditText ordSumm = findViewById(R.id.editTextTextMultiLine);

        ordSumm.setText(orderSummary);

    }
}