package com.example.hw2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Objects.requireNonNull(getSupportActionBar()).hide();

        setContentView(R.layout.activity_main);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(0xffFFFFFF);

        final Spinner countryPicker = findViewById(R.id.country_input);

        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.countries,
                android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        countryPicker.setAdapter(adapter);

        // The UI has 4 input fields, and one button. Below are the variables that will
        // hold the input values, and the button that will be used to navigate to the
        // next activity.
        final String[] selectedCountry = { "" };
        final EditText legalNameField = findViewById(R.id.legal_name_input);
        final EditText dobField = findViewById(R.id.dob_input);
        final EditText phoneField = findViewById(R.id.phone_input);
        final EditText emailField = findViewById(R.id.email_input);

        final FloatingActionButton nextButton = findViewById(R.id.fab_next);

        countryPicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                // Get the array from the xml and convert it to arraylist.
                final List<String> raw = Arrays.asList(getResources().getStringArray(R.array.countries));

                // Set the selected country to the country that was selected.
                selectedCountry[0] = raw.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        // The onClick method is called when the button is clicked.
        // noinspection SpellCheckingInspection
        nextButton.setOnClickListener((v) -> {
            // Validate the text before moving on to the next activity.
            final List<Boolean> conditions = new ArrayList<>();

            conditions.add(!selectedCountry[0].isEmpty());
            conditions.add(!legalNameField.getText().toString().isEmpty());
            conditions.add(!dobField.getText().toString().isEmpty());
            conditions.add(!phoneField.getText().toString().isEmpty());
            conditions.add(!emailField.getText().toString().isEmpty());

            // Something in the form is not valid. Ignore activity navigation.
            if (conditions.contains(false)) {
                Snackbar.make(v, "Please fill in all fields.", Snackbar.LENGTH_LONG).show();
                return;
            }

            // Validate the numbers in the form are within valid ranges.
            if (phoneField.getText().toString().length() > 10) {
                Snackbar.make(v, "Please enter a valid phone number.", Snackbar.LENGTH_LONG).show();
                return;
            }

            // Validate the email is valid.
            if (!emailField.getText().toString().contains("@")) {
                Snackbar.make(v, "Please enter a valid email.", Snackbar.LENGTH_LONG).show();
                return;
            }

            // Validate the date of birth by parsing the string and confirming if it is
            // valid.
            try {
                final String[] dob = dobField.getText().toString().split("/");

                // Parse each seperated date values
                final int month = Integer.parseInt(dob[0]);
                final int day = Integer.parseInt(dob[1]);
                final int year = Integer.parseInt(dob[2]);

                // Define valid attributes ranges
                final int[] validMonths = { 1, 12 };
                final int[] validDays = { 1, 31 };
                final int[] validYears = { 1800, 2022};

                // Confirm that values are within ranges
                final boolean validMonth = (month > validMonths[0]) && (month < validMonths[1]);
                final boolean validDay = (day > validDays[0]) && (day < validDays[1]);
                final boolean validYear = (year > validYears[0]) && (year < validYears[1]);

                if (!validMonth || !validDay || !validYear) {
                    Snackbar.make(v, "Please enter a valid date of birth.", Snackbar.LENGTH_LONG).show();
                    return;
                }
            } catch (Exception e) {
                Snackbar.make(v, "Please enter a valid date of birth using the following syntax: mm/dd/yyyy", Snackbar.LENGTH_LONG).show();
                return;
            }

             // The next activity is started by getting the intent and setting the
             // class to the next activity.
             final android.content.Intent intent = new android.content.Intent(this, CVResult.class);

             // The input values are put into the intent as extras.
             intent.putExtra("country", selectedCountry[0]);
             intent.putExtra("legal_name", legalNameField.getText().toString());
             intent.putExtra("dob", dobField.getText().toString());
             intent.putExtra("phone_no", phoneField.getText().toString());
             intent.putExtra("email", emailField.getText().toString());

            startActivity(intent);
        });
    }
}