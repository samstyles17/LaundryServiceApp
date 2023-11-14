package com.example.laundryservice;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NewOrder extends AppCompatActivity
{
    ImageSwitcher imageSwitcher;
    FloatingActionButton nextButton,prevButton;
    SeekBar weightSeekBar;
    TextView weightTextView;
    private TextView selectedDateTimeTextView;
    private Button pickDateButton;
    private Button pickTimeButton;
    private Button finishButton;
    private Button notifyButton;

    private Calendar calendar;
    private SimpleDateFormat dateFormatter;
    private SimpleDateFormat timeFormatter;
    int imageSwitcherImages[]={R.drawable.wash,R.drawable.iron,R.drawable.dry};

    int switcherImageLength = imageSwitcherImages.length;
    int counter=0;
    CheckBox checkBox;
    Switch switchWidget;

    private boolean checkBoxValue = false;
    private boolean switchValue = false;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence channelName = "Order Notifications";
            String channelId = "order_channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        imageSwitcher = findViewById(R.id.imageSwitcher);
        nextButton = findViewById(R.id.floatingActionButton);
        prevButton = findViewById(R.id.floatingActionButton2);

        weightSeekBar =findViewById(R.id.seekBar);
        weightTextView = findViewById(R.id.weight);

        selectedDateTimeTextView = findViewById(R.id.selectedDateTimeTextView);
        pickDateButton = findViewById(R.id.pickDateButton);
        pickTimeButton = findViewById(R.id.pickTimeButton);

        finishButton = findViewById(R.id.finish);
        notifyButton = findViewById(R.id.notifyButton);
        checkBox = findViewById(R.id.checkBox);
        switchWidget = findViewById(R.id.instantDelivery);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkBoxValue = isChecked;
            }
        });

        switchWidget.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switchValue = isChecked;
            }
        });

        calendar = Calendar.getInstance();
        dateFormatter = new SimpleDateFormat("EEE, MMM dd, yyyy", Locale.getDefault());
        timeFormatter = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        updateSelectedDateTime();

        pickDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
        pickTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });


        weightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                weightTextView.setText(progress+"kg");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                return new ImageView(NewOrder.this);
            }
        });

        showImage(counter);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextImage();
            }
        });


        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPreviousImage();
            }
        });

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog("Order Confirmation","Confirm your order");
            }
        });

        notifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(NewOrder.this, "order_channel")
                        .setSmallIcon(R.drawable.ic_notification_icon)
                        .setContentTitle("Order Summary")
                        .setContentText("Your order summary is ready.")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                // Add an action to open the OrderSummaryActivity when the notification is clicked
                Intent intent = new Intent(NewOrder.this, OrderSummary.class);
                intent.putExtra("orderSummary", getOrderSummary()); // Pass the order summary data
                PendingIntent pendingIntent = PendingIntent.getActivity(NewOrder.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pendingIntent);

                // Show the notification
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(NewOrder.this);
                notificationManager.notify(1, builder.build());
            }
        });
    }
    private void showAlertDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(getOrderSummary());
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

    // Method to get the order summary
    private String getOrderSummary() {
        String orderSummary = "Order Summary:\n";

        // Append selected ImageSwitcher image
        orderSummary += "Selected Image: ";
        switch (counter) {
            case 0:
                orderSummary += "Wash";
                break;
            case 1:
                orderSummary += "Iron";
                break;
            case 2:
                orderSummary += "Dry";
                break;
        }
        orderSummary += "\n";

        // Append SeekBar weight
        int weight = weightSeekBar.getProgress();
        orderSummary += "Weight: " + weight + " kg\n";

        // Append selected date and time
        String dateStr = dateFormatter.format(calendar.getTime());
        String timeStr = timeFormatter.format(calendar.getTime());
        orderSummary += "Selected Date: " + dateStr + "\n";
        orderSummary += "Selected Time: " + timeStr + "\n";

        // Append CheckBox and Switch values
        orderSummary += "Use SMS Service: " + (checkBoxValue ? "Yes" : "No") + "\n";
        orderSummary += "Express Service: " + (switchValue ? "Yes" : "No") + "\n";

        return orderSummary;
    }
    private void updateSelectedDateTime() {
        String dateStr = dateFormatter.format(calendar.getTime());
        String timeStr = timeFormatter.format(calendar.getTime());
        selectedDateTimeTextView.setText(dateStr + " " + timeStr);
    }
    private void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(year, monthOfYear, dayOfMonth);

                        Calendar currentDate = Calendar.getInstance();

                        if (selectedDate.before(currentDate)) {
                            // The selected date is before the current date.
                            // Show an error message or reset the date picker to the current date.
                            // You can display a Toast message or a dialog to inform the user.
                            // For example:
                            Toast.makeText(NewOrder.this, "Please select a future date", Toast.LENGTH_SHORT).show();
                        } else {
                            calendar.set(Calendar.YEAR, year);
                            calendar.set(Calendar.MONTH, monthOfYear);
                            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            updateSelectedDateTime();
                        }
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }

    private void showTimePicker() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar selectedTime = Calendar.getInstance();
                        selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        selectedTime.set(Calendar.MINUTE, minute);

                        Calendar currentTime = Calendar.getInstance();

                        if (selectedTime.before(currentTime)) {
                            // The selected time is before the current time.
                            // Show an error message or reset the time picker to the current time.
                            // You can display a Toast message or a dialog to inform the user.
                            // For example:
                            Toast.makeText(NewOrder.this, "Please select a future time", Toast.LENGTH_SHORT).show();
                        } else {
                            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            calendar.set(Calendar.MINUTE, minute);
                            updateSelectedDateTime();
                        }
                    }
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                false
        );

        timePickerDialog.show();
    }

    public void showPreviousImage() {
        counter = (counter - 1 + switcherImageLength) % switcherImageLength;
        showImage(counter);
    }

    public void showNextImage() {
        counter = (counter+ 1) % switcherImageLength;
        showImage(counter);
    }

    private void showImage(int index) {
        imageSwitcher.setImageResource(imageSwitcherImages[index]);
    }
}