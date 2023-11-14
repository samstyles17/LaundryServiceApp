package com.example.laundryservice;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;



public class Signup extends AppCompatActivity
{
    String[] locations = {"SG Palya", "Kormangala", "Marthahalli", "Madiwala", "Indiranagar"};


    TextInputEditText userNameEditText,phoneNumberEditText, passwordEditText;
    RadioGroup genderGroup;
    RadioButton maleRadioButton,femaleRadioButton;
    Button nextButton,resetButton;
    Spinner locationSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        userNameEditText = findViewById(R.id.edit_text);
        phoneNumberEditText = findViewById(R.id.edit_text2);
        passwordEditText = findViewById(R.id.password_edit_text);

        genderGroup = findViewById(R.id.gender);
        maleRadioButton = findViewById(R.id.male);
        femaleRadioButton = findViewById(R.id.female);

        nextButton = findViewById(R.id.next);
        resetButton = findViewById(R.id.reset);





        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, locations);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner = findViewById(R.id.location_spinner);
        locationSpinner.setAdapter(adapter);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateFields();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFields();
            }
        });

    }

    private void validateFields()
    {
        userNameEditText = findViewById(R.id.edit_text);
        phoneNumberEditText = findViewById(R.id.edit_text2);
        passwordEditText = findViewById(R.id.password_edit_text);

        genderGroup = findViewById(R.id.gender);
        maleRadioButton = findViewById(R.id.male);
        femaleRadioButton = findViewById(R.id.female);

        nextButton = findViewById(R.id.next);
        resetButton = findViewById(R.id.reset);
        String name="",phno="",psw="";
        Editable userNameField = userNameEditText.getText();
        if(userNameField!=null)
        {
            name = userNameField.toString().trim();
        }

        Editable phnoField = phoneNumberEditText.getText();
        if(phnoField!=null)
        {
            phno = phnoField.toString().trim();
        }
        Editable pswField = passwordEditText.getText();
        if(pswField!=null)
        {
            psw = pswField.toString().trim();
        }
        int genderId = genderGroup.getCheckedRadioButtonId();
        String loc = locationSpinner.getSelectedItem().toString();

        //Validate username
        if (TextUtils.isEmpty(name)) {
            TextInputLayout nameLayout = findViewById(R.id.filledTextField);
            nameLayout.setError("Please enter your name.");
            return;
        }

        // Validate Phone Number
        if (TextUtils.isEmpty(phno)) {
            TextInputLayout phoneNumberLayout = findViewById(R.id.filledTextField2);
            phoneNumberLayout.setError("Please enter your phone number.");
            return;
        } else if (phno.length() != 10) {
            showAlertDialog("Invalid Phone Number", "Phone number should not exceed 10 digits.");
            return;
        }

        // Validate Password field
        if (TextUtils.isEmpty(psw)) {
            TextInputLayout passwordLayout = findViewById(R.id.textInputLayout);
            passwordLayout.setError("Please enter your password.");
            return;
        } else if (psw.length() < 8) {
            showAlertDialog("Invalid Password", "Password should be at least 8 characters long.");
            return;
        }

        // Validate Radio Buttons (Gender)
        if (genderId == -1) {
            Toast.makeText(this, "Please select your gender.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate Location Spinner
        if (TextUtils.isEmpty(loc) || loc.equals("Select Location"))
        {
            Toast.makeText(this, "Please select your location.", Toast.LENGTH_SHORT).show();
        }

        Intent i = new Intent(Signup.this, UserHome.class);
        i.putExtra("Username",name);
        startActivity(i);

    }

    private void showAlertDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle the 'OK' button click (if needed)
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void resetFields()
    {

        userNameEditText.setText("");
        phoneNumberEditText.setText("");
        passwordEditText.setText("");

        TextInputLayout nameLayout = findViewById(R.id.filledTextField);
        nameLayout.setError(null);
        TextInputLayout phoneNumberLayout = findViewById(R.id.filledTextField2);
        phoneNumberLayout.setError(null);
        TextInputLayout passwordLayout = findViewById(R.id.textInputLayout);
        passwordLayout.setError(null);

        genderGroup.clearCheck();
        locationSpinner.setSelection(0);


    }
}