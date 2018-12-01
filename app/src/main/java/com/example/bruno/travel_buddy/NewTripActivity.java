package com.example.bruno.travel_buddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewTripActivity extends AppCompatActivity {

    static final int CREATE_TRIP_REQUEST = 3;
    private TripListEngine trip_engine;
    Button btnCreate;
    EditText tripName, tripBudget, tripDate, tripEndDate, tripLocation, tripDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trip);

        btnCreate = findViewById(R.id.btn_create_trip);
        tripName = findViewById(R.id.et_trip_title);
        tripDate = findViewById(R.id.et_trip_date);
        tripEndDate = findViewById(R.id.et_trip_end_date);
        tripBudget = findViewById(R.id.et_trip_budget);
        tripLocation = findViewById(R.id.et_trip_location);
        tripDetails = findViewById(R.id.et_trip_details);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            trip_engine = extras.getParcelable("TRIP_ENGINE");
        }

    }

    public void createNewTrip(View view) {
        String title = tripName.getText().toString();
        String date = tripDate.getText().toString();
        String endDate = tripEndDate.getText().toString();
        double budget = Double.valueOf(tripBudget.getText().toString());
        String location = tripLocation.getText().toString();
        String details = tripDetails.getText().toString();

        Trip t = new Trip(trip_engine.getTrip_list().size(), title, location, details, date, endDate, budget);
        trip_engine.getTrip_list().add(t);

        Intent returnIntent = getIntent();
        //returnIntent.putExtra("TRIP_ENGINE", trip_engine);
        setResult(RESULT_OK,returnIntent);
        finish();
    }
}
