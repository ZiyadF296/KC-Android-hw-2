package com.example.hw2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

public class CVResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Objects.requireNonNull(getSupportActionBar()).hide();

        setContentView(R.layout.activity_cvresult);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(0xffFFFFFF);

        // Load the intent that was passed from the previous activity.
        final Intent intent = getIntent();
        final Bundle extras = intent.getExtras();

        // Variables
        final String country = extras.getString("country");
        final String legal_name = extras.getString("legal_name");
        final String dob = extras.getString("dob");
        final String phone_no = extras.getString("phone_no");
        final String email = extras.getString("email");

        TextView titleView = findViewById(R.id.k_title);
        TextView nameView = findViewById(R.id.name_value);
        TextView countryView = findViewById(R.id.country_value);
        TextView dobView = findViewById(R.id.dob);
        TextView phoneView = findViewById(R.id.phone);
        TextView emailView = findViewById(R.id.email);

        titleView.setText(String.format("Hi, %s", legal_name));
        nameView.setText(legal_name);
        countryView.setText(country);
        dobView.setText(dob);
        phoneView.setText(phone_no);
        emailView.setText(email);

        final Button backButton = findViewById(R.id.back);

        backButton.setOnClickListener((v) -> finish());
    }
}