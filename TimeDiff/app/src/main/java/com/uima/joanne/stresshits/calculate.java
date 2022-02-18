package com.uima.joanne.stresshits;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class calculate extends AppCompatActivity {
    boolean valid_flag = false;
    int shInt = 0; // start_hour
    int smInt = 0; // start_min
    int ehInt = 0; // end_hour
    int emInt = 0; // end_min
    int convertUnit = 60;

    int time_diff_hour = 0;
    int time_diff_minute = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String start_hr = intent.getStringExtra(MainActivity.EXTRA_MESSAGE1);
        String start_min = intent.getStringExtra(MainActivity.EXTRA_MESSAGE2);
        String end_hr = intent.getStringExtra(MainActivity.EXTRA_MESSAGE3);
        String end_min = intent.getStringExtra(MainActivity.EXTRA_MESSAGE4);

        checkValidAmPm(start_hr, start_min, end_hr, end_min);
        calculateTimeDiff();

        // Capture the layout's TextView and set the string as its text
        TextView textView6 = findViewById(R.id.textView6);
        textView6.setText(String.format("%2d", time_diff_hour)); // String format

        TextView textView7 = findViewById(R.id.textView7);
        textView7.setText(String.format("%2d", time_diff_minute)); // String format

    }

    public void toastErrorMessage() {
        Context context = getApplicationContext();
        CharSequence text = "Invalid input!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void checkValidAmPm(String start_hr, String start_min, String end_hr, String end_min) {
        int min_hour = 0;
        int max_hour = 12;
        int min_minute= 0;
        int max_minute = 59;

        shInt = Integer.parseInt(start_hr); // start_hour
        smInt = Integer.parseInt(start_min); // start_min
        ehInt = Integer.parseInt(end_hr); // end_hour
        emInt = Integer.parseInt(end_min); // end_min

        if (shInt < min_hour || shInt > max_hour) {
            shInt = 0;
            toastErrorMessage();
        }
        if (ehInt < min_hour || ehInt > max_hour) {
            ehInt = 0;
            toastErrorMessage();
        }
        if (smInt < min_minute || smInt > max_minute) {
            smInt = 0;
            toastErrorMessage();
        }
        if (emInt < min_minute || emInt > max_minute) {
            emInt = 0;
            toastErrorMessage();
        }
        valid_flag = true;
    }

    public void calculateTimeDiff() {
        Spinner startSpinner = (Spinner) findViewById(R.id.spinner);
        String start_text = startSpinner.getSelectedItem().toString();
        Spinner endSpinner = (Spinner) findViewById(R.id.spinner2);
        String end_text = endSpinner.getSelectedItem().toString();

        if (start_text.equals("PM")) {
            shInt += 12;
        }
        if (end_text.equals("PM")) {
            ehInt += 12;
        }

        int start_time_total = shInt * convertUnit + smInt;
        int end_time_total = ehInt * convertUnit + emInt;
        int time_diff = Math.abs(end_time_total - start_time_total);

        time_diff_hour = time_diff / convertUnit;
        time_diff_minute = time_diff % convertUnit;
    }


}